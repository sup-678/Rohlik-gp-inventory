package io.rohlik.gp.product.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductInput implements Serializable {

    @Schema(name = "name", example = "Ginger")
    private String name;
    @Schema(name = "stock", example = "100")
    private int stock;
    @Schema(name = "reserved", example = "0")
    private int reserved;
    @Schema(name = "price", example = "7.90")
    private BigDecimal price;
}
