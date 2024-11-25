
CREATE TABLE IF NOT EXISTS teacher (
    mail VARCHAR(64) PRIMARY KEY,
    name VARCHAR(60),
    password VARCHAR(32)
); 

CREATE TABLE IF NOT EXISTS student (
    mail VARCHAR(64) PRIMARY KEY,
    name VARCHAR(60),
    password VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS test (
    test_id INT PRIMARY KEY,
    test_name VARCHAR(30),
    q_count INTEGER
);

CREATE TABLE IF NOT EXISTS contents (
    test_id INTEGER,
    q_no INTEGER,
    q_text TEXT,
    sel1 TEXT,
    sel2 TEXT,
    sel3 TEXT,
    sel4 TEXT,
    ans1 CHAR(1),
    ans2 CHAR(1),
    ans3 CHAR(1),
    ans4 CHAR(1),
    PRIMARY KEY (test_id, q_no)
);

-- teacher テーブルへのテストデータ挿入
INSERT INTO teacher (mail, name, password) VALUES
('teacher1@example.com', 'Alice Smith', 'password123'),
('teacher2@example.com', 'Bob Johnson', 'securePass'),
('teacher3@example.com', 'Carol Williams', 'teach2024');

-- student テーブルへのテストデータ挿入
INSERT INTO student (mail, name, password) VALUES
('student1@example.com', 'David Brown', 'pass1234'),
('student2@example.com', 'Eve Davis', 'studPass'),
('student3@example.com', 'Frank Wilson', 'learn2024');


CREATE TABLE IF NOT EXISTS answer (
    mail VARCHAR(64),
    test_id INT,
    q_no INT,
    a_no INT,
    q_date TIMESTAMP,
    PRIMARY KEY( mail, test_id, q_no )
);


-- test テーブルへ列追加（修正）
ALTER TABLE `test`
ADD COLUMN `mail` VARCHAR(64);
