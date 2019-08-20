# `spring-webflux-client-server`

## product-api

```
./mvnw spring-boot:run --projects product-api -Dspring-boot.run.jvmArguments="-Dserver.port=9080"
```

## order-api

```
./mvnw spring-boot:run --projects order-api -Dspring-boot.run.jvmArguments="-Dserver.port=9081"
```

## Reference

- Using R2DBC & Postgres: https://dimitr.im/reactive-relational-databases-r2dbc-spring

## Issues

- Spring Boot version 2.2.0.M5 has a problem with Springfox: https://github.com/springfox/springfox/issues/2932