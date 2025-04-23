package io.rohlik.gp.product.repository;

import io.rohlik.gp.product.entity.ProductEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductEntity> findAllByIdIn(List<Long> ids);
}
