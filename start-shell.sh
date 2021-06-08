#!/usr/bin/env bash

echo
echo "Starting client-shell..."

docker run -it --rm --name client-shell \
  -e PRODUCT_API_HOST=product-api -e CUSTOMER_API_HOST=customer-api -e ORDER_API_HOST=order-api \
  --network spring-webflux-reactive-databases_default \
  ivanfranchin/client-shell:1.0.0
