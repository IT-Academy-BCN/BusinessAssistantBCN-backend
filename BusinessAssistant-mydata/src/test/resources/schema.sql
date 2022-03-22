

CREATE TABLE IF NOT EXISTS my_searches(
  search_uuid VARCHAR(36) NOT NULL,
  user_uuid VARCHAR(36) NOT NULL,
  search_date DATE NOT NULL,
  search_name VARCHAR(45),
  search_detail VARCHAR(150) DEFAULT NULL,
  search_result JSON,
  PRIMARY KEY (search_uuid)
 );