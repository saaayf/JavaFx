package com.skillseekr.User;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import com.skillseekr.Services.User.ServiceUser;
import javafx.stage.Stage;
import java.sql.SQLException;

public class resetPassword {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private String email; // Variable pour stocker l'adresse e-mail

    private ServiceUser serviceUser; // Instance de ServiceUser pour accéder aux méthodes de gestion des utilisateurs

    private Stage stage;

    public resetPassword() {
        serviceUser = new ServiceUser();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void updatePassword(ActionEvent event) {
        String newPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Error", "Empty Fields", "Please fill in all fields.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Error", "Password Mismatch", "Passwords do not match.");
            return;
        }

        try {
            serviceUser.updatePassword(email, newPassword);
            showAlert("Success", "Password Updated", "Your password has been updated successfully.");
            closeWindow(event);
        } catch (SQLException e) {
            showAlert("Error", "Update Failed", "Failed to update password: " + e.getMessage());
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        source.getScene().getWindow().hide(); // Ferme la fenêtre associée à l'événement
    }
}
