package org.example.db;

import org.example.properties.PropertyReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static final Database INSTANCE = new Database();
    private static Connection connection;

    private Database() {
        String url = PropertyReader.getInstance().getConnectionUrl();
        String user = PropertyReader.getInstance().getUser();
        String password = PropertyReader.getInstance().getPassword();

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.printf("Can`t create connection. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    public int executeUpdate(String query) {
        try (Statement statement = INSTANCE.getConnection().createStatement()) {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.printf("Can`t run query. Reason: %s%n", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
