package io.rohlik.gp.order.dto;

import io.rohlik.gp.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {

    private Long id;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> orderItemDTOS;
}
