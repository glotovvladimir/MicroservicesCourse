package com.griddynamics.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class CatalogApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(CatalogApplication.class, args);

        for (String name : applicationContext.getBeanDefinitionNames())
            if (name.contains("catalog") || name.contains("product")) System.out.println(name);
    }
}
