package org.bobbysStore.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public static Connection connect() {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username="postgres";
        String password="Bobbyjay1@2&3";
        try {
            return DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
