INSERT INTO user(ID, LOGIN, PASSWORD, EMAIL, IS_ENABLED) VALUES(1, 'admin', '$2a$10$EREHlU8b7dvTGDp5fqVZy.PRCS2xsDlXOWKDXxWykLjdXliEHb/2W', 'admin@domain.com', true);
INSERT INTO user(ID, LOGIN, PASSWORD, EMAIL, IS_ENABLED) VALUES(2, 'user', '$2a$10$PFXy51P9KjjhcKq6UWagSuop2S5CMRp5nQJG2LtcJF2dPJ88MyZUq', 'test-user@domain.com', true);

INSERT INTO role(ID, AUTHORITY) values(1, 'ROLE_ADMIN');
INSERT INTO role(ID, AUTHORITY) values(2, 'ROLE_USER');

INSERT INTO user_role(ID_USER, ID_ROLE) values(1, 1);
INSERT INTO user_role(ID_USER, ID_ROLE) values(1, 2);
INSERT INTO user_role(ID_USER, ID_ROLE) values(2, 2);
