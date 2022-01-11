
# Business Assistant - Opendata

### Endpoints disponibles

- http<nolink>://localhost:8762/v1/api/opendata/test
- http<nolink>://localhost:8762/v1/api/opendata/test-reactive

##### Spring Boot Actuator

- http<nolink>://localhost:8762/actuator/health (debe responder {"status":"UP"})
- http<nolink>://localhost:8762/actuator/auditevents
- http<nolink>://localhost:8762/actuator/beans
- http<nolink>://localhost:8762/actuator/conditions
- http<nolink>://localhost:8762/actuator/configprops
- http<nolink>://localhost:8762/actuator/env
- http<nolink>://localhost:8762/actuator/heapdump (genera volcado de heap para descarga)
- http<nolink>://localhost:8762/actuator/httptrace
- http<nolink>://localhost:8762/actuator/info
- http<nolink>://localhost:8762/actuator/loggers
- http<nolink>://localhost:8762/actuator/metrics
- http<nolink>://localhost:8762/actuator/mappings
- http<nolink>://localhost:8762/actuator/scheduledtasks
- http<nolink>://localhost:8762/actuator/threaddump

### Swagger URL

- http://localhost:8762/swagger-ui/index.html

### Reactive Programming Samples

- Véase HttpClientHelper. Spring WebClient object en lugar de RESTTemplate (pronto deprecada)
- Véase endpoint /test-reactive.

### Testing

- Comando <b>[./gradlew | gradle.bat] :BusinessAssistant-opendata:test </b>

### h2 Database

- Consola accesible en http://localhost:8762/h2-console
- Carga automática al arrancar contexto de files sql de test schema.sql y data.sql
