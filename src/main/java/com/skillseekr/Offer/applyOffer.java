package com.skillseekr.Offer;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.regex.Pattern;

public class applyOffer {

    @FXML
    private TextField authorEmailField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextArea applicationRequestArea;

    private final Apply apply;

    public applyOffer() {
        try {
            apply = new Apply();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // The handleSendButtonAction method should be outside of the setMailDetails method
    @FXML
    private void handleSendButtonAction() {
        String authorEmail = authorEmailField.getText();
        String subject = subjectField.getText();
        String applicationRequest = applicationRequestArea.getText();

        // Input validation
        if (authorEmail.isEmpty() || subject.isEmpty() || applicationRequest.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }

        // Email format validation
        if (!isValidEmail(authorEmail)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email format.");
            return;
        }

        try {
            apply.sendMail(authorEmail, subject, applicationRequest); // Call sendMail method
            showAlert(Alert.AlertType.INFORMATION, "Success", "Email sent successfully.");
            clearFields(); // Clear fields after successful email send
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send email. Please try again later.");
        }
    }

    // Method to show an alert
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to clear input fields
    private void clearFields() {
        authorEmailField.clear();
        subjectField.clear();
        applicationRequestArea.clear();
    }

    // Method to validate email format using regular expression
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
