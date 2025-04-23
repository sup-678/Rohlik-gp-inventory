package io.rohlik.gp.product.mapper;

import io.rohlik.gp.product.dto.ProductDTO;
import io.rohlik.gp.product.input.ProductInput;
import io.rohlik.gp.product.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(ProductEntity entity) {
        if (entity == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStock(entity.getStock());
        dto.setReserved(entity.getReserved());
        dto.setPrice(entity.getPrice());
        return dto;
    }

    public ProductEntity toEntity(ProductInput dto) {
        if (dto == null) return null;

        ProductEntity entity = new ProductEntity();
        entity.setName(dto.getName());
        entity.setStock(dto.getStock());
        entity.setReserved(dto.getReserved());
        entity.setPrice(dto.getPrice());
        return entity;
    }
}
