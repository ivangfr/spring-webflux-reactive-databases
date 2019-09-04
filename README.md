# `spring-webflux-client-server`

The goal of this project is to play with [`Spring WebFlux`](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html)
both on client and server side. For it, we will implement some [`Spring Boot`](https://spring.io/projects/spring-boot)
Java Web applications, `product-api`, `customer-api`, `order-api` and `client-shell`, and use reactive NoSQL database
like [`Cassandra`](https://cassandra.apache.org/), [`MongoDB`](https://www.mongodb.com/) and [`Couchbase`](https://www.couchbase.com/).

## Project Architecture

![project-diagram](images/project-diagram.png)

## Microservices

### product-api

Spring Boot Java Web application that exposes a REST API to manage `products`. It uses `MongoDB` as storage.

### customer-api

Spring Boot Java Web application that exposes a REST API to manage `customers`. It uses `Couchbase` as storage.

### order-api

Spring Boot Java Web application that exposes a REST API to manage `orders`. It uses `Cassandra` as storage. In order
to get more information about an `order`, i.e, the `name` of the customer who placed it or the `name` or `price` of
the products in the order, `order-api` uses [`WebClient`](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client)
and [`CompletableFuture`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) to
fetch those information from `customer-api` and `product-api`.

### client-shell

Spring Boot Shell Java application that has a couple of commands to interact with `product-api`, `customer-api` and
`order-api`. The picture below show those commands.

![client-shell](images/client-shell.png)

## Start Environment

Open a terminal and inside `spring-webflux-client-server` root folder run
```
docker-compose up -d
```

Wait a little bit until all containers are Up (healthy). You can check their status running
```
docker-compose ps
```

Then, still inside `spring-webflux-client-server` root folder, run the script below. It will initialize
`Couchbase` database that is used by `customer-api`
```
./setup-couchbase.sh
```

## Start microservices

The following commands must be executed inside `spring-webflux-client-server` root folder.

### product-api

In order to start `product-api`, run the following Maven command
```
./mvnw spring-boot:run --projects product-api -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
```

### customer-api

To start `customer-api`, run the Maven command below
```
./mvnw spring-boot:run --projects customer-api -Dspring-boot.run.jvmArguments="-Dserver.port=9081"
```

### order-api

Run the following Maven command to start `order-api` 
```
./mvnw spring-boot:run --projects order-api -Dspring-boot.run.jvmArguments="-Dserver.port=9082"
```

### client-shell

Run the following Maven command to build the executable jar file
```
./mvnw clean package -DskipTests --projects client-shell
```

To start `client-shell` run
```
./client-shell/target/client-shell-0.0.1-SNAPSHOT.jar 
```

## Microservices Swagger Links

| Microservice   | Swagger Link                          |
| -------------- | ------------------------------------- |
| `product-api`  | http://localhost:9080/swagger-ui.html |
| `customer-api` | http://localhost:9081/swagger-ui.html |
| `order-api`    | http://localhost:9082/swagger-ui.html | 

## Shutdown

To stop and remove containers, networks and volumes, run
```
docker-compose down -v
```

## Useful Commands & Links

### Cassandra

Select all orders
```
docker exec -it cassandra cqlsh
USE mycompany;
SELECT * FROM orders;
```
> Type `exit` to get out of `cqlsh`

### MongoDB

Find all products
```
docker exec -it mongodb mongo
use productdb
db.products.find()
```
> Type `exit` to get out of `MongoDB shell`

### Couchbase

Couchbase Web Console can be accessed at http://localhost:8091.

The login credentials are
```
username: Administrator
password: password
```

## TODO

- validate if customer and products exist before creating an order

## Issues

- Spring Boot version 2.2.0.M5 has a problem with Springfox: https://github.com/springfox/springfox/issues/2932