CREATE TABLE users(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    enabled BOOLEAN
);

CREATE TABLE user_roles(
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL REFERENCES users,
    authority VARCHAR(60) NOT NULL
);