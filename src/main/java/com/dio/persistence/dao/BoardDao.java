package com.dio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.dio.persistence.entity.BoardEntity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardDao {
    
    private final Connection connection;

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        var sql = "INSERT INTO boards (name) values (?)";

        try(var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            statement.setString(1, entity.getName());
            statement.executeUpdate();

            try(var resultSet = statement.getGeneratedKeys()){
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        }
        return entity;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var sql = "SELECT id, name FROM boards WHERE id = ?";

        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();

            var resultSet = statement.getResultSet();

            if (resultSet.next()) {
                var entity = new BoardEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));

                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }

    public boolean existsById(final Long id) throws SQLException {
        var sql = "SELECT 1 FROM boards WHERE id = ?";

        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();

            return statement.getResultSet().next();
        }
    }

    public void delete(final Long id) throws SQLException {
        var sql = "DELETE FROM boards WHERE id = ?";

        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }
}
