#!/bin/sh

cd ..
./gradlew :BusinessAssistant-gencat:build -x test

cd BusinessAssistant-gencat
docker build -t=babcn:gencat-v1.0-SNAPSHOT .

cd ..
docker-compose up businessassistantbcn-gencat