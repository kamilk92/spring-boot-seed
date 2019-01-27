INSERT INTO user(ID, LOGIN, PASSWORD, NICK, EMAIL, IS_ENABLED) VALUES(0, 'test-admin', '$2a$10$sSXtNMvXgp0PUL8Lwj2b/uhqdoZR5PJCXTjFWg5SPGI.ruDVBIaq.', 'test-admin', 'test-admin@domain.com', true);

INSERT INTO role(ID, AUTHORITY) values(0, 'ROLE_ADMIN');
INSERT INTO role(ID, AUTHORITY) values(1, 'ROLE_USER');

INSERT INTO user_role(ID, ID_USER, ID_ROLE) values(0, 0, 0);
INSERT INTO user_role(ID, ID_USER, ID_ROLE) values(1, 0, 1);

INSERT INTO tournament(ID, NAME, DESCRIPTION) VALUES(0, 'Test tournament', 'This is test tournament');

INSERT INTO team(ID, NAME) VALUES(0, 'Test team');