CREATE DATABASE competitions;

DROP TABLE IF EXISTS tournament;
DROP TABLE IF EXISTS user;

CREATE TABLE user {
    ID int primary key,
    LOGIN varchar(32),
    PASSWORD varchar(128),
    NICK varchar(32),
    EMAIL varchar(64),
    IS_ENABLED BOOL tinyint(1)
};

CREATE TABLE tournament{
    ID int primary key,
    NAME varchar(128),
    DESCRIPTION varchar(1024)
};

CREATE TABLE tournament_season{
    ID int primary key,
    BEGIN_DATE date,
    IS_OPEN tinyint(1),
    TOURNAMENT_ID int,
    foreign key (TOURNAMENT_ID) references tournament(ID)
};

CREATE TABLE team{
    ID int primary key,
    NAME varchar(64)
};

CREATE TABLE match{
    ID int primary key,
    BEGIN_DATE date,
    HOME_TEAM int,
    AWAY_TEAM int,
    foreign key(HOME_TEAM) references team(ID),
    foreign key(AWAY_TEAM) references team(ID)
};