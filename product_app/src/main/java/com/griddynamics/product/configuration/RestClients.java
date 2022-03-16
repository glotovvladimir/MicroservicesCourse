package com.griddynamics.product.configuration;

import com.griddynamics.product.clients.CatalogClient;
import com.griddynamics.product.clients.InventoryClient;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Random;

@Configuration
public class RestClients {

    @Value("${inventory.app.name}")
    private String inventoryAppName;

    @Value("${catalog.app.name}")
    private String catalogAppName;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Bean
    CatalogClient getCatalogClient() {
        return createClient(CatalogClient.class, getServiceUrl(catalogAppName));
    }

    @Bean
    InventoryClient getInventoryClient() {
        return createClient(InventoryClient.class, getServiceUrl(inventoryAppName));
    }

    private String getServiceUrl(String name) {
        Random rand = new Random();
        List<ServiceInstance> instances = discoveryClient.getInstances(name);

        return instances.get(rand.nextInt(instances.size())).getUri().toString();
    }

    private static <T> T createClient(Class<T> type, String uri) {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(type))
                .logLevel(Logger.Level.FULL)
                .target(type, uri);
    }
}
