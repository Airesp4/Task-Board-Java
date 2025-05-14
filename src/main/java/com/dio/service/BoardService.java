package com.dio.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import com.dio.persistence.dao.BoardColumnDAO;
import com.dio.persistence.dao.BoardDAO;
import com.dio.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardService {
    
    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        try {
            dao.insert(entity);

            var columns = entity.getBoardColumns().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();

            for (var column : columns){
                boardColumnDAO.insert(column);
            }

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
        return entity;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);

        var optional = dao.findById(id);
        if (optional.isPresent()) {
            var entity = optional.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }

        return Optional.empty();
    }

    public boolean delete(final Long id) throws SQLException{
        var dao = new BoardDAO(connection);

        try{
            if (!dao.existsById(id)) {
                return false;
            }

            dao.delete(id);
            connection.commit();
            
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}
