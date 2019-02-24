CREATE TABLE user_role(
	ID int auto_increment primary key,
    ID_USER int,
    ID_ROLE int,
    foreign key (ID_USER) references user(id),
    foreign key (ID_ROLE) references role(id)
);