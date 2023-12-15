DROP TABLE IF EXISTS conditions;

CREATE TABLE conditions (
    condition_id INT UNSIGNED AUTO_INCREMENT,
    condition_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (condition_id)
);

INSERT INTO conditions (condition_name)
VALUES ('新品'), ('新品同様'), ('美品'), ('通常使用問題なし'), ('目的によって修理必要'), ('完動品ではない'), ('ジャンク');
