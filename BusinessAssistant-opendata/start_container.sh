#!/bin/sh

cd ..
./gradlew :BusinessAssistant-opendata:build -x test

cd BusinessAssistant-opendata
docker build -t=babcn:opendata-v1.0-SNAPSHOT .

cd ..
docker-compose up businessassistantbcn-opendata