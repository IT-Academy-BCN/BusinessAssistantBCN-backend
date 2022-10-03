
# Consul

Pasos a seguir para inicialización de Consul en la aplicación y gestión de microservicios:

- Arrancar Docker en la máquina (UNIX based)

```
sudo systemctl start docker 
```
- Descargar imagen Docker. Véase [documentación oficial](https://hub.docker.com/_/consul).

```
docker pull consul
```

- Arrancar cluster consul (desde directorio raíz)

```
docker compose -f consul/consul-compose.yml up --remove-orphans
```

- http://localhost:8500 debe mostrar consola de Administración Consul ![Administracion Consul](img/Consul.png)
