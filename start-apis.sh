#!/usr/bin/env bash

source scripts/my-functions.sh

echo
echo "Starting product-api..."

docker run -d --rm --name product-api \
  -p 9080:9080 \
  -e MONGODB_HOST=mongodb \
  --network spring-webflux-reactive-databases_default \
  --health-cmd="curl -f http://localhost:9080/actuator/health || exit 1" \
  ivanfranchin/product-api:1.0.0

echo
echo "Starting customer-api..."

docker run -d --rm --name customer-api \
  -p 9081:9081 \
  -e POSTGRES_HOST=postgres \
  --network spring-webflux-reactive-databases_default \
  --health-cmd="curl -f http://localhost:9081/actuator/health || exit 1" \
  ivanfranchin/customer-api:1.0.0

echo
echo "Starting order-api..."

docker run -d --rm --name order-api \
  -p 9082:9082 \
  -e CASSANDRA_HOST=cassandra -e PRODUCT_API_HOST=product-api -e CUSTOMER_API_HOST=customer-api \
  --network spring-webflux-reactive-databases_default \
  --health-cmd="curl -f http://localhost:9082/actuator/health || exit 1" \
  ivanfranchin/order-api:1.0.0

echo
echo "Starting notification-api..."

docker run -d --rm --name notification-api \
  -p 9083:9083 \
  -e MYSQL_HOST=mysql -e ORDER_API_HOST=order-api -e CUSTOMER_API_HOST=customer-api \
  --network spring-webflux-reactive-databases_default \
  --health-cmd="curl -f http://localhost:9083/actuator/health || exit 1" \
  ivanfranchin/notification-api:1.0.0

echo
wait_for_container_log "product-api" "Started"

echo
wait_for_container_log "customer-api" "Started"

echo
wait_for_container_log "order-api" "Started"

echo
wait_for_container_log "notification-api" "Started"

echo
echo "APIs are Up and Running"
echo "======================="
echo