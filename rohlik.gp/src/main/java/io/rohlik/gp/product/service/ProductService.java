package io.rohlik.gp.product.service;

import io.rohlik.gp.ProductNotFoundException;
import io.rohlik.gp.product.dto.ProductDTO;
import io.rohlik.gp.product.input.ProductInput;
import io.rohlik.gp.product.entity.ProductEntity;
import io.rohlik.gp.product.mapper.ProductMapper;
import io.rohlik.gp.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDTO createProduct(ProductInput productDTO) {
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        ProductEntity saved = productRepository.save(productEntity);
        return productMapper.toDto(saved);
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductInput productUpdateDTO) {
        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        if (productUpdateDTO.getStock() < existing.getReserved()) {
            throw new IllegalArgumentException("Stock cannot be less than reserved quantity");
        }

        // fields updated
        existing.setName(productUpdateDTO.getName());
        existing.setStock(productUpdateDTO.getStock());
        existing.setPrice(productUpdateDTO.getPrice());

        ProductEntity updated = productRepository.save(existing);
        return productMapper.toDto(updated);
    }

    @Transactional
    public void deleteProduct(Long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
