#!/bin/sh
cd ..
# CAUTION: avoid tests, only for development purposes
./gradlew :BusinessAssistant-usermanagement:build -x test
#./gradlew :BusinessAssistant-usermanagement:build

cd BusinessAssistant-usermanagement
docker build -t=babcn:usermanagement-v1.0-SNAPSHOT .
