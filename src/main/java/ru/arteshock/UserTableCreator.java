package ru.arteshock;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTableCreator {

    private static final String CREATE_TABLE_SQL = """
    CREATE TABLE IF NOT EXISTS users (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        age INTEGER
    )
    """;

    void createUserTable(){
        DBConnector connector = new DBConnector();

        try(Connection connection = connector.getConnection();
            Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(CREATE_TABLE_SQL);
            System.out.println("Таблица users создана (или уже существует)");
        }
        catch (SQLException e){
            System.err.println("Ошибка создании таблицы users");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserTableCreator UTCreator = new UserTableCreator();
        UTCreator.createUserTable();
    }
}
