#!/usr/bin/env bash

echo
echo "Starting the environment shutdown"
echo "================================="

echo
echo "Removing containers"
echo "-------------------"
docker rm -fv mysql mongodb postgres cassandra

echo
echo "Removing network"
echo "----------------"
docker network rm spring-webflux-reactive-databases_default

echo
echo "Environment shutdown successfully"
echo "================================="
echo