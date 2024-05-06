package com.skillseekr.Services.User;


import com.skillseekr.Models.Offers.Offer;
import org.mindrot.jbcrypt.BCrypt;
import com.skillseekr.User.UserSession;
import  com.skillseekr.Models.User.User;
import com.skillseekr.Utils.MyJDBC;
import com.skillseekr.Services.IServices;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServiceUser implements IServices<User> {
    public Connection connection;
    public Statement statement;


    public ServiceUser(){
        connection= MyJDBC.getInstance().getConnection();
    }

    public void updatePassword(String email, String newPassword) throws SQLException {
        try {
            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

            String query = "UPDATE user SET password=? WHERE email=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, hashedNewPassword);
                preparedStatement.setString(2, email);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error updating password: " + e.getMessage());
            throw e; // Re-throw the exception to be handled in the controller
        }
    }

/*
       public boolean authenticateUser(String email, String userPassword) throws SQLException {
        try {
            String query = "SELECT password FROM user WHERE email=?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("password");
                    return BCrypt.checkpw(userPassword, hashedPasswordFromDB);
                }
                return false; // No user with the provided email found
            }
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error authenticating user: " + e.getMessage());
            throw e; // Re-throw the exception to be handled in the controller
        }
    }
    */


    public boolean authenticateUser(String email, String userPassword) throws SQLException {
        try {
            String query = "SELECT password, is_verified FROM user WHERE email=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String hashedPasswordFromDB = resultSet.getString("password");
                    boolean isVerified = resultSet.getBoolean("is_verified");
                    if (isVerified && BCrypt.checkpw(userPassword, hashedPasswordFromDB)) {
                        return true; // User is verified and password matches
                    } else {
                        return false; // User is not verified or password does not match
                    }
                }
                return false; // No user with the provided email found
            }
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error authenticating user: " + e.getMessage());
            throw e; // Re-throw the exception to be handled in the controller
        }
    }

    public boolean emailExists(String email) throws SQLException {
        try {
            String query = "SELECT COUNT(*) AS count FROM user WHERE email=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, email);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0; // True if email exists, false otherwise
                }
                return false; // No result found
            }
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error checking email existence: " + e.getMessage());
            throw e; // Re-throw the exception to be handled in the controller
        }
    }



    /*
    @Override
    public void add(User user) throws SQLException{
        String password = user.getPassword();
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String req = "INSERT INTO user (email, password, username, roles, is_verified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, user.getEmail());
            pre.setString(2, encryptedPassword);
            pre.setString(3, user.getUsername());
            pre.setString(4, user.getRoles());
            pre.setBoolean(5, user.getIs_verified());
            pre.executeUpdate();
        }
    }
*/
    @Override
    public void add(User user) throws SQLException {
        String email = user.getEmail();

        // Check if the user with the provided email already exists
        if (userExists(email)) {
            throw new SQLException("Error: User with email " + email + " already exists.");
        }

        // If user doesn't exist, proceed with adding the user
        String password = user.getPassword();
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String req = "INSERT INTO user (email, password, username, roles, is_verified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, email);
            pre.setString(2, encryptedPassword);
            pre.setString(3, user.getUsername());
            pre.setString(4, user.getRoles());
            pre.setBoolean(5, user.getIs_verified());
            pre.executeUpdate();
        }
    }

    // Helper method to check if a user with the given email already exists
    public boolean userExists(String email) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                return count > 0; // User exists if count is greater than 0
            }
            return false; // No user found with the provided email
        }
    }

    @Override
    public void update(User user) throws SQLException {


        String req = "UPDATE user SET email=?, username=?, roles=?, is_verified=? WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, user.getEmail());

        pre.setString(2, user.getUsername());
        pre.setString(3, user.getRoles());
        pre.setBoolean(4, user.getIs_verified());
        pre.setInt(5, user.getId());
        pre.executeUpdate();


    }



    @Override
    public void delete(User user) throws SQLException {
        String req = "DELETE FROM user WHERE id=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, user.getId());
            pre.executeUpdate();
        }
    }


    public List<User> show() throws SQLException {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        statement = connection.createStatement();
        ResultSet res =statement.executeQuery(req);
        while (res.next()) {
            int id = res.getInt("id");
            String email = res.getString("email");
            String roles = res.getString("roles");
            String password = res.getString("password");
            String username = res.getString("username");
            boolean is_verified = res.getBoolean("is_verified");
            User u = new User(id, email, roles, password, username, is_verified);
            users.add(u);
        }
        return users;
    }

    @Override
    public void addReclamation(User user, int userId) throws SQLException {

    }


    public void signup(User user) throws SQLException {
        String email = user.getEmail();

        // Check if the user with the provided email already exists
        if (userExists(email)) {
            throw new SQLException("Error: User with email " + email + " already exists.");
        }

        // If user doesn't exist, proceed with adding the user
        String password = user.getPassword();
        String encryptedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String req = "INSERT INTO user (email, password, username, roles, is_verified) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setString(1, email);
            pre.setString(2, encryptedPassword);
            pre.setString(3, user.getUsername());
            pre.setString(4, user.getRoles());
            pre.setBoolean(5, user.getIs_verified());
            pre.executeUpdate();
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String roles = resultSet.getString("roles");
                String password = resultSet.getString("password");
                String username = resultSet.getString("username");
                String Role = resultSet.getString("roles");
                boolean is_verified = resultSet.getBoolean("is_verified");
                return new User(id, email, roles, password, username,Role, is_verified);
            }
            return null; // No user found with the provided email
        } catch (SQLException e) {
            // Log the exception for debugging purposes
            System.err.println("Error getting user by email: " + e.getMessage());
            throw e; // Re-throw the exception to be handled in the controller
        }
    }

}
