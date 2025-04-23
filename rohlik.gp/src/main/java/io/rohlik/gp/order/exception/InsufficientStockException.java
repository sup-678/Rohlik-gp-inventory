package io.rohlik.gp.order.exception;

import io.rohlik.gp.order.model.InsufficientStockItem;
import lombok.Getter;

import java.util.List;

@Getter
public class InsufficientStockException extends RuntimeException {
    private final List<InsufficientStockItem> insufficientItems;

    public InsufficientStockException(List<InsufficientStockItem> insufficientItems) {
        super("Insufficient stock for some items");
        this.insufficientItems = insufficientItems;
    }
}
