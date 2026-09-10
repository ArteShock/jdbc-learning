package ru.arteshock;

import java.sql.*;

public class UserReader {
    static void printAllUsers(){
        DBConnector connector = new DBConnector();
        try(Connection connection = connector.getConnection();
            PreparedStatement pstmt = connection.prepareStatement("SELECT id, name, email, age FROM users");
            ResultSet rs = pstmt.executeQuery()){

            System.out.println("ID|NAME|EMAIL|AGE");
            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String mail = rs.getString("email");
                int age = rs.getInt("age");
                System.out.println(id + "|" + name + "|" + mail + "|" + age);
            }
            System.out.println("Все пользователи считаны");
        }
        catch(SQLException e){
            System.err.println("Считывание пользователей не удалось");
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        UserReader.printAllUsers();
    }
}
