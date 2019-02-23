CREATE DATABASE IF NOT EXISTS LogisticsApp;
USE LogisticsApp;

DROP TABLE IF EXISTS User;
CREATE TABLE User(
 user_id		INT				NOT NULL AUTO_INCREMENT COMMENT 'user id',
 unique_id		VARCHAR(255) 	NOT NULL 				COMMENT 'unique identifier',
 username		VARCHAR(30)  	NOT NULL 				COMMENT 'user name',
 password		VARCHAR(255)	NOT NULL 				COMMENT 'hash of password',
 create_time 	DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 	DATETIME 								COMMENT 'update time',
 PRIMARY KEY(user_id),
 UNIQUE(username),
 UNIQUE(unique_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'User table';


DROP TABLE IF EXISTS Company;
CREATE TABLE Company(
 company_id				INT				NOT NULL AUTO_INCREMENT COMMENT 'company id',
 user_id				VARCHAR(255) 	 						COMMENT 'reference of User.user_id',
 name					VARCHAR(255)	NOT NULL 				COMMENT 'company name',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(company_id),
 FOREIGN KEY(user_id) REFERENCES User(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Company table';


DROP TABLE IF EXISTS Job;
CREATE TABLE Job(
 job_id					INT				NOT NULL AUTO_INCREMENT COMMENT 'job id',
 user_id				VARCHAR(255) 	 						COMMENT 'reference of User.user_id',
 company_id				INT  	 		NOT NULL				COMMENT 'reference of Company.comapny_id',
 name					VARCHAR(255)	NOT NULL 				COMMENT 'job name',
 tax_id					VARCHAR(255)	 						COMMENT 'tax id',
 goveronment_tax_code	VARCHAR(255)	 						COMMENT 'goveronment tax code',
 salary					INT	 			NOT NULL				COMMENT 'salary',
 tax					INT	 									COMMENT 'tax',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(job_id),
 FOREIGN KEY(user_id) REFERENCES User(user_id),
 FOREIGN KEY(company_id) REFERENCES Company(company_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Job table';
