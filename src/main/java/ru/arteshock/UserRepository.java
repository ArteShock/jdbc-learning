package ru.arteshock;

import java.sql.*;
import java.util.*;

public class UserRepository {

    private static final String SQL_FIND_ALL = "SELECT id, name, email, age FROM users";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, email, age FROM users WHERE id = ?";
    private static final String SQL_SAVE_USER = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";



    public List<User> findAll(){

        List<User> users = new ArrayList<>();

        DBConnector connector = new DBConnector();

        try(Connection connection = connector.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_ALL);
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
             PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_BY_ID)) {

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

    public User saveUser(User user){
        DBConnector connector = new DBConnector();

        try(Connection connection = connector.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(SQL_SAVE_USER, Statement.RETURN_GENERATED_KEYS)){

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.executeUpdate();

            try(ResultSet rs = pstmt.getGeneratedKeys()){
                if(rs.next()){
                    user.setId(rs.getInt(1));
                }
            }

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return user;

    }

    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        User newUser = new User(0, "Пётр", "petr@post.com", 40);
        repo.saveUser(newUser);
        System.out.println("Сохранён: " + newUser);
    }
}
