CREATE DATABASE IF NOT EXISTS competitions;
USE competitions;

DROP TABLE IF EXISTS tournament_match;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS tournament_season;
DROP TABLE IF EXISTS tournament;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    ID int primary key,
    LOGIN varchar(32),
    PASSWORD varchar(128),
    NICK varchar(32),
    EMAIL varchar(64),
    IS_ENABLED tinyint(1) default 0,
    ROLE_ID int
);

CREATE TABLE role(
	ID int primary key,
    AUTHORITY varchar(64)
);

CREATE TABLE user_role(
	ID int primary key,
    ID_USER int,
    ID_ROLE int,
    foreign key (ID_USER) references user(id),
    foreign key (ID_ROLE) references role(id)
);

CREATE TABLE tournament(
    ID int primary key auto_increment,
    NAME varchar(128),
    DESCRIPTION varchar(1024)
);

CREATE TABLE tournament_season(
    ID int primary key,
    BEGIN_DATE date,
    IS_OPEN tinyint(1),
    TOURNAMENT_ID int,
    foreign key (TOURNAMENT_ID) references tournament(ID)
);

CREATE TABLE team(
    ID int primary key,
    NAME varchar(64)
);

CREATE TABLE tournament_match(
    ID int primary key,
    BEGIN_DATE date,
    HOME_TEAM int,
    AWAY_TEAM int,
    foreign key(HOME_TEAM) references team(ID),
    foreign key(AWAY_TEAM) references team(ID)
);

INSERT INTO user(ID, LOGIN, PASSWORD, NICK, EMAIL, IS_ENABLED) VALUES(1, 'test-admin', '$2a$10$sSXtNMvXgp0PUL8Lwj2b/uhqdoZR5PJCXTjFWg5SPGI.ruDVBIaq.', 'test-admin', 'test-admin@domain.com', true);

INSERT INTO role(ID, AUTHORITY) values(1, 'ROLE_ADMIN');
INSERT INTO role(ID, AUTHORITY) values(2, 'ROLE_USER');

INSERT INTO user_role(ID, ID_USER, ID_ROLE) values(1, 1, 1);
INSERT INTO user_role(ID, ID_USER, ID_ROLE) values(2, 1, 2);

INSERT INTO tournament(ID, NAME, DESCRIPTION) VALUES(1, 'Test tournament', 'This is test tournament.');
INSERT INTO tournament(ID, NAME, DESCRIPTION) VALUES(2, 'Test tournament 2', 'This is test tournament 2.');

INSERT INTO tournament_season(ID, BEGIN_DATE, IS_OPEN, TOURNAMENT_ID) VALUES(1, '2018-01-01', True, 1);
INSERT INTO tournament_season(ID, BEGIN_DATE, IS_OPEN, TOURNAMENT_ID) VALUES(2, '2018-07-01', True, 1);

INSERT INTO team(ID, NAME) VALUES(0, 'Test team');