
### Creación y arranque de container Docker

Es necesario tener instalado Docker y docker-compose en la máquina. Efectuar los siguientes pasos:

1. **Empaquetado** del proyecto (desde /BusinessAssistantBCN-backend)

```
./gradlew :BusinessAssistant-opendata:build [-x test]
```

2. **Construcción de la imagen** (desde /BusinessAssistant-opendata)
```
docker build -t=babcn:opendata-v1.0-SNAPSHOT .
```

3. **Arranque** de imagen (desde /BusinessAssistantBCN-backend)

```
docker-compose up -d businessassistantbcn-opendata
```

* Acceso a API en **http://[host]:7777/v1/api/[common | opendata]**
* Acceso a Portainer en **http://[host]:9500**. User admin password administrator
