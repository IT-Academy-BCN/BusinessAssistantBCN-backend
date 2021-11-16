FROM openjdk:15-alpine 

ADD ["BusinessAssistant-login-1.0-SNAPSHOT.jar", "/opt/businessassistant-login/"] 

WORKDIR /opt/businessassistant-login/ 

ENTRYPOINT ["java", "-jar", "BusinessAssistant-login-1.0-SNAPSHOT.jar"] 

EXPOSE 7777