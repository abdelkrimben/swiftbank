CREATE TABLE Bank (
                      bank_id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(100) NOT NULL
);

CREATE TABLE Product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         bank_id INT NOT NULL,
                         name VARCHAR(100) NOT NULL,
                         type VARCHAR(50) NOT NULL,
                         description VARCHAR(100),
                         interest_rate DECIMAL(5, 2) NOT NULL,
                         FOREIGN KEY (bank_id) REFERENCES Bank(bank_id) ON DELETE CASCADE
);

CREATE TABLE clients (
                         client_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         email VARCHAR(255) UNIQUE NOT NULL
);
