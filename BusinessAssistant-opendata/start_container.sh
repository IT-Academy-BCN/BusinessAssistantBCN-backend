#!/bin/sh

cd ..
./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-opendata
docker build -t=babcn:opendata-v1.0-SNAPSHOT .

cd ..
docker-compose up businessassistantbcn-opendata