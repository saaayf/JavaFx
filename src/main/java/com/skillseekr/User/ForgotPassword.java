package com.skillseekr.User;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.skillseekr.API.EmailAPI;
import java.io.IOException;

public class ForgotPassword {

    @FXML
    private TextField emailField;

    private String email; // Variable to store the email address

    @FXML
    private void continueButtonClicked(MouseEvent event) {
        email = emailField.getText(); // Store the email address entered by the user

        if (isUserExist(email)) {
            generateVerificationCode();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeOTP.fxml"));
                Parent root = loader.load();

                // Access the controller of the loaded FXML
                codeOTP codeOTPController = loader.getController();
                codeOTPController.setEmail(email); // Pass the email to codeOTP controller

                // Replace the current content with the loaded FXML content
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                // Handle any potential exceptions
                e.printStackTrace();
            }
        } else {
            // User does not exist, show an alert
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("User Not Found");
            alert.setHeaderText(null);
            alert.setContentText("The user does not exist. Please enter a valid email address.");
            alert.showAndWait();
        }
    }

    private boolean isUserExist(String emailAddress) {
        // Perform the logic to check if the user exists
        // You can replace this with your own implementation
        // For demonstration purposes, let's assume the user exists if the email address is not empty
        return !emailAddress.isEmpty();
    }

    public void generateVerificationCode() {
        // Get the email address from the text field
        String emailAddress = emailField.getText();

        // Send the email with the OTP code
        String otp = EmailAPI.generateRandomCode(6); // Utilisation de la méthode de génération de code OTP
        EmailAPI.sendEmailWithOTP(emailAddress, otp);

        // Display a message to indicate that the email is sent
        System.out.println("Email sent to " + emailAddress + " with OTP code: " + otp);
    }
}
