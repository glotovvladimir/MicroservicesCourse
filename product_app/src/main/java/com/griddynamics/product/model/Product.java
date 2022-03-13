package com.griddynamics.product.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Product {
    
    private String uniq_id;
    private String SKU;
    private String name_title;
    private String description;
    private String list_price;
    private String sale_price;
    private String category;
    private String category_tree;
    private String average_product_rating;
    private String product_url;
    private String product_image_urls;
    private String brand;
    private String total_number_reviews;
    private String reviews;
}
