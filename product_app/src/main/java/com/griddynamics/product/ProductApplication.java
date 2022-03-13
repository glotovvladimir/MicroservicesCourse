package com.griddynamics.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;

@EnableCircuitBreaker
@SpringBootApplication
public class ProductApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(ProductApplication.class, args);

        for (String name : applicationContext.getBeanDefinitionNames())
            if (name.contains("product")) System.out.println(name);
    }
}
