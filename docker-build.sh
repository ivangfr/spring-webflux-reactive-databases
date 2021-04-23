#!/usr/bin/env bash

#if [ "$1" = "native" ];
#then
#  ./mvnw clean spring-boot:build-image --projects product-api
#  ./mvnw clean spring-boot:build-image --projects customer-api
#  ./mvnw clean spring-boot:build-image --projects order-api
#  ./mvnw clean spring-boot:build-image --projects client-shell
#else
  ./mvnw clean compile jib:dockerBuild --projects product-api
  ./mvnw clean compile jib:dockerBuild --projects customer-api
  ./mvnw clean compile jib:dockerBuild --projects order-api
  ./mvnw clean compile jib:dockerBuild --projects client-shell
#fi