# `spring-webflux-client-server`

## product-api

```
./mvnw spring-boot:run --projects product-api -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
```

## customer-api

```
./mvnw spring-boot:run --projects customer-api -Dspring-boot.run.jvmArguments="-Dserver.port=9081"
```

## order-api

```
./mvnw spring-boot:run --projects order-api -Dspring-boot.run.jvmArguments="-Dserver.port=9082"
```

## Issues

- Spring Boot version 2.2.0.M5 has a problem with Springfox: https://github.com/springfox/springfox/issues/2932