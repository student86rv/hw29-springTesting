USE kep6t95a7zq4bijb;

INSERT INTO skills (id, name) VALUES
	(1, 'Java'),
    (2, 'C++'),
    (3, 'PHP'),
    (4, 'Front-end');
COMMIT;

INSERT INTO accounts (id, email, status) VALUES
	(1, 'ivan.ivanov@gmail.com', 'ACTIVE'),
    (2, 'petr.petrov@i.ua', 'BANNED'),
    (3, 'sidor.sidorov@ukr.net', 'DELETED');
COMMIT;

INSERT INTO developers (id, name, account_id) VALUES
	(1, 'Ivan Ivanov', 1),
    (2, 'Petr Petrov', 2),
    (3, 'Sidor Sidorov', 3);
COMMIT;

INSERT INTO developer_skills (developer_id, skill_id) VALUES
	(1, 1),
    (1, 3),
    (2, 2),
    (3, 3),
    (3, 4);
COMMIT;