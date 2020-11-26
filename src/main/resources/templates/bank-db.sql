-- `bank-db`.clients definition
CREATE TABLE `clients` (
  `id` int NOT NULL AUTO_INCREMENT,
  `business_name` varchar(255) DEFAULT NULL,
  `cuil` varchar(255) DEFAULT NULL,
  `dni` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- `bank-db`.users definition

-- `bank-db`.users definition

-- `bank-db`.users definition

CREATE TABLE `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enabled` tinyint(1) NOT NULL,
  `type` varchar(255) DEFAULT NULL,
  `client_id` int DEFAULT NULL,
  `password_reset` bit(1) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `FKqvykjc6027qa8n5es37omu3xs` (`client_id`),
  CONSTRAINT `FKqvykjc6027qa8n5es37omu3xs` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- `bank-db`.accounts definition

CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_type` varchar(255) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `balance` decimal(19,2) DEFAULT NULL,
  `identification_number` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `overdraft` decimal(19,2) DEFAULT NULL,
  `client_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgymog7firrf8bnoiig61666ob` (`client_id`),
  CONSTRAINT `FKgymog7firrf8bnoiig61666ob` FOREIGN KEY (`client_id`) REFERENCES `clients` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- `bank-db`.authorities definition
CREATE TABLE `authorities` (
  `id` int(11) not null auto_increment,
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- CREATE TABLE `authorities` (
--  `username` varchar(50) NOT NULL,
--  `authority` varchar(50) NOT NULL,
--  UNIQUE KEY `ix_auth_username` (`username`,`authority`),
--  CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- `bank-db`.providers definition

CREATE TABLE `providers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `provider_code` varchar(255) DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo3ed5qpwr8jor5r64sbtgoscp` (`account_id`),
  CONSTRAINT `FKo3ed5qpwr8jor5r64sbtgoscp` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- `bank-db`.transactions definition

CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `cash` bit(1) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `transaction_type` varchar(255) DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `operation_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20w7wsg13u9srbq3bd7chfxdh` (`account_id`),
  CONSTRAINT `FK20w7wsg13u9srbq3bd7chfxdh` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


-- `bank-db`.payments definition

CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(19,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `electronic_code` varchar(255) DEFAULT NULL,
  `paid` bit(1) DEFAULT NULL,
  `provider_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc8w6394u7cyjy4w1uob1iiupx` (`provider_id`),
  CONSTRAINT `FKc8w6394u7cyjy4w1uob1iiupx` FOREIGN KEY (`provider_id`) REFERENCES `providers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;