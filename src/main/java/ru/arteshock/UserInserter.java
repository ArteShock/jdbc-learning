package ru.arteshock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInserter {
    static void insertUser(String name, String mail, int age){
        DBConnector connector = new DBConnector();
        try(Connection connection = connector.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users(name, email, age) VALUES (?,?,?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, mail);
            pstmt.setInt(3, age);
            if(pstmt.executeUpdate() == 1){
                System.out.println("Пользователь " + name + " добавлен");
            }
        }
        catch (SQLException e){
            System.err.println("Добавление пользователя \"" + name + "\" не удалось");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserInserter.insertUser("Иван", "ivan@post.com", 18);
        UserInserter.insertUser("Мария", "maria@post.com", 25);
    }
}
