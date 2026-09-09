package ru.arteshock;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    private static final String URL = "jdbc:postgresql://localhost:5432/learning_db";
    private  static final String USER = "postgres";
    private static final String PASS = "16101998";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USER,PASS);
    }

    public static void main(String[] args) {
        DBConnector connector = new DBConnector();
        try(Connection connection = connector.getConnection()) {
            System.out.println("Успешное подключение");
        }
        catch (SQLException e){
            System.out.println("Подключение не удалось");
            System.out.println(e.getMessage());
        }

    }
}
