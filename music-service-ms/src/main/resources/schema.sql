Create TABLE if not exists Song
(
    id      BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    version INT,
    author  VARCHAR(255) NOT NULL,
    name    VARCHAR(255) NOT NULL
);
