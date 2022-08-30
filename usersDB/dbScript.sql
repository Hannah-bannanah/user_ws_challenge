CREATE DATABASE IF NOT EXISTS technest;

USE technest;

CREATE TABLE users (
	user_id 	mediumint PRIMARY KEY AUTO_INCREMENT,
	first_name VARCHAR(50) NOT NULL,
	last_name_1 VARCHAR(50) NOT NULL,
	last_name_2 VARCHAR(50),
	email VARCHAR(128) NOT NULL,
	password VARCHAR(100) NOT NULL,
	UNIQUE (email)
);

-- CREATE USER 'utechnest'@'localhost' IDENTIFIED BY 'utechnest';
-- GRANT ALL PRIVILEGES ON technest.* TO 'utechnest'@'localhost';
CREATE USER 'utechnest'@'%' IDENTIFIED BY 'utechnest';
GRANT ALL PRIVILEGES ON technest.* TO 'utechnest'@'%';