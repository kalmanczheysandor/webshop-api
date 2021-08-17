
CREATE TABLE IF NOT EXISTS t_product (
`id` INT AUTO_INCREMENT,
`product_name` VARCHAR(255) NOT NULL,
`price_net` FLOAT NOT NULL DEFAULT 0.0,
`price_vat` TINYINT NOT NULL DEFAULT 0,
`description` TEXT NOT NULL DEFAULT '',
`is_active` TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (id),
CONSTRAINT is_active_01 CHECK (is_active=0 or is_active=1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS t_customer (
`id` INT AUTO_INCREMENT,
`identifier` VARCHAR(255) NOT NULL,
`password` VARCHAR(255) NOT NULL,
`email` VARCHAR(255) NOT NULL,
`phone` VARCHAR(255) NOT NULL,
`firstname` VARCHAR(255) NOT NULL,
`lastname` VARCHAR(255) NOT NULL,
`is_active` TINYINT NOT NULL DEFAULT 0,
PRIMARY KEY (id),
CONSTRAINT is_active_01 CHECK (is_active=0 or is_active=1)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS t_customer_address (
`id` INT AUTO_INCREMENT,
`country` VARCHAR(255) NOT NULL,
`city` VARCHAR(255) NOT NULL,
`street` VARCHAR(255) NOT NULL,
`postcode` VARCHAR(255) NOT NULL,
`customer_id` INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (customer_id) REFERENCES t_customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS t_order (
`id` INT AUTO_INCREMENT,
`price_total_net` FLOAT NOT NULL DEFAULT 0.0,
`price_total_gross` FLOAT NOT NULL DEFAULT 0.0,
`delivery_status` VARCHAR(255) NOT NULL,
`customer_id` INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (customer_id) REFERENCES t_customer(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE IF NOT EXISTS t_order_item (
`id` INT AUTO_INCREMENT,
`price_total_net` FLOAT NOT NULL DEFAULT 0.0,
`price_total_gross` FLOAT NOT NULL DEFAULT 0.0,
`quantity` INT NOT NULL DEFAULT 0,
`order_id` INT NOT NULL,
`product_id` INT NOT NULL,
PRIMARY KEY (id),
FOREIGN KEY (order_id) REFERENCES t_order(id),
FOREIGN KEY (product_id) REFERENCES t_product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
