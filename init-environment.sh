#!/usr/bin/env bash

source scripts/my-functions.sh

CASSANDRA_VERSION="5.0.2"
MYSQL_VERSION="9.0.1"
MONGO_VERSION="7.0.14"
POSTGRES_VERSION="17.0"

echo
echo "Starting environment"
echo "===================="

echo
echo "Creating network"
echo "----------------"
docker network create spring-webflux-reactive-databases_default

echo
echo "Starting Cassandra"
echo "------------------"
docker run -d --name cassandra \
  -p 9042:9042 \
  -p 7199:7199 \
  -p 9160:9160 \
  --restart=unless-stopped \
  --network=spring-webflux-reactive-databases_default \
  --health-cmd="cqlsh < /dev/null" \
  cassandra:${CASSANDRA_VERSION}

echo
echo "Starting MySQL"
echo "--------------"
docker run -d --name mysql \
  -p 3306:3306 \
  -e MYSQL_DATABASE=notificationdb \
  -e MYSQL_ROOT_PASSWORD=secret \
  --restart=unless-stopped \
  --network=spring-webflux-reactive-databases_default \
  --health-cmd="mysqladmin ping -u root -p$${MYSQL_ROOT_PASSWORD}" \
  mysql:${MYSQL_VERSION}

echo
echo "Starting MongoDB"
echo "----------------"
docker run -d --name mongodb \
  -p 27017:27017 \
  --restart=unless-stopped \
  --network=spring-webflux-reactive-databases_default \
  --health-cmd="echo 'db.stats().ok' | mongosh localhost:27017/bookdb --quiet" \
  mongo:${MONGO_VERSION}

echo
echo "Starting Postgres"
echo "-----------------"
docker run -d --name postgres \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=customerdb \
  --restart=unless-stopped \
  --network=spring-webflux-reactive-databases_default \
  --health-cmd="pg_isready -U postgres" \
  postgres:${POSTGRES_VERSION}

echo
wait_for_container_log "postgres" "port 5432"

echo
wait_for_container_log "mongodb" "Waiting for connections"

echo
wait_for_container_log "mysql" "port: 3306"

echo
wait_for_container_log "cassandra" "Created default superuser role"

echo
echo "Environment Up and Running"
echo "=========================="
echo