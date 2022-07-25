#!/bin/sh
cd ..
# CAUTION: avoid tests, only for development purposes
./gradlew :BusinessAssistant-gencat:build -x test
#./gradlew :BusinessAssistant-opendata:build

cd BusinessAssistant-gencat
docker build -t=babcn:gencat-v1.0-SNAPSHOT .
