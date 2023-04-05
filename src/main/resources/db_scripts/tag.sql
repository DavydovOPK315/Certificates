create table tag
(
    id   int auto_increment primary key,
    name varchar(45) not null,
    constraint name_UNIQUE unique (name)
);