#!/bin/sh
# CAUTION: avoid tests, only for development purposes
#./gradlew :BusinessAssistant-opendata:build -x test
./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-opendata
docker build -t=itacademybcn/businessassistant-bcn:opendata-v1.0.2-BETA .
