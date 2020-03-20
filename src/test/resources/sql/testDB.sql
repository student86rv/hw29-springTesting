INSERT INTO skills (name) VALUES
	('Java'),
    ('C++'),
    ('PHP'),
    ('Front-end');

INSERT INTO accounts (email, status) VALUES
	('ivan.ivanov@gmail.com', 'ACTIVE'),
    ('petr.petrov@i.ua', 'BANNED'),
    ('sidor.sidorov@ukr.net', 'DELETED');

INSERT INTO developers (name, account_id) VALUES
	('Ivan Ivanov', 1),
    ('Petr Petrov', 2),
    ('Sidor Sidorov', 3);

INSERT INTO developer_skills (developer_id, skill_id) VALUES
	(1, 1),
    (1, 3),
    (2, 2),
    (3, 3),
    (3, 4);