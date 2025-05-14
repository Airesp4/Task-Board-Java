package com.dio.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.dio.persistence.dao.BoardDao;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BoardService {
    
    private final Connection connection;

    public boolean delete(final Long id) throws SQLException{
        var dao = new BoardDao(connection);

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
