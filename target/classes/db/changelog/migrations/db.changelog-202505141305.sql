--liquibase formatted sql
--changeset pedro:202505141305
--comment: boards_columns table create

CREATE TABLE boards_columns (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    `order` INT NOT NULL,
    kind VARCHAR(7) NOT NULL,
    board_id BIGINT NOT NULL,
    CONSTRAINT boards__boards_columns_fk FOREIGN KEY (board_id) REFERENCES boards(id) ON DELETE CASCADE,
    CONSTRAINT unique_board_id_order UNIQUE (board_id, `order`)
)
--rollback DROP TABLE boards_columns