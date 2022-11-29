#!/bin/bash

echo "########### Loading data to Mysql ###########"

mysql --defaults-file=/tmp/data/defaults-file businessassistantbcndb < /docker-entrypoint-initdb.d/schema.sql
mysql --defaults-file=/tmp/data/defaults-file businessassistantbcndb < /tmp/data/test-data.sql