# spring-webflux-client-server

The goal of this project is to play with [`Spring WebFlux`](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html) both on client and server side. For it, we will implement [`Spring Boot`](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) Java Web applications (`product-api`, `customer-api`, `order-api` and `client-shell`) and use reactive NoSQL database like [`Cassandra`](https://cassandra.apache.org/), [`MongoDB`](https://www.mongodb.com/) and [`Postgres`](https://www.postgresql.org/).

## Project Architecture

![project-diagram](images/project-diagram.png)

## Applications

- **product-api**

  `Spring Boot` Java Web application that exposes a REST API to manage `products`. It uses `MongoDB` as storage.
  
  ![product-api-swagger](images/product-api-swagger.png)

- **customer-api**

  `Spring Boot` Java Web application that exposes a REST API to manage `customers`. It uses `Postgres` as storage.
  
  ![customer-api-swagger](images/customer-api-swagger.png)

- **order-api**

  `Spring Boot` Web Java application that exposes a REST API to manage `orders`. It uses `Cassandra` as storage. In order to get more information about an `order`, i.e, the `name` of the customer who placed it or the `name` or `price` of the products in the order, `order-api` uses [`WebClient`](https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client) and [`CompletableFuture`](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) to fetch those information from `customer-api` and `product-api`.
  
  ![order-api-swagger](images/order-api-swagger.png)

- **client-shell**

  `Spring Boot` Shell Java application that has a couple of commands to interact with `product-api`, `customer-api` and `order-api`. The picture below show those commands.

  ![client-shell](images/client-shell.png)
  
## Prerequisites

- `Java 11+`
- `Docker`
- `Docker-Compose`

## Start Environment

- Open a terminal and inside `spring-webflux-client-server` root folder run
  ```
  docker-compose up -d
  ```

- Wait a little bit until all containers are Up (healthy). You can check their status running
  ```
  docker-compose ps
  ```

## Start applications

- **product-api**

  Open a new terminal and, inside `spring-webflux-client-server` root folder, run the following command
  ```
  ./mvnw clean spring-boot:run --projects product-api -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
  ```

- **customer-api**

  Open a new terminal and, inside `spring-webflux-client-server` root folder, run the following command
  ```
  ./mvnw clean spring-boot:run --projects customer-api -Dspring-boot.run.jvmArguments="-Dserver.port=9081"
  ```

- **order-api**

  Open a new terminal and, inside `spring-webflux-client-server` root folder, run the following command 
  ```
  ./mvnw clean spring-boot:run --projects order-api -Dspring-boot.run.jvmArguments="-Dserver.port=9082"
  ```

- **client-shell**

  Open a new terminal and, inside `spring-webflux-client-server` root folder, run the following command to build the executable jar file
  ```
  ./mvnw clean package -DskipTests --projects client-shell
  ```

  To start `client-shell` run
  ```
  ./client-shell/target/client-shell-0.0.1-SNAPSHOT.jar
  ```

## Applications URLs

| Application  | URL                                   |
| ------------ | ------------------------------------- |
| product-api  | http://localhost:9080/swagger-ui.html |
| customer-api | http://localhost:9081/swagger-ui.html |
| order-api    | http://localhost:9082/swagger-ui.html | 

## Playing around

> **Important:** the ids shown below will be different when you run it

- Go to `client-shell` terminal and import some products and customers by running the following command
  ```
  script ../samples.txt
  ```
  
- Get all customer
  ```
  get-customers
  ```
  
  It returns
  ```
  {"id":"1","name":"Customer A","email":"customer.a@test.com","city":"Berlin","street":"NYC Strasse","number":"123"}
  {"id":"2","name":"Customer B","email":"customer.b@test.com","city":"Berlin","street":"LA Strasse","number":"234"}
  {"id":"3","name":"Customer C","email":"customer.c@test.com","city":"Berlin","street":"DC Strasse","number":"345"}
  ...
  ```
  
- Get all products
  ```
  get-products
  ```
  
  It returns
  ```
  {"id":"5ee3ee31b460d868af49f389","name":"product-1","price":199.99}
  {"id":"5ee3ee32b460d868af49f38a","name":"product-2","price":299.99}
  ...
  ```
  
- Create an order where `Customer A` buys `1 unit` of `product-1` and `2 units` of `product-2`
  ```
  create-order --customer-id 1 --products 5ee3ee31b460d868af49f389:1;5ee3ee32b460d868af49f38a:2
  ```
  
  It returns
  ```
  {
    "orderId": "5aaad64c-4e80-48e0-926d-8f1b7027955a",
    "status": "OPEN",
    "created": "2020-06-12T22:09:59.558232",
    "products": [
      {"id": "5ee3ee31b460d868af49f389","quantity": 1},
      {"id": "5ee3ee32b460d868af49f38a","quantity": 2}
    ],
    "customerId": "1"
  }
  ```
  
- Get details about the order created
  ```
  get-order-detailed 5aaad64c-4e80-48e0-926d-8f1b7027955a
  ```
  
  It returns
  ```
  {
    "orderId": "5aaad64c-4e80-48e0-926d-8f1b7027955a",
    "status": "OPEN",
    "created": "2020-06-12T22:09:59.558",
    "products": [
      {"id": "5ee3ee32b460d868af49f38a","name": "product-2","quantity": 2,"price": 299.99},
      {"id": "5ee3ee31b460d868af49f389","name": "product-1","quantity": 1,"price": 199.99}
    ],
    "customer": {
      "id": "1",
      "name": "Customer A",
      "email": "customer.a@test.com",
      "city": "Berlin",
      "street": "NYC Strasse",
      "number": "123"
    }
  }
  ```
  
- To check how fast `order-api` get details about customer and products of an order, create another order where `Customer A` order `50` random products
  ```
  create-order-random --customer-id 1 --num-products 50
  ```
  
  It returns
  ```
  {
    "orderId": "87133d36-67f0-4388-b15b-7d66ad739374",
    "status": "OPEN",
    "created": "2020-06-12T22:14:08.342338",
    "products": [
      {"id": "5ee3ee32b460d868af49f38a","quantity": 4},
      ...
      {"id": "5ee3ee32b460d868af49f396","quantity": 3}
    ],
    "customerId": "1"
  }
  ```
  
- In another terminal, to get the details of the order previously created and the response time of this call, we are using `order-api`'s endpoint `GET ​/api​/orders​/{orderId}​/detailed`
  ```
  curl -w "\n\nResponse Time: %{time_total}s" -s localhost:9082/api/orders/87133d36-67f0-4388-b15b-7d66ad739374/detailed
  ```
  
  It will return
  ```
  {
    "orderId": "87133d36-67f0-4388-b15b-7d66ad739374",
    "status": "OPEN",
    "created": "2020-06-12T22:14:08.342338",
    "products": [
      {"id": "5ee3ee32b460d868af49f395","name": "product-13","quantity": 4,"price": 1399.99},
      ...
    ],
    "customer": {
      "id": "1",
      "name": "Customer A",
      "email": "customer.a@test.com",
      "city": "Berlin",
      "street": "NYC Strasse",
      "number": "123"
    }
  }
  
  Response Time: 0.292698s
  ```

## Shutdown

- Go to `client-shell` terminal and type `exit`

- Go to `product-api`, `customer-api` and `order-api` terminals and press `Ctrl+C` on each one

- To stop and remove docker-compose containers, network and volumes, run
  ```
  docker-compose down -v
  ```

## Useful Commands & Links

- **Cassandra**

  Select all orders
  ```
  docker exec -it cassandra cqlsh
  USE mycompany;
  SELECT * FROM orders;
  ```
  > Type `exit` to get out of `cqlsh`

- **MongoDB**

  Find all products
  ```
  docker exec -it mongodb mongo
  use productdb
  db.products.find()
  ```
  > Type `exit` to get out of `MongoDB shell`

- **Postgres**

  Select all customers
  ```
  docker exec -it studies-postgres psql -U postgres -d customerdb
  \dt customer
  SELECT * FROM CUSTOMER;
  ```
  > Type `\q` to exit

## TODO

- Validate if customer and products exist before creating an order

