package io.rohlik.gp.order.service;

import io.rohlik.gp.order.dto.OrderDTO;
import io.rohlik.gp.order.exception.OrderNotFoundException;
import io.rohlik.gp.order.mapping.OrderMapper;
import io.rohlik.gp.order.entity.OrderEntity;
import io.rohlik.gp.order.entity.OrderItem;
import io.rohlik.gp.order.entity.OrderStatus;
import io.rohlik.gp.order.exception.InsufficientStockException;
import io.rohlik.gp.order.model.InsufficientStockItem;
import io.rohlik.gp.order.model.OrderItemRequest;
import io.rohlik.gp.order.model.OrderRequest;
import io.rohlik.gp.order.repository.OrderRepository;
import io.rohlik.gp.product.entity.ProductEntity;
import io.rohlik.gp.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    private static final Duration ORDER_EXPIRATION = Duration.ofMinutes(30);

    @Transactional
    public OrderDTO createOrder(OrderRequest request) {
        Map<Long, Integer> productQuantities = request.getItems().stream()
                .collect(Collectors.toMap(OrderItemRequest::getProductId, OrderItemRequest::getQuantity));

        List<ProductEntity> products = productRepository.findAllByIdIn(new ArrayList<>(productQuantities.keySet()));

        List<InsufficientStockItem> insufficientStockItems = new ArrayList<>();
        for (ProductEntity product : products) {
            int required = productQuantities.get(product.getId());
            int available = product.getStock() - product.getReserved();
            if (available < required) {
                insufficientStockItems.add(new InsufficientStockItem(product.getId(), product.getName(), required - available));
            }
        }

        if (!insufficientStockItems.isEmpty()) {
            throw new InsufficientStockException(insufficientStockItems);
        }

        products.forEach(product -> {
            int required = productQuantities.get(product.getId());
            product.setReserved(product.getReserved() + required);
            productRepository.save(product);
        });

        OrderEntity order = orderMapper.toEntity(request, products);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        OrderEntity savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException( "Order not found"+ orderId));
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be canceled");
        }

        order.getOrderItems().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setReserved(product.getReserved() - item.getQuantity());
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        });

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }



    @Transactional
    public void payOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order not found"+ orderId));
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Order cannot be paid");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only pending orders can be paid.");
        }

        if (order.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(30))) {
            throw new RuntimeException("Order has expired");
        }

        order.getOrderItems().forEach(item -> {
            ProductEntity product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            product.setReserved(product.getReserved() - item.getQuantity());
            productRepository.save(product);
        });

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void cancelExpiredOrders() {

        LocalDateTime expirationTime = LocalDateTime.now().minus(ORDER_EXPIRATION);
        List<OrderEntity> expiredOrders = orderRepository.findByStatusAndCreatedAtBefore(OrderStatus.PENDING, expirationTime);
        for (OrderEntity order : expiredOrders) {
            for (OrderItem item : order.getOrderItems()) {
                ProductEntity product = item.getProduct();
                product.setReserved(product.getReserved() - item.getQuantity());
                product.setStock(product.getStock() + item.getQuantity());
            }

            order.setStatus(OrderStatus.CANCELED);
        }

        orderRepository.saveAll(expiredOrders);
    }

}
