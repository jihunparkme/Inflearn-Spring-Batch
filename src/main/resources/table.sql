CREATE TABLE product
(
    id    INT NOT NULL AUTO_INCREMENT,
    name  VARCHAR(20),
    price INT,
    type  VARCHAR(20),
    PRIMARY KEY (id)
) ENGINE = MYISAM
  CHARSET = utf8;