#!/bin/bash

echo "########### Loading data to Mongo DB ###########"
mongoimport --jsonArray --db babcn-users --collection users --file /tmp/data/test-data.json