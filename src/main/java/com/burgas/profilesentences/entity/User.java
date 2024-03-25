package com.burgas.profilesentences.entity;

public class User extends Entity{

    private String userName;
    private String email;
    private String password;
    private String date;

    public User(int id) {
        super(id);
    }

    public User(int id, String userName, String email, String password, String date) {
        super(id);
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public User(String userName, String email, String password, String date) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.date = date;
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public User(int id, String userName, String email, String password) {
        super(id);
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        //noinspection StringTemplateMigration
        return "UserData{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
