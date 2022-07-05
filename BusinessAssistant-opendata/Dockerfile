FROM bellsoft/liberica-openjdk-alpine:17.0.1
ADD ["build/libs/BusinessAssistant-opendata-1.0-SNAPSHOT.jar", "/opt/BusinessAssistant-opendata/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-opendata/BusinessAssistant-opendata-1.0-SNAPSHOT.jar"]
EXPOSE 7777