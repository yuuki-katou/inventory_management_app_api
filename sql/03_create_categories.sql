DROP TABLE IF EXISTS categories;

-- カテゴリテーブルの作成
CREATE TABLE categories (
    category_id INT UNSIGNED AUTO_INCREMENT,
    category_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (category_id)
);

INSERT INTO categories (category_name) VALUES
('エレキギター'),
('アコースティックギター'),
('ベースギター'),
('クラシックギター'),
('エフェクター'),
('アンプ'),
('シールド'),
('その他');
