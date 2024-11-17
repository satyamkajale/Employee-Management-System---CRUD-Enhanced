CREATE DATABASE IF NOT EXISTS `employee_directory`;
USE `employee_directory`;

-- Table structure for table `employee`
DROP TABLE IF EXISTS `employee`;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `is_deleted` BOOLEAN DEFAULT FALSE,     -- Soft delete column
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Timestamp for updates
  `deleted_at` TIMESTAMP DEFAULT NULL,    -- Timestamp for deletion
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- Data for table `employee`
INSERT INTO `employee` VALUES 
    (1, 'Leslie', 'Andrews', 'leslie@luv2code.com', FALSE, CURRENT_TIMESTAMP, NULL),
    (2, 'Emma', 'Baumgarten', 'emma@luv2code.com', FALSE, CURRENT_TIMESTAMP, NULL),
    (3, 'Avani', 'Gupta', 'avani@luv2code.com', FALSE, CURRENT_TIMESTAMP, NULL),
    (4, 'Yuri', 'Petrov', 'yuri@luv2code.com', FALSE, CURRENT_TIMESTAMP, NULL),
    (5, 'Juan', 'Vega', 'juan@luv2code.com', FALSE, CURRENT_TIMESTAMP, NULL);

-- Create Employee_History table for tracking changes
CREATE TABLE `Employee_History` (
    `history_id` SERIAL PRIMARY KEY,
    `employee_id` INT,                      -- Reference to the employee
    `first_name` VARCHAR(50),
    `last_name` VARCHAR(50),
    `email` VARCHAR(100),
    `modified_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Timestamp for the modification
);
