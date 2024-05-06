package com.skillseekr.Models.User;

public class User {

    private int id;

    private String email;
    private String roles;

    private String password;
    private String username;
    private boolean is_verified;

    public User() {
    }

    public User(String email, String roles, String password, String username, boolean is_verified) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.username = username;
        this.is_verified = is_verified;
    }

    public User(int id, String email, String roles, String password, String username, boolean is_verified) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.username = username;
        this.is_verified = is_verified;
    }

    public User(int id, String email, String roles, String password, String username, String role, boolean isVerified) {
        this.id = id;
        this.email = email;
        this.roles = role;
        this.password = password;
        this.username = username;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getIs_verified() {
        return is_verified;
    }


    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", roles=" + roles + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", is_verified=" + is_verified +
                '}';
    }

}