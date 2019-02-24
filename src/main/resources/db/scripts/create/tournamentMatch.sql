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