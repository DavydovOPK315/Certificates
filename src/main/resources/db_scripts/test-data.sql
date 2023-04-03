INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('c1', 'desc1', 100, 10, '2023-03-03T00:15:42.265', '2023-03-03T00:15:42.265');

INSERT INTO gift_certificates (name, description, price, duration, create_date, last_update_date)
VALUES ('c2', 'desc2', 200, 20, '2023-04-03T00:15:42.375', '2023-04-03T00:15:42.375');

INSERT INTO tag (name) VALUES ('tag1');
INSERT INTO tag (name) VALUES ('tag2');
INSERT INTO gift_certificates_has_tag VALUES (1, 1);
INSERT INTO gift_certificates_has_tag VALUES (1, 2);
INSERT INTO gift_certificates_has_tag VALUES (2, 2);