package com.griddynamics.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class InventoryApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(InventoryApplication.class, args);

        for (String name : applicationContext.getBeanDefinitionNames())
            if (name.contains("inventory")) System.out.println(name);
    }
}
