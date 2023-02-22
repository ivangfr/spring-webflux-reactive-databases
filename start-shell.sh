#!/usr/bin/env bash

docker run -it --rm --name client-shell \
  -e PRODUCT_API_HOST=product-api -e CUSTOMER_API_HOST=customer-api \
  -e ORDER_API_HOST=order-api -e NOTIFICATION_API_HOST=notification-api \
  --network spring-webflux-reactive-databases_default \
  ivanfranchin/client-shell:1.0.0
