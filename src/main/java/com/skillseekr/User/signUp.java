package com.skillseekr.User;

import javafx.stage.Stage;
import com.skillseekr.API.EmailAPI;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import com.skillseekr.Services.User.ServiceUser;
import com.skillseekr.Models.User.User;

public class signUp {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private ComboBox<String> roleId;

    @FXML
    private Button signupButton;

    @FXML
    private CheckBox termsCheckBox;

    @FXML
    private TextField usernameField;

    private ServiceUser userService; // Assuming you have a ServiceUser class for user operations

    @FXML
    public void initialize() {
        userService = new ServiceUser(); // Initialize the ServiceUser instance
    }

    @FXML
    public void validateInfo() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String selectedRole = roleId.getValue();


        if (username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter your username.");
            return;
        }


        if (email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter your email address.");
            return;
        } else if (!email.contains("@")) {
            showAlert(Alert.AlertType.ERROR, "Error", "Email address must contain '@'.");
            return;
        }

        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter your password.");
            return;
        }

        if (!confirmPassword.equals(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match.");
            return;
        }

        if (selectedRole == null || selectedRole.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a role.");
            return;
        }


        // Check if user with the provided email already exists
        try {
            if (userService.userExists(email)) {
                showAlert(Alert.AlertType.ERROR, "Error", "User with email " + email + " already exists.");
                return;
            }

            // Create a new User object and set its properties
            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
            user.setRoles(getRoleValue(selectedRole)); // Get the role value based on the selected role


            userService.signup(user); // Add the user using the ServiceUser instance

            // Call the sendEmailVerification method from EmailAPI to send the verification email
            EmailAPI.sendEmailVerification(email);

            showAlert(Alert.AlertType.INFORMATION, "Success", "User registered successfully.");
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to register user: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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