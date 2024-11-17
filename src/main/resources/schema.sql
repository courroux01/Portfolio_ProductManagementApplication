CREATE TABLE IF NOT EXISTS Product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    productCode VARCHAR(255) NOT NULL,
    productBrand VARCHAR(255) NOT NULL,
    productQuantity INT NOT NULL,
    productUnitPrice DOUBLE NOT NULL
);
