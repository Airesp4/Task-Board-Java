--liquibase formatted sql
--changeset pedro:202505141522
--comment: cards table create

CREATE TABLE cards (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    board_column_id BIGINT NOT NULL,
    CONSTRAINT boards_columns__cards_fk FOREIGN KEY (board_column_id) REFERENCES boards_columns(id) ON DELETE CASCADE
)
--rollback DROP TABLE cards