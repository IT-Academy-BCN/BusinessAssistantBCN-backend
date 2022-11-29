
# Business Assistant - Gencat

### Endpoints disponibles

- http<nolink>://localhost:8762/businessassistantbcn/api/v1/gencat/test

##### Spring Boot Actuator

- http<nolink>://localhost:8762/actuator/health (response should be {"status":"UP"})
- http<nolink>://localhost:8762/actuator/auditevents
- http<nolink>://localhost:8762/actuator/beans
- http<nolink>://localhost:8762/actuator/conditions
- http<nolink>://localhost:8762/actuator/configprops
- http<nolink>://localhost:8762/actuator/env
- http<nolink>://localhost:8762/actuator/heapdump 
- http<nolink>://localhost:8762/actuator/httptrace
- http<nolink>://localhost:8762/actuator/info
- http<nolink>://localhost:8762/actuator/loggers
- http<nolink>://localhost:8762/actuator/metrics
- http<nolink>://localhost:8762/actuator/mappings
- http<nolink>://localhost:8762/actuator/scheduledtasks
- http<nolink>://localhost:8762/actuator/threaddump

### Creación y arranque de container Docker

Es necesario tener instalado Docker y docker-compose en la máquina. Efectuar los siguientes pasos:

1. **Empaquetado** del proyecto (desde /BusinessAssistantBCN-backend)

```
./gradlew :BusinessAssistant-gencat:build [-x test]
```

2. **Construcción de la imagen** (desde /BusinessAssistant-gencat)
```
docker build -t=babcn:gencat-v1.0-SNAPSHOT .
```

3. **Arranque** de imagen (desde /BusinessAssistantBCN-backend)

```
docker-compose up -d businessassistantbcn-gencat
```

* Acceso a API en **http://[host]:7777/v1/api/[common | gencat]**
* Acceso a Portainer en **http://[host]:9500**. User admin password administrator