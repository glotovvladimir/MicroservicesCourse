package com.griddynamics.product.service;

import com.griddynamics.product.model.Product;
import com.griddynamics.product.model.ProductStatus;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.ResponseBodyExtractionOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

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

    @Autowired
    private DiscoveryClient discoveryClient;

    private String getServiceUrl(String name) {
        Random rand = new Random();
        List<ServiceInstance> instances = discoveryClient.getInstances(name);
        
        return instances.get(rand.nextInt(instances.size())).getUri().toString();
    }

    @HystrixCommand(fallbackMethod = "fallbackForGetProductByIdIfAvailable")
    public Product getProductByIdIfAvailable(String id) {
        String result = getAvailabilityById(id);
        
        return result == null || result.equals("0.0") ? null : getProductInfoFromCatalogById(id);
    }
    
    private Product fallbackForGetProductByIdIfAvailable(String id) {
        return Product.builder()
                .uniq_id(id)
                .build();
    }
    
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
    
    public String getAvailabilityById(String id) {
        ResponseBodyExtractionOptions response =  
                given()
                        .contentType(ContentType.JSON)
                        .relaxedHTTPSValidation()
                        .baseUri(getServiceUrl(inventoryAppName))
                        .log().all()
                .when()
                        .get(inventoryAvailabilityPath + id)
                .then()
                        .log().all()
                        .extract()
                        .body();

        return ((RestAssuredResponseImpl) response)
                .getGroovyResponse()
                .getContent()
                .equals("") ? null : response.as(ProductStatus.class).getStatus();
    }
    
    public Product getProductInfoFromCatalogById(String id) {
        return
                given()
                        .contentType(ContentType.JSON)
                        .relaxedHTTPSValidation()
                        .baseUri(getServiceUrl(catalogAppName))
                        .log().all()
                .when()
                        .get(productInfoPath + id)
                .then()
                        .log().all()
                        .extract()
                        .body()
                        .as(Product.class);
    }

    public List<Product> getProductsInfoFromCatalogBySku(String sku) {
        ResponseBodyExtractionOptions response =
                given()
                        .contentType(ContentType.JSON)
                        .relaxedHTTPSValidation()
                        .baseUri(getServiceUrl(catalogAppName))
                        .queryParam("sku", sku)
                        .log().all()
                .when()
                        .get(productListBySkuPath)
                .then()
                        .log().all()
                        .extract()
                        .body();
        
        return ((RestAssuredResponseImpl) response)
                .getGroovyResponse()
                .getContent()
                .equals("") ? null : response.jsonPath().getList(".", Product.class);
    }
}
