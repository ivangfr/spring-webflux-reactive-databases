#!/usr/bin/env bash

DOCKER_IMAGE_PREFIX="ivanfranchin"
APP_VERSION="1.0.0"

PRODUCT_API_APP_NAME="product-api"
CUSTOMER_API_APP_NAME="customer-api"
ORDER_API_APP_NAME="order-api"
NOTIFICATION_API_APP_NAME="notification-api"
CLIENT_SHELL_APP_NAME="client-shell"

PRODUCT_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${PRODUCT_API_APP_NAME}:${APP_VERSION}"
CUSTOMER_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${CUSTOMER_API_APP_NAME}:${APP_VERSION}"
ORDER_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${ORDER_API_APP_NAME}:${APP_VERSION}"
NOTIFICATION_API_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${NOTIFICATION_API_APP_NAME}:${APP_VERSION}"
CLIENT_SHELL_DOCKER_IMAGE_NAME="${DOCKER_IMAGE_PREFIX}/${CLIENT_SHELL_APP_NAME}:${APP_VERSION}"

SKIP_TESTS="true"

./mvnw clean spring-boot:build-image --projects "$PRODUCT_API_APP_NAME" -DskipTests="$SKIP_TESTS" -Dspring-boot.build-image.imageName="$PRODUCT_API_DOCKER_IMAGE_NAME"
./mvnw clean spring-boot:build-image --projects "$CUSTOMER_API_APP_NAME" -DskipTests="$SKIP_TESTS" -Dspring-boot.build-image.imageName="$CUSTOMER_API_DOCKER_IMAGE_NAME"
./mvnw clean spring-boot:build-image --projects "$ORDER_API_APP_NAME" -DskipTests="$SKIP_TESTS" -Dspring-boot.build-image.imageName="$ORDER_API_DOCKER_IMAGE_NAME"
./mvnw clean spring-boot:build-image --projects "$NOTIFICATION_API_APP_NAME" -DskipTests="$SKIP_TESTS" -Dspring-boot.build-image.imageName="$NOTIFICATION_API_DOCKER_IMAGE_NAME"
./mvnw clean spring-boot:build-image --projects "$CLIENT_SHELL_APP_NAME" -DskipTests="$SKIP_TESTS" -Dspring-boot.build-image.imageName="$CLIENT_SHELL_DOCKER_IMAGE_NAME"