package io.rohlik.gp.order.mapping;

import io.rohlik.gp.order.dto.OrderDTO;
import io.rohlik.gp.order.dto.OrderItemDTO;
import io.rohlik.gp.order.entity.OrderEntity;
import io.rohlik.gp.order.entity.OrderItem;
import io.rohlik.gp.order.model.OrderItemRequest;
import io.rohlik.gp.order.model.OrderRequest;
import io.rohlik.gp.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderRequest request, List<ProductEntity> products) {
        OrderEntity order = new OrderEntity();
        order.setOrderItems(new ArrayList<>());

        Map<Long, ProductEntity> productMap = products.stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

        for (OrderItemRequest item : request.getItems()) {
            ProductEntity product = productMap.get(item.getProductId());

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(product.getPrice());
            orderItem.setPriceAtOrder(product.getPrice());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }

        return order;
    }

    public OrderDTO toDto(OrderEntity entity) {
        if (entity == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());

        List<OrderItemDTO> itemDTOs = entity.getOrderItems().stream()
                .map(this::toOrderItemDto)
                .collect(Collectors.toList());

        dto.setOrderItemDTOS(itemDTOs);
        return dto;
    }

    private OrderItemDTO toOrderItemDto(OrderItem item) {
        BigDecimal unitPrice = item.getUnitPrice();
        int quantity = item.getQuantity();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));

        return new OrderItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                quantity,
                unitPrice,
                totalPrice
        );
    }
}