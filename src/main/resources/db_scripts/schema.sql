create table tag
(
    id   int auto_increment primary key,
    name varchar(45) not null,
    constraint name_UNIQUE unique (name)
);

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

CREATE TABLE gift_certificates_has_tag
(
    `gift_certificates_id` INT NOT NULL,
    `tag_id`               INT NOT NULL,
    PRIMARY KEY (`gift_certificates_id`, `tag_id`),
    FOREIGN KEY (`gift_certificates_id`) REFERENCES `gift_certificates` (`id`) ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON UPDATE CASCADE
);