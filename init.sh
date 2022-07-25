#!/bin/sh
DELAY_START=20
now="$(date +'%d-%m-%Y %H:%M:%S:%3N')"

##Init
echo "Date: "${now}
  echo " ======================================================"
  echo ""
  echo "                 Starting Consul Cluster "
  echo ""
  echo " ======================================================"
  echo ""
  echo " Starting consul-server1, consul-server2 and consul-server3 in daemon mode"
docker compose -f consul/consul-compose.yml up -d consul-server1 consul-server2 consul-server3
sleep ${DELAY_START}s
  echo ""
  echo ""

echo " Starting businessassistantbcn-opendata in daemon mode"
docker compose -f consul/consul-compose.yml up -d businessassistantbcn-opendata
sleep ${DELAY_START}s

echo " Starting businessassistantbcn-gencat in daemon mode"
docker compose -f consul/consul-compose.yml up -d businessassistantbcn-gencat
sleep ${DELAY_START}s
echo ""
echo " ================ THE END ;) ==============="

