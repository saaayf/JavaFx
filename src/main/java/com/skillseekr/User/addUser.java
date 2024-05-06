package com.skillseekr.User;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.skillseekr.Models.User.User;
import com.skillseekr.Services.User.ServiceUser;
import com.skillseekr.API.EmailAPI;

public class addUser {

    @FXML
    private TextField usernameId;

    @FXML
    private TextField emailId;

    @FXML
    private ComboBox<String> roleId;

    @FXML
    private Button addUserButton;

    @FXML
    private CheckBox isVerifiedId;

    private ServiceUser userService; // Assuming you have a ServiceUser class for user operations

    @FXML
    public void initialize() {
        userService = new ServiceUser(); // Initialize the ServiceUser instance
    }

    @FXML
    public void addUser() {
        String username = usernameId.getText().trim();
        String email = emailId.getText().trim();
        String selectedRole = roleId.getValue();

        Alert alert = new Alert(Alert.AlertType.ERROR);

        if (username.isEmpty()) {
            alert.setContentText("Please enter a username.");
            alert.showAndWait();
        }

        else if (email.isEmpty()) {
            alert.setContentText("Please enter an email address.");
            alert.showAndWait();

        } else if (!email.contains("@")) {
            alert.setContentText("Email address must contain '@'.");
            alert.showAndWait();

        }

        else if (selectedRole == null || selectedRole.trim().isEmpty()) {
            alert.setContentText("Please select a role.");
            alert.showAndWait();

        }

        try {
            // Check if user with the provided email already exists
            if (userService.userExists(email)) {
                alert.setContentText("User with email " + email + " already exists.");
                alert.showAndWait();

            }

            // Create a new User object and set its properties
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setRoles(getRoleValue(selectedRole)); // Get the role value based on the selected role

            userService.signup(user); // Add the user using the ServiceUser instance

            // Call the sendEmailVerification method from EmailAPI to send the verification email
            EmailAPI.sendEmailVerification(email);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("User registered successfully.");
            alert.showAndWait();

            Stage stage = (Stage) addUserButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            alert.setContentText("Failed to register user: " + e.getMessage());
            alert.showAndWait();
        }
    }

    private String getRoleValue(String selectedRole) {
        switch (selectedRole) {
            case "Administrateur":
                return "[\"ROLE_ADMIN\"]";
            case "Recruteur":
                return "[\"ROLE_RECRUTEUR\"]";
            case "Freelancer":
                return "[\"ROLE_FREELANCER\"]";
            default:
                return ""; // Handle other cases accordingly
        }
    }
}
