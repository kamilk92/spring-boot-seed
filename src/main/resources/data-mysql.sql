INSERT INTO user(ID, LOGIN, PASSWORD, EMAIL, IS_ENABLED) VALUES(1, 'admin', '$2a$10$EREHlU8b7dvTGDp5fqVZy.PRCS2xsDlXOWKDXxWykLjdXliEHb/2W', 'admin@domain.com', true);
INSERT INTO user(ID, LOGIN, PASSWORD, EMAIL, IS_ENABLED) VALUES(2, 'user', '$2a$10$PFXy51P9KjjhcKq6UWagSuop2S5CMRp5nQJG2LtcJF2dPJ88MyZUq', 'test-user@domain.com', true);

INSERT INTO role(ID, AUTHORITY) VALUES(1, 'ROLE_ADMIN');
INSERT INTO role(ID, AUTHORITY) VALUES(2, 'ROLE_USER');

INSERT INTO user_role(ID_USER, ID_ROLE) VALUES(1, 1);
INSERT INTO user_role(ID_USER, ID_ROLE) VALUES(1, 2);
INSERT INTO user_role(ID_USER, ID_ROLE) VALUES(2, 2);

INSERT INTO tournament(id, name, description) VALUES(1, 'Premier League', 'First England league');

INSERT INTO tournament_season(id, begin_date, is_open, tournament_id) VALUES(1, '2018-10-08', 1, 1);

INSERT INTO team(id, name) VALUES (1, 'Manchester Utd.');
INSERT INTO team(id, name) VALUES (2, 'Leicester');

INSERT INTO tournament_match(id, begin_date, home_score, away_score, home_team_id, away_team_id, tournament_season_id) VALUES(1, '2018-10-08 21:00:00', 2, 1, 1, 2, 1);
