CREATE TABLE IF NOT EXISTS my_searches(
  search_uuid VARCHAR(36) NOT NULL,
  user_uuid VARCHAR(36) NOT NULL,
  search_date DATE NOT NULL,
  search_name VARCHAR(45),
  search_detail VARCHAR(150) DEFAULT NULL,
  search_result JSON,
  PRIMARY KEY (search_uuid)
 );


--CREATE TABLE IF NOT EXISTS user_test (
--    id BIGINT AUTO_INCREMENT PRIMARY KEY,
--    username VARCHAR(255) NOT NULL UNIQUE,
--    password VARCHAR(255) NOT NULL,
--    enabled BOOLEAN NOT NULL
--);
--
--CREATE TABLE IF NOT EXISTS authorities (
--    username VARCHAR(50) NOT NULL,
--    authority VARCHAR(50) NOT NULL,
--    CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES user_test(username)
--);
