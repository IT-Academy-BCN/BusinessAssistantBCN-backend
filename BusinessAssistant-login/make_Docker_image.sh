#!/bin/sh
# CAUTION: avoid tests, only for development purposes
./gradlew :BusinessAssistant-login:build -x test
#./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-login
docker build -t=babcn:login-v1.0-SNAPSHOT .
