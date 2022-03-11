package com.griddynamics.catalog.controller;

import com.griddynamics.catalog.model.Product;
import com.griddynamics.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CatalogController {
    
    private CatalogService catalogService;
    
    public CatalogController(@Autowired CatalogService catalogService) {
        this.catalogService = catalogService;
    }
    
    @RequestMapping(path = "/product/{id}", method = RequestMethod.GET)
    public Product getProductById(@PathVariable("id") String id) {
        return catalogService.getProductById(id);
    }

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    public List<Product> getProductListBySKU(@RequestParam("sku") String sku) {
        return catalogService.getProductsBySku(sku);
    }
    
    
}
