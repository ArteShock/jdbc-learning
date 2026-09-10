package ru.arteshock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserRepository {
    public List<User> findAll(){

        List<User> users = new ArrayList<>();

        DBConnector connector = new DBConnector();

        try(Connection connection = connector.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, email, age FROM users");
            ResultSet rs = pstmt.executeQuery()){

            while (rs.next()){
                users.add(new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getInt("age")));
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return users;
    }

    public User findById(int id) {

        DBConnector connector = new DBConnector();

        User user = null;

        try (Connection connection = connector.getConnection();
             PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, email, age FROM users WHERE id = ?")) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if(rs.next()){
                    user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getInt("age"));
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        return user;
    }

    public static void main(String[] args) {

        UserRepository repo = new UserRepository();
        System.out.println("Все пользователи:");
        List<User> users = repo.findAll();
        for(User user : users){
            System.out.println(user);
        }

        System.out.println("Id = 1:");
        System.out.println(repo.findById(1));

        System.out.println("Id = 999");
        System.out.println(repo.findById(999));
    }
}
