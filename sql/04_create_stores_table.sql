DROP TABLE IF EXISTS stores;

CREATE TABLE stores (
    store_id INT UNSIGNED AUTO_INCREMENT,
    store_name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    telephone_number VARCHAR(255) NOT NULL,
    PRIMARY KEY (store_id)
);

INSERT INTO stores (store_name, location, telephone_number) VALUES
('ハーモニー楽器 東京店', '東京都渋谷区神南1-2-3', '03-1234-5678'),
('ハーモニー楽器 大阪店', '大阪府大阪市中央区心斎橋筋2-3-4', '06-2345-6789'),
('ハーモニー楽器 名古屋店', '愛知県名古屋市中村区名駅南1-1-1', '052-3456-7890'),
('ハーモニー楽器 福岡店', '福岡県福岡市中央区天神2-2-2', '092-4567-8901'),
('ハーモニー楽器 札幌店', '北海道札幌市中央区北五条西3-3-3', '011-5678-9012');
