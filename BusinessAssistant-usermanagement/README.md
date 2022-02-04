

### User Management



#### Mongodb database para gestión de usuarios

Proceso de arranque de MongoDB (requiere instalación previa de Docker y docker-compose):

- Descarga de imagen MongoDB

```
docker pull mongo
```
- Ejecución desde directorio raíz:

```
docker-compose up -d mongodb
```
- Cadena de conexión:
```
mongodb://username:password@host:port
```



### Creación y arranque de container Docker

Es necesario tener instalado Docker y docker-compose en la máquina. Efectuar los siguientes pasos:

1. **Empaquetado** del proyecto (desde /BusinessAssistantBCN-backend)

```
./gradlew :BusinessAssistant-usermanagement:build [-x test]
```

2. **Construcción de la imagen** (desde /BusinessAssistant-usermanagement)
```
docker build -t=babcn:usermanagement-v1.0-SNAPSHOT .
```

3. **Arranque** de imagen (desde /BusinessAssistantBCN-backend)

```
docker-compose up -d businessassistantbcn-usermanagement
```

* Acceso a API en **http://[host]:7778/v1/api/usermanagement**
* Acceso a Portainer en **http://[host]:9500**. User admin password administrator

