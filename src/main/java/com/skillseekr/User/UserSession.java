package com.skillseekr.User;

import java.sql.ResultSet;

public class UserSession {
    private static UserSession instance;

    private int id;
    private String email;
    private String roles;
    private String username;
    private String lastname;

    private UserSession(int id, String email, String roles, String username, String lastname) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.username = username;
        this.lastname = lastname;
    }

    public static UserSession getInstance(int id, String email, String roles, String username, String lastname) {
        if (instance == null) {
            instance = new UserSession(id, email, roles, username, lastname);
        }
        return instance;
    }

    public void cleanUserSession() {
        id = 0;
        email = null;
        roles = null;
        username = null;
        lastname = null;
    }

    public void addUserLogin(ResultSet userRow) {
        try {
            id = userRow.getInt("id");
            email = userRow.getString("email");
            roles = userRow.getString("roleu");
            username = userRow.getString("nomu");
            lastname = userRow.getString("prenomu");
        } catch (Exception e) {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Logged in as {" +
                "Email='" + email + '\'' +
                ", Role ='" + roles + '\'' +
                ", Name ='" + username + '\'' +
                ", Lastname='" + lastname + '\'' +
                '}';
    }
}
