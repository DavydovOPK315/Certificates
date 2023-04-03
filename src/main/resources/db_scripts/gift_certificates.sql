CREATE TABLE gift_certificates
(
    id               int primary key auto_increment,
    name             VARCHAR(45)  NOT NULL,
    description      VARCHAR(255) NOT NULL,
    price            INT          NOT NULL DEFAULT '0',
    duration         INT          NOT NULL,
    create_date      VARCHAR(45)  NOT NULL,
    last_update_date VARCHAR(45)  NOT NULL
);