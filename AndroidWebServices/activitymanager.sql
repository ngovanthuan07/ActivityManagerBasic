
CREATE TABLE users (
  `id` int NOT NULL PRIMARY KEY auto_increment,
  `username` VARCHAR(150) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `fullname` VARCHAR(150) NULL,
  `photo` longtext DEFAULT NULL,
  `status` int NOT NULL,
  `role` VARCHAR(25) NOT NULL,
  `createddate` TIMESTAMP NULL,
  `modifieddate` TIMESTAMP NULL,
  `createdby` VARCHAR(255) NULL,
  `modifiedby` VARCHAR(255) NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE `activities` (
  `id` int NOT NULL PRIMARY KEY auto_increment,
  `title` text DEFAULT NULL,
  `content` text DEFAULT NULL,
  `costactivities` bigint DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `photo` longtext DEFAULT NULL,
  `datestart` datetime DEFAULT NULL,
  `dateend` datetime DEFAULT NULL,
  `createddate` TIMESTAMP NULL,
  `modifieddate` TIMESTAMP NULL,
  `createdby` VARCHAR(255) NULL,
  `modifiedby` VARCHAR(255) NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

CREATE TABLE `activeregistrations` (
  `id` int NOT NULL PRIMARY KEY auto_increment,
  `activityid` int NOT NULL,
  `userid` int NOT NULL,		
  `timeregistration` TIMESTAMP NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=DYNAMIC;

ALTER TABLE activeregistrations 
ADD CONSTRAINT fk_activeregistrations_activities 
FOREIGN KEY (activityid) REFERENCES activities(id)
ON UPDATE CASCADE
ON DELETE CASCADE;
ALTER TABLE activeregistrations 
ADD CONSTRAINT fk_activeregistrations_users 
FOREIGN KEY (userid) REFERENCES users(id) 
ON UPDATE CASCADE
ON DELETE CASCADE;