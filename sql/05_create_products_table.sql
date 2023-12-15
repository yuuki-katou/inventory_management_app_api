DROP TABLE IF EXISTS products;

CREATE TABLE products (
    product_id INT UNSIGNED AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    brand_id INT UNSIGNED NOT NULL,
    category_id INT UNSIGNED NOT NULL,
    model_number VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    stock_quantity INT UNSIGNED NOT NULL,
    condition_id INT UNSIGNED NOT NULL,
    description TEXT,
    image VARCHAR(255),
    store_id INT UNSIGNED NOT NULL,
    date_added DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_updated DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by_program_id VARCHAR(255),
    updated_by_program_id VARCHAR(255),
    PRIMARY KEY (product_id),
    FOREIGN KEY (brand_id) REFERENCES brands(brand_id),
    FOREIGN KEY (store_id) REFERENCES stores(store_id),
    FOREIGN KEY (condition_id) REFERENCES conditions(condition_id),
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

INSERT INTO products (
    product_name,
    brand_id,
    category_id,
    model_number,
    price,
    stock_quantity,
    condition_id,
    description,
    image,
    store_id,
    date_added,
    last_updated
)
VALUES
('エレキギター モデルX', 1, 1, 'X100', 50000, 5, 1, '高品質のエレキギター', 'image_url_1', 1, '2023-01-01 10:00:00', '2023-01-01 10:00:00'),
('エレキギター モデルX', 1, 1, 'X100', 52000, 3, 2, '限定版エレキギター', 'image_url_2', 2, '2023-02-01 10:00:00', '2023-02-01 10:00:00'),
('アコースティックギター モデルY', 2, 2, 'Y200', 30000, 2, 2, 'クラシックなデザイン', 'image_url_3', 3, '2023-03-01 10:00:00', '2023-03-01 10:00:00'),
('アコースティックギター モデルY', 2, 2, 'Y200', 31000, 4, 3, '高品質な木材使用', 'image_url_4', 4, '2023-04-01 10:00:00', '2023-04-01 10:00:00'),
('ベースギター モデルZ', 3, 3, 'Z300', 48000, 4, 1, '限定版ベースギター', 'image_url_6', 1, '2023-03-01 10:00:00', '2023-03-01 10:00:00'),
('ベースギター モデルZ', 3, 3, 'Z300', 46000, 2, 2, '特別デザインのベースギター', 'image_url_7', 2, '2023-03-02 10:00:00', '2023-03-02 10:00:00'),
('エフェクター Alpha', 4, 5, 'A400', 16000, 5, 1, '高性能エフェクター', 'image_url_8', 3, '2023-04-01 10:00:00', '2023-04-01 10:00:00'),
('エフェクター Alpha', 4, 5, 'A400', 15500, 8, 3, 'コンパクトサイズのエフェクター', 'image_url_9', 4, '2023-04-02 10:00:00', '2023-04-02 10:00:00'),
('アンプ Beta', 5, 6, 'B500', 26000, 6, 1, 'プロ用の高出力アンプ', 'image_url_10', 1, '2023-05-01 10:00:00', '2023-05-01 10:00:00'),
('アンプ Beta', 5, 6, 'B500', 24000, 10, 4, '初心者向けのアンプ', 'image_url_11', 2, '2023-05-02 10:00:00', '2023-05-02 10:00:00');
