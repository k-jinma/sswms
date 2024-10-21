-- データベースが存在しない場合は新規作成
CREATE DATABASE IF NOT EXISTS test;

-- データベースを選択
USE test;

-- demo_userテーブルが存在しない場合は新規作成
CREATE TABLE IF NOT EXISTS demo_user (
    mail VARCHAR(30) PRIMARY KEY,
    name VARCHAR(50),
    password VARCHAR(50)
);

-- データを挿入
INSERT INTO demo_user (mail, name, password) VALUES ("test@test", "name1", "test");
