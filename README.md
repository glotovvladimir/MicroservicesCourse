# Microservices project

## Prerequisites:
- Docker client running

## Project information
Project consists of 4 modules:
- Eureka server
- Catalog app
- Inventory app
- Product app

Zipkin server starts on http://localhost:9411 \
Eureka server starts on http://localhost:8761

### Eureka server
Registers other services to perform inter service calls using application name

### Catalog app
Holds product info in H2 database \
Used to retrieve info by product id as one item or product sku as product list \
Uses Zipkin server to post info for request performed

### Inventory app
Holds product availability info in H2 database \
Used to retrieve availability info by product id \
Uses Zipkin server to post info for request performed

### Product app
Performs product info requests and returns it based on its availability \
Used to retrieve info by product id as one item or product sku as product list \
Uses Zipkin server to post info for request performed

## How to start project:
1. Run ```docker run -d -p 9411:9411 openzipkin/zipkin``` to start Zipkin server
2. Start Eureka server from 'eureka_server' module
3. Start Catalog app from 'catalog_app' module
4. Start Inventory app from 'inventory_app' module
5. Start Product app from 'product_app' module

## Request examples:
Id and sku values used in example can return different results based on DB creation for Inventory app with random availability for each id 
- http://localhost:8084/product/id/b6c0b6bea69c722939585baeac73c13d
- http://localhost:8084/product/sku/pp5006790242

