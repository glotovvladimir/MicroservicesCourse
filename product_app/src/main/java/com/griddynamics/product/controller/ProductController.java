package com.griddynamics.product.controller;

import com.griddynamics.product.model.Product;
import com.griddynamics.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    
    @RequestMapping(path = "/product/id/{id}", method = RequestMethod.GET)
    public Product getProductByIdIfAvailable(@PathVariable("id") String id) {
        return productService.getProductByIdIfAvailable(id);
    }

    @RequestMapping(path = "/product/sku/{sku}", method = RequestMethod.GET)
    public List<Product> getProductsBySkuIfAvailable(@PathVariable("sku") String sku) {
        return productService.getProductsBySkuIfAvailable(sku);
    }
}
