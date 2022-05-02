
# Business Assistant - Mydata

##### Spring Boot Actuator

- http<nolink>://localhost:8763/actuator/health (debe responder {"status":"UP"})
- http<nolink>://localhost:8763/actuator/auditevents
- http<nolink>://localhost:8763/actuator/beans
- http<nolink>://localhost:8763/actuator/conditions
- http<nolink>://localhost:8763/actuator/configprops
- http<nolink>://localhost:8763/actuator/env
- http<nolink>://localhost:8763/actuator/heapdump (genera volcado de heap para descarga)
- http<nolink>://localhost:8763/actuator/httptrace
- http<nolink>://localhost:8763/actuator/info
- http<nolink>://localhost:8763/actuator/loggers
- http<nolink>://localhost:8763/actuator/metrics
- http<nolink>://localhost:8763/actuator/mappings
- http<nolink>://localhost:8763/actuator/scheduledtasks
- http<nolink>://localhost:8763/actuator/threaddump

#### Spring Security & Profiles

Perfiles habilitados (véase configuración en application.properties):
- **dev**: deshabilita la seguridad
- **pro**: habilita seguridad por JWT

#### Swagger URL

- Swagger disponible en http://localhost:8763/api-docs/ y http://localhost:8763/swagger-ui-custom.html (con JWT disabled)

### Creación y arranque de container Docker

Es necesario tener instalado Docker y docker-compose en la máquina. Efectuar los siguientes pasos:

1. **Empaquetado** del proyecto (desde /BusinessAssistantBCN-backend)

```
./gradlew :BusinessAssistant-mydata:build [-x test]
```

2. **Construcción de la imagen** (desde /BusinessAssistant-mydata)
```
docker build -t=babcn:mydata-v1.0-SNAPSHOT .
```

3. **Arranque** de imagen (desde /BusinessAssistantBCN-backend)

```
docker-compose up -d businessassistantbcn-mydata
```

* Acceso a API en **http://[host]:7777/businessassistantbnc/v1/api/mydata**
