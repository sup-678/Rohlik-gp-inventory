package io.rohlik.gp.order.controller;

import io.rohlik.gp.order.dto.OrderDTO;
import io.rohlik.gp.order.entity.OrderEntity;
import io.rohlik.gp.order.model.OrderRequest;
import io.rohlik.gp.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order")
    public OrderDTO createOrder(@RequestBody OrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel an order")
    public void cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
    }

    @PostMapping("/{id}/pay")
    @Operation(summary = "Pay for an order")
    public void payOrder(@PathVariable Long id) {
        orderService.payOrder(id);
    }
}
