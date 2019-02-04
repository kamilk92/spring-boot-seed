DROP TABLE IF EXISTS tournament_match;
DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS tournament_season;
DROP TABLE IF EXISTS tournament;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;

CREATE TABLE user (
    ID int auto_increment primary key,
    LOGIN varchar(32),
    PASSWORD varchar(128),
    NICK varchar(32),
    EMAIL varchar(64),
    IS_ENABLED tinyint(1) default 0
);

CREATE TABLE role(
	ID int auto_increment primary key,
    AUTHORITY varchar(64)
);

CREATE TABLE user_role(
	ID int auto_increment primary key,
    ID_USER int,
    ID_ROLE int,
    foreign key (ID_USER) references user(id),
    foreign key (ID_ROLE) references role(id)
);

CREATE TABLE tournament(
    ID int auto_increment primary key,
    NAME varchar(128),
    DESCRIPTION varchar(1024)
);

CREATE TABLE tournament_season(
    ID int auto_increment primary key,
    BEGIN_DATE date,
    IS_OPEN tinyint(1),
    TOURNAMENT_ID int,
    foreign key (TOURNAMENT_ID) references tournament(ID)
);

CREATE TABLE team(
    ID int auto_increment primary key,
    NAME varchar(64)
);

CREATE TABLE tournament_match(
    ID int auto_increment primary key,
    BEGIN_DATE datetime,
    HOME_SCORE int,
    AWAY_SCORE int,
    HOME_TEAM_ID int,
    AWAY_TEAM_ID int,
    TOURNAMENT_SEASON_ID int,
    foreign key(HOME_TEAM_ID) references team(ID),
    foreign key(AWAY_TEAM_ID) references team(ID),
    foreign key(TOURNAMENT_SEASON_ID) references tournament_season(id)
);