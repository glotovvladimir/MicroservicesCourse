package com.griddynamics.inventory.service;

import com.griddynamics.inventory.db.ProductRepository;
import com.griddynamics.inventory.model.ProductStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    private ProductRepository repository;
    
    public InventoryService(@Autowired ProductRepository repository) {
        this.repository = repository;
    }

    public ProductStatus getStatus(String id) {
        Optional<ProductStatus> result = repository.findById(id);
        return result.orElse(null);
    }
}
