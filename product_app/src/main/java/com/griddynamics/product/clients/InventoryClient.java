package com.griddynamics.product.clients;

import com.griddynamics.product.model.ProductStatus;
import feign.Param;
import feign.RequestLine;

public interface InventoryClient {

    @RequestLine("GET /status/{id}")
    ProductStatus getAvailabilityById(@Param("id") String id);
}
