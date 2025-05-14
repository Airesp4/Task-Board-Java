--liquibase formatted sql
--changeset pedro:202505141153
--comment: boards table create

CREATE TABLE boards(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)
--rollback DROP TABLE BOARDS