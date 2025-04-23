package io.rohlik.gp.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InsufficientStockItem {
    private Long productId;
    private String productName;
    private int missingQuantity;
}
