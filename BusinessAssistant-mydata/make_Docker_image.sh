#!/bin/sh
# CAUTION: avoid tests, only for development purposes
./gradlew :BusinessAssistant-mydata:build -x test
#./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-mydata
docker build -t=babcn:mydata-v1.0-SNAPSHOT .
