CREATE TABLE tournament_season(
    ID int auto_increment primary key,
    BEGIN_DATE date,
    IS_OPEN tinyint(1),
    TOURNAMENT_ID int,
    foreign key (TOURNAMENT_ID) references tournament(ID)
);