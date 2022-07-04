
# Business Assistant Barcelona Backend Project


### Versiones necesarias

- Gradle 7.4.1
- Java 17

### Módulos del proyecto

- [BusinessAssistant-gateway](BusinessAssistant-gateway/README.md). 
- [BusinessAssistant-login](BusinessAssistant-login/README.md). Microservicio para manejo de login
- [BusinessAssistant-mydata](BusinessAssistant-mydata/README.md). 
- [BusinessAssistant-opendata](BusinessAssistant-opendata/README.md). Microservicio para manejo de información de [Opendata](https://opendata-ajuntament.barcelona.cat/es/api-cataleg)
- - [BusinessAssistant-opendata](BusinessAssistant-opendata/README.md). Microservicio para manejo de información de [datos.gob.es](https://datos.gob.es/es/catalogo)
- [BusinessAssistant-usermanagement](BusinessAssistant-usermanagement/README.md). Microservicio para gestión de usuarios.

## Inicialización de contenedores 

### Arranque de microservicios en contenedores Docker

- Construcción del contenedor (UNIX based)

```
./start_container.sh
```

### MongoDB

La configuración para la inicialización de la base de datos Mongo está incluida en docker-compose.yml (**importante**: docker-compose.yml aún
contiene rutas hard-coded a los scripts de inicialización de BD -tanto MongoDB como Mysql-. No olvide sustituirlas por las suyas propias :(  
La comprobación de inicialización es válida aún si la base de datos se levanta en host (sin container), aunque será necesario 
ejecutar manualmente las instrucciones del script BusinessAssistant-usermanagement/src/main/resources/scripts-mongodb/mongo-init.js para creación 
de usuarios, ya que el arranque con Docker incluye la creación de configuración inicial (especialmente la creación de usuarios).

#### Comandos

- Arranque instancia MongoDB (desde directorio raíz, versión en docker-compose.yml):
```
docker compose up -d businessassistantbcn-mongodb
```

- Entrar en contenedor accediendo a consola bash (para conectar por consola Mongo shell, p.ej.):
```
docker exec -it [containerID] bash
```

- Verificación de inicialización Mongo (desde dentro del contenedor): ejecutar desde cmd 
```
mongosh --username [user] --password [pwd]  --authenticationDatabase babcn-users babcn-users --eval "db.adminCommand({ listDatabases: 1 })"
```

- Verificación de inicialización Mongo (desde fuera del contenedor):

```
docker exec -it [containerID] mongosh --username admin_businessassistantbcn --password UhWQQYFVBx95W7  --authenticationDatabase babcn-users babcn-users --eval "db.adminCommand({ listDatabases: 1 })"
```


### Consul

Pasos a seguir para inicialización de Consul en la aplicación y gestión de microservicios:

- Arrancar Docker en la máquina (UNIX based)

```
sudo systemctl start docker 
```
- Descargar imagen Docker. Véase [documentación oficial](https://hub.docker.com/_/consul).

```
docker pull consul
```

- Arrancar contenedor consul (desde directorio raíz)

```
docker-compose up consul-server1
```

- http://localhost:8500 debe mostrar consola de Administración Consul ![Administracion Consul](img/Consul.png)


<hr/>

[![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT_EN.md) 
 [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT_ES.md) 
  [![Contributor Covenant](https://img.shields.io/badge/Contributor%20Covenant-v2.0%20adopted-ff69b4.svg)](CODE_OF_CONDUCT_CA.md) 
