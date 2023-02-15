

### User Management

#### Ejecución de tests Unitarios y de Integración  (Uso de TestContainer)

- Es necesario tener docker instalado y con una imagen MongoDB en el repository local, y arrancado para la ejecución
- La ejecución de UserManagementRepositoryTest y de UserManagementIntegrationTest

### _Uso de Mongodb para gestión de usuarios_

##### Instalación de MongoDB en local

- Descarga o **instalación de MongoDB** desde https://www.mongodb.com/download-center/community
- Conectar por consola con MongoDB (por defecto está sin seguridad)
- Ejecutar **use admin** para usar la base de datos **admin**
- Crear usuario de la aplicación admin_businessassistantbcn (véase el script en mongo-init.js)
- Importar datos de prueba con usuario admin_businessassistantbcn (datos en test-data.json)
```
  mongoimport --jsonArray --file test-data.json --collection users mongodb://admin_businessassistantbcn:UhWQQYFVBx95W7@localhost:27017/babcn-users
```  
- **Securización general de accesos a MongoDb**:
  - Detener el servicio MongoDB
  - Habilitar opción 'security' a 'enabled' en el fichero **/etc/mongod.conf**
```    
security:
    authorization: "enabled"
```
   - Arrancar de nuevo el servicio MongoDB
    
##### Uso con Docker

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

