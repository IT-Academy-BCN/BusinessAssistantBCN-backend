#!/bin/sh
## Initial requisites:
##  - Verify Docker is running on system (sudo systemctl start docker) and consul is up

## Mysql
echo "  Registering Mysql on Consul ... "
curl --request PUT --data @register-mysql.json localhost:8500/v1/catalog/register
echo "  "

echo "  Registering Mongodb on Consul ... "
curl --request PUT --data @register-mongodb.json localhost:8500/v1/catalog/register
echo "  "

## To deregister services, execute next commands:
## curl --request PUT --data @deregister-mysql.json localhost:8500/v1/catalog/deregister
## curl --request PUT --data @deregister-mongodb.json localhost:8500/v1/catalog/deregister
