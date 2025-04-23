package io.rohlik.gp.order.repository;

import io.rohlik.gp.order.entity.OrderEntity;
import io.rohlik.gp.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByStatusAndCreatedAtBefore(OrderStatus status, LocalDateTime createdAt);
}