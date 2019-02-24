CREATE TABLE user (
    ID int auto_increment primary key,
    LOGIN varchar(32),
    PASSWORD varchar(128),
    NICK varchar(32),
    EMAIL varchar(64),
    IS_ENABLED tinyint(1) default 0
);