CREATE TABLE IF NOT EXISTS skills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS developers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    account_id INT,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS developer_skills (
    developer_id INT NOT NULL,
    skill_id INT NOT NULL,
    UNIQUE (developer_id, skill_id),
    FOREIGN KEY (developer_id) REFERENCES developers (id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills (id) ON DELETE CASCADE
);