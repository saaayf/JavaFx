package com.skillseekr.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.skillseekr.Models.User.User;
import com.skillseekr.Services.User.ServiceUser;
import com.skillseekr.Services.IServices;

import java.sql.SQLException;

public class editUser {
    @FXML
    private TextField usernameId;

    @FXML
    private TextField emailId;


    @FXML
    private ComboBox<String> roleId;

    @FXML
    private CheckBox isVerifiedId;

    private User currentUser; // User to edit
    private IServices<User> userService; // Service for user operations

    public editUser() {
        userService = new ServiceUser(); // Initialize user service
    }

    public void initData(User user) {
        currentUser = user; // Set the user to edit
        // Display user data in the form fields
        usernameId.setText(user.getUsername());
        emailId.setText(user.getEmail());

        // Set the role based on the value from the User object
        switch (user.getRoles()) {
            case "[\"ROLE_ADMIN\"]":
                roleId.setValue("Administrateur");
                break;
            case "[\"ROLE_RECRUTEUR\"]":
                roleId.setValue("Recruteur");
                break;
            case "[\"ROLE_FREELANCER\"]":
                roleId.setValue("Freelancer");
                break;
            default:
                roleId.setValue(null);
                break;
        }
        isVerifiedId.setSelected(user.getIs_verified()); // Assuming isVerified is a boolean in User class
    }

    @FXML
    public void updateUser() {
        try {
            // Update user data with form values
            currentUser.setUsername(usernameId.getText());
            currentUser.setEmail(emailId.getText());

            String selectedRole = roleId.getValue(); // Get the selected role from ComboBox
            String roleValue;
            switch (selectedRole) {
                case "Administrateur":
                    roleValue = "[\"ROLE_ADMIN\"]";
                    break;
                case "Recruteur":
                    roleValue = "[\"ROLE_RECRUTEUR\"]";
                    break;
                case "Freelancer":
                    roleValue = "[\"ROLE_FREELANCER\"]";
                    break;
                default:
                    showAlert(Alert.AlertType.ERROR, "Error", "Invalid role selected.");
                    return; // Stop processing if an invalid role is selected
            }
            currentUser.setRoles(roleValue);
            currentUser.setIs_verified(isVerifiedId.isSelected()); // Assuming setVerified is the correct method

            // Update user in the database
            userService.update(currentUser);

            // Show success message
            showAlert(Alert.AlertType.INFORMATION, "Success", "User updated successfully.");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user: " + e.getMessage());
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setUser(User user) {
        this.currentUser = user; // Fixed the assignment to currentUser
        // Set the user data to the corresponding fields
        if (user != null) {
            usernameId.setText(user.getUsername());
            emailId.setText(user.getEmail());

            roleId.setValue(user.getRoles());
            isVerifiedId.setSelected(user.getIs_verified()); // Assuming isVerified is a boolean in User class
        }
    }
}
