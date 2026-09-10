package ru.arteshock;

public class User {
    private int id;
    private String name;
    private String email;
    private int age;

    public User() {
    }

    public User(int id, String name, String mail, int age) {
        this.id = id;
        this.name = name;
        this.email = mail;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString(){
        return "Id: " + id + ", Name: " + name + ", E-mail: " + email + ", Age: " + age;
    }
}
