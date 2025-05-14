--liquibase formatted sql
--changeset pedro:202505141529
--comment: blocks table create

CREATE TABLE blocks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    block_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    block_reason VARCHAR(255) NOT NULL,
    unblock_at TIMESTAMP NULL,
    unblock_reason VARCHAR(255) NOT NULL,
    card_id BIGINT NOT NULL,
    CONSTRAINT cards__blocks_fk FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
)
--rollback DROP TABLE blocks