package models;

public class User {
    int id ;
    String email ;
    String role ;

    public User(int id, String email, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
    }


    public User(String email, String role) {
        this.email = email;
        this.role = role;
    }


    public User() {
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

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
