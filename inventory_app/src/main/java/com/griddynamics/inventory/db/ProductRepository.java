package com.griddynamics.inventory.db;

import com.griddynamics.inventory.model.ProductStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<ProductStatus, String> {
    
    Optional<ProductStatus> findById(String Id);
}
