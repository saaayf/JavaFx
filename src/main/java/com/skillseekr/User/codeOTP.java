package com.skillseekr.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.skillseekr.API.EmailAPI;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class codeOTP {

    @FXML
    private TextField digit1;

    @FXML
    private TextField digit2;

    @FXML
    private TextField digit3;

    @FXML
    private TextField digit4;

    @FXML
    private TextField digit5;

    @FXML
    private TextField digit6;

    private String email;
    private String generatedOTP; // Store the generated OTP

    public void setEmail(String email) {
        this.email = email;
        // Generate OTP and send email
        generatedOTP = EmailAPI.generateRandomCode(6); // Assuming OTP length is 6 characters
        EmailAPI.sendEmailWithOTP(email, generatedOTP);
    }

    @FXML
    private void verifyOTPButtonClicked(MouseEvent event) {
        String enteredOTP = digit1.getText() + digit2.getText() + digit3.getText()
                + digit4.getText() + digit5.getText() + digit6.getText();

        // Vérifie si le code saisi correspond au code généré
        if (enteredOTP.equals(generatedOTP)) {
            try {
                // Charge le fichier resetPassword.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetPassword.fxml"));
                Parent root = loader.load();

                // Obtient le contrôleur du fichier resetPassword.fxml
                resetPassword resetPasswordController = loader.getController();

                // Vérifie la validité de l'adresse email avant de la passer
                if (email != null && !email.isEmpty()) {
                    resetPasswordController.setEmail(email);
                } else {
                    throw new IllegalStateException("L'adresse e-mail est invalide.");
                }

                // Crée une nouvelle scène
                Scene scene = new Scene(root);

                // Obtient la scène actuelle à partir de l'événement
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Affiche la nouvelle scène
                currentStage.setScene(scene);
                currentStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Affiche une alerte indiquant que le code saisi est invalide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Code OTP invalide");
            alert.setContentText("The OTP code you entered is incorrect. Please verify your code.");
            alert.showAndWait();
        }
    }
}
