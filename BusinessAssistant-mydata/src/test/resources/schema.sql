DROP SCHEMA IF EXISTS mydata_test;

CREATE DATABASE IF NOT EXISTS mydata_test;

use mydata_test;

CREATE TABLE IF NOT EXISTS my_searches(
  search_uuid VARCHAR(36) NOT NULL,
  user_uuid VARCHAR(36) NOT NULL,
  search_date VARCHAR(10),
  search_name VARCHAR(45),
  search_detail TEXT DEFAULT NULL,
  search_result JSON,
  PRIMARY KEY (search_uuid)
 );