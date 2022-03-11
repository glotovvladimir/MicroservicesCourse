package com.griddynamics.inventory.controller;

import com.griddynamics.inventory.model.ProductStatus;
import com.griddynamics.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    private InventoryService inventoryService;
    
    public InventoryController(@Autowired InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping(path = "/status/{id}", method = RequestMethod.GET)
    public ProductStatus getStatusById(@PathVariable("id") String id) {
        return inventoryService.getStatus(id);
    }
}
