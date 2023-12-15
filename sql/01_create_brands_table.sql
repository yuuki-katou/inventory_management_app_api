DROP TABLE IF EXISTS brands;

CREATE TABLE brands (
    brand_id INT UNSIGNED AUTO_INCREMENT,
    brand_name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    PRIMARY KEY (brand_id)
);

INSERT INTO brands (brand_name, address, phone_number) VALUES
('ブランド1', '住所1', '電話番号1'),
('ブランド2', '住所2', '電話番号2'),
('ブランド3', '住所3', '電話番号3'),
('ブランド4', '住所4', '電話番号4'),
('ブランド5', '住所5', '電話番号5');
