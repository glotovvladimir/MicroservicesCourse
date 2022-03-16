package com.griddynamics.product.clients;

import com.griddynamics.product.model.Product;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface CatalogClient {

    @RequestLine("GET /product/{id}")
    Product getProductInfoFromCatalogById(@Param("id") String id);

    @RequestLine("GET /products?sku={sku}")
    List<Product> getProductsInfoFromCatalogBySku(@Param("sku") String sku);
}
