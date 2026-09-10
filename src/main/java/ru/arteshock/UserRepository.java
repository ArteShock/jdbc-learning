package ru.arteshock;

import java.sql.*;
import java.util.*;

public class UserRepository {

    private static final String SQL_FIND_ALL = "SELECT id, name, email, age FROM users ORDER BY id";
    private static final String SQL_FIND_BY_ID = "SELECT id, name, email, age FROM users WHERE id = ?";
    private static final String SQL_SAVE_USER = "INSERT INTO users (name, email, age) VALUES (?, ?, ?)";
    private static final String SQL_UPDATE_USER = "UPDATE users SET name = ?, email = ?, age = ? WHERE id = ?";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM users WHERE id = ?";



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

    public boolean updateUser(User user){

        DBConnector connector = new DBConnector();

        boolean result = false;

        try(Connection connection = connector.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_USER)){

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setInt(3, user.getAge());
            pstmt.setInt(4, user.getId());

            result = pstmt.executeUpdate() == 1;

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }

        return result;
    }

    public boolean deleteById(int id){
        DBConnector connector = new DBConnector();

        boolean result = false;

        try(Connection connection = connector.getConnection();
        PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_BY_ID)) {

            pstmt.setInt(1, id);
            result = pstmt.executeUpdate() == 1;

        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return result;
    }

    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        System.out.println("=== Все пользователи ===");
        repo.findAll().forEach(System.out::println);

        System.out.println("\n=== UPDATE id=1 ===");
        User user = repo.findById(1);
        user.setName("Иван Иванович");
        user.setAge(19);
        System.out.println("Обновлён: " + repo.updateUser(user));
        System.out.println(repo.findById(1));

        System.out.println("\n=== UPDATE id=999 (не существует) ===");
        User fake = new User(999, "Невидимка", "no@mail.com", 1);
        System.out.println("Обновлён: " + repo.updateUser(fake));

        System.out.println("\n=== DELETE id=2 ===");
        System.out.println("Удалён: " + repo.deleteById(2));

        System.out.println("\n=== DELETE id=999 (не существует) ===");
        System.out.println("Удалён: " + repo.deleteById(999));

        System.out.println("\n=== Все пользователи после изменений ===");
        repo.findAll().forEach(System.out::println);

    }
}
