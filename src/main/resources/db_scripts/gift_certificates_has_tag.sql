CREATE TABLE gift_certificates_has_tag
(
    `gift_certificates_id` INT NOT NULL,
    `tag_id`               INT NOT NULL,
    PRIMARY KEY (`gift_certificates_id`, `tag_id`),
    FOREIGN KEY (`gift_certificates_id`) REFERENCES `gift_certificates` (`id`) ON UPDATE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON UPDATE CASCADE
);