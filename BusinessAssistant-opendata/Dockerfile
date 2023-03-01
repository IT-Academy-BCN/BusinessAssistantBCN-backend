FROM bellsoft/liberica-openjdk-alpine:17.0.1
RUN apk add -U consul
ADD ["build/libs/BusinessAssistant-opendata-1.0.4-BETA.jar", "/opt/BusinessAssistant-opendata/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-opendata/BusinessAssistant-opendata-1.0.4-BETA.jar"]
EXPOSE 7777
