#!/bin/sh

cd ..
# CAUTION: avoid tests, only for development purposes
./gradlew :BusinessAssistant-opendata:build -x test
#./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-opendata
docker build -t=babcn:opendata-v1.0-SNAPSHOT .

cd ..
docker compose up businessassistantbcn-opendata