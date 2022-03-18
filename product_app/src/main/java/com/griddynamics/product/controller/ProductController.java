package com.griddynamics.product.controller;

import com.griddynamics.product.model.Product;
import com.griddynamics.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    
    private ProductService productService;
    private static final Logger log =  LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @RequestMapping(path = "/product/id/{id}", method = RequestMethod.GET)
    public Product getProductByIdIfAvailable(@PathVariable("id") String id) {
        log.info("Getting available product by id: " + id);
        return productService.getProductByIdIfAvailable(id);
    }

    @RequestMapping(path = "/product/sku/{sku}", method = RequestMethod.GET)
    public List<Product> getProductsBySkuIfAvailable(@PathVariable("sku") String sku) {
        log.info("Getting available products by sku: " + sku);
        return productService.getProductsBySkuIfAvailable(sku);
    }
}
