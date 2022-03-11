package com.griddynamics.catalog.service;

import com.griddynamics.catalog.db.ProductRepository;
import com.griddynamics.catalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CatalogService {
    
    private ProductRepository repository;

    @Autowired
    public CatalogService(ProductRepository repository) {
        this.repository = repository;
    }
    
    public Product getProductById(String id) {
        Optional<Product> result = repository.findById(id);
        return result.orElse(null);
    }
    
    public List<Product> getProductsBySku(String sku) {
        return repository.findAllBySKU(sku);
    }
}
