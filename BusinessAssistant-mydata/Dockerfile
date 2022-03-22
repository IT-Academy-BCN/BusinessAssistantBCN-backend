FROM openjdk:17-alpine
ADD ["build/libs/BusinessAssistant-mydata-1.0-SNAPSHOT.jar", "/opt/BusinessAssistant-mydata/"]
ENTRYPOINT ["java", "-jar", "/opt/BusinessAssistant-mydata/BusinessAssistant-mydata-1.0-SNAPSHOT.jar"]
EXPOSE 7777