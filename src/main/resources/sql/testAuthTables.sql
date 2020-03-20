INSERT INTO users (user_name, password, enabled) VALUES
    ('admin', 'admin', true),
    ('user', 'user', true);

INSERT INTO user_roles (user_id, authority) VALUES
    (1, 'ROLE_ADMIN'),
    (2, 'ROLE_USER');

UPDATE users
    SET password = '$2y$12$2xsv4NoQlFtw8/.jFLztw.4n.jADL7TQwQItBaVrv.ANBjz.Y049y'
    WHERE user_name = 'user';

UPDATE users
    SET password = '$2y$12$NMpMDvgiaO1Q91PDwtH1SeQ2TyfnczF2e0.bkaaPreZSeUxWMAO7q'
    WHERE user_name = 'admin';