DROP SCHEMA IF EXISTS businessassistantbcndb;

CREATE DATABASE IF NOT EXISTS businessassistantbcndb;

use businessassistantbcndb;

CREATE TABLE IF NOT EXISTS my_searches(
  search_uuid VARCHAR(36) NOT NULL,
  user_uuid VARCHAR(36) NOT NULL,
  search_date VARCHAR(10) NOT NULL,
  search_name VARCHAR(45) NOT NULL,
  search_detail TEXT DEFAULT NULL,
  search_result JSON,
  PRIMARY KEY (search_uuid)
 );
 
 