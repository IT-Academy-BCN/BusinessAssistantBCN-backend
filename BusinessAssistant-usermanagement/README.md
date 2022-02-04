

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
