package io.rohlik.gp.order.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    @Schema(name = "productId", example = "1")
    private Long productId;
    @Schema(name = "quantity", example = "5")
    private int quantity;
}