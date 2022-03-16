package com.griddynamics.product.service;

import com.griddynamics.product.clients.CatalogClient;
import com.griddynamics.product.clients.InventoryClient;
import com.griddynamics.product.model.Product;
import com.griddynamics.product.model.ProductStatus;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ProductService {

    @Value("${catalog.app.info.path}")
    private String productInfoPath;
    
    @Value("${catalog.app.list.by.sku.path}")
    private String productListBySkuPath;
    
    @Value("${inventory.app.availability.path}")
    private String inventoryAvailabilityPath;

    @Value("${inventory.app.name}")
    private String inventoryAppName;

    @Value("${catalog.app.name}")
    private String catalogAppName;
    
    private CatalogClient catalogClient;
    
    private InventoryClient inventoryClient;

    public ProductService(@Autowired CatalogClient catalogClient, 
                          @Autowired InventoryClient inventoryClient) {
        this.catalogClient = catalogClient;
        this.inventoryClient = inventoryClient;
    }

    @HystrixCommand(fallbackMethod = "fallbackForGetProductByIdIfAvailable",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "4000"),
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
            })
    public Product getProductByIdIfAvailable(String id) {
        String result = getAvailabilityById(id);
        
        return result == null || result.equals("0.0") ? null : getProductInfoFromCatalogById(id);
    }

    private Product fallbackForGetProductByIdIfAvailable(String id) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    @HystrixCommand(fallbackMethod = "fallbackForGetProductsBySkuIfAvailable",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "4000"),
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
            })
    public List<Product> getProductsBySkuIfAvailable(String sku) {
        ArrayList<Product> result = new ArrayList<>(getProductsInfoFromCatalogBySku(sku));
        Iterator iterator = result.iterator();

        while (iterator.hasNext()) {
            Product product = (Product) iterator.next();
            String availability = getAvailabilityById(product.getUniq_id());

            if (availability == null || availability.equals("0.0"))
                iterator.remove();
        }
        return result;
    }

    private List<Product> fallbackForGetProductsBySkuIfAvailable(String sku) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    public String getAvailabilityById(String id) {
        ProductStatus ps = inventoryClient.getAvailabilityById(id);
        return null != ps ? ps.getStatus() : null;
    }
    
    public Product getProductInfoFromCatalogById(String id) {
        return catalogClient.getProductInfoFromCatalogById(id);
    }

    public List<Product> getProductsInfoFromCatalogBySku(String sku) {
        return catalogClient.getProductsInfoFromCatalogBySku(sku);
    }
}
