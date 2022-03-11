package com.griddynamics.catalog.db;

import com.griddynamics.catalog.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
    
    Optional<Product> findById(String Id);

    List<Product> findAllBySKU(String sku);
}
