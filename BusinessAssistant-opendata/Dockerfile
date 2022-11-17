FROM bellsoft/liberica-openjdk-alpine:17.0.1
RUN apk add -U consul
# ./consul agent -data-dir=/tmp/consul -join=172.17.0.2 -bind=172.17.0.3 -client=172.17.0.3
ADD ["build/libs/BusinessAssistant-opendata-1.0-BETA.jar", "/opt/BusinessAssistant-opendata/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-opendata/BusinessAssistant-opendata-1.0-BETA.jar"]
EXPOSE 7777