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

DROP TABLE IF EXISTS Admin;
CREATE TABLE Admin(
 admin_id		INT				NOT NULL AUTO_INCREMENT COMMENT 'admin id',
 user_id 		INT				NOT NULL 				COMMENT 'reference of User.user_id',
 create_time 	DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 	DATETIME 								COMMENT 'update time',
 PRIMARY KEY(admin_id), 
 FOREIGN KEY(user_id) REFERENCES User(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Admin table';


DROP TABLE IF EXISTS Employee;
CREATE TABLE Employee(
 employee_id	INT				NOT NULL AUTO_INCREMENT COMMENT 'employee id',
 user_id 		INT				NOT NULL 				COMMENT 'reference of User.user_id',
 forename		VARCHAR(255)	NOT NULL 				COMMENT 'forename',
 surname		VARCHAR(255)	NOT NULL 				COMMENT 'surname',
 create_time 	DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 	DATETIME 								COMMENT 'update time',
 PRIMARY KEY(employee_id),
 FOREIGN KEY(user_id) REFERENCES User(user_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Employee table';

DROP TABLE IF EXISTS Company;
CREATE TABLE Company(
 company_id				INT				NOT NULL AUTO_INCREMENT COMMENT 'company id',
 admin_id				INT 	 								COMMENT 'reference of Admin.admin_id',
 name					VARCHAR(255)	NOT NULL 				COMMENT 'company name',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(company_id),
 FOREIGN KEY(admin_id) REFERENCES Admin(admin_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Company table';


DROP TABLE IF EXISTS Job;
CREATE TABLE Job(
 job_id					INT				NOT NULL AUTO_INCREMENT COMMENT 'job id',
 employee_id			INT 	 								COMMENT 'reference of Employee.employee_id',
 company_id				INT  	 		NOT NULL				COMMENT 'reference of Company.comapny_id',
 name					VARCHAR(255)	NOT NULL 				COMMENT 'job name',
 tax_id					VARCHAR(255)	 						COMMENT 'tax id',
 goveronment_tax_code	VARCHAR(255)	 						COMMENT 'goveronment tax code',
 salary					INT	 			NOT NULL				COMMENT 'salary',
 tax					INT	 									COMMENT 'tax',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(job_id),
 FOREIGN KEY(employee_id) REFERENCES Employee(employee_id),
 FOREIGN KEY(company_id) REFERENCES Company(company_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Job table';

DROP TABLE IF EXISTS Asset;
CREATE TABLE Asset(
 asset_id				INT				NOT NULL AUTO_INCREMENT COMMENT 'asset id',
 company_id				INT  	 		NOT NULL				COMMENT 'reference of Company.comapny_id',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(asset_id),
 FOREIGN KEY(company_id) REFERENCES Company(company_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Asset table';

DROP TABLE IF EXISTS Property;
CREATE TABLE Property(
 property_id			INT				NOT NULL AUTO_INCREMENT COMMENT 'property id',
 asset_id				INT  	 		NOT NULL				COMMENT 'reference of Asset.asset_id',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(property_id),
 FOREIGN KEY(asset_id) REFERENCES Asset(asset_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Porperty table';


DROP TABLE IF EXISTS Device;
CREATE TABLE Device(
 device_id				INT				NOT NULL AUTO_INCREMENT COMMENT 'device id',
 asset_id				INT  	 		NOT NULL				COMMENT 'reference of Asset.asset_id',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(device_id),
 FOREIGN KEY(asset_id) REFERENCES Asset(asset_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Device table';

DROP TABLE IF EXISTS Vehicle;
CREATE TABLE Vehicle(
 vehicle_id				INT				NOT NULL AUTO_INCREMENT COMMENT 'vehicle id',
 asset_id				INT  	 		NOT NULL				COMMENT 'reference of Asset.asset_id',
 create_time 			DATETIME 		NOT NULL 				COMMENT 'create time',
 update_time 			DATETIME 								COMMENT 'update time',
 PRIMARY KEY(vehicle_id),
 FOREIGN KEY(asset_id) REFERENCES Asset(asset_id)
)ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT 'Vehicle table';