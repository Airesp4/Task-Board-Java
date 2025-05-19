package com.dio.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dio.persistence.entity.BoardColumnEntity;
import static com.dio.persistence.entity.BoardColumnKindEnum.findByName;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardColumnDAO {
    
    private final Connection connection;

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        var sql = "INSERT INTO boards_columns (name, 'order', kind, board_id) VALUES (?, ?, ?, ?)";

        try (var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getOrder());
            statement.setString(3, entity.getKind().name());
            statement.setLong(4, entity.getBoard().getId());
            statement.executeUpdate();

            try (var resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                }
            }
        }

        return entity;
    }

    public List<BoardColumnEntity> findByBoardId(final Long id) throws SQLException{
        List<BoardColumnEntity> entities = new ArrayList<>();

        var sql = "SELECT id, name, `order` FROM boards_columns WHERE board_id = ? ORDER BY `order`";

        try (var statement = connection.prepareStatement(sql)){
            statement.setLong(1, id);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }
    }
}
