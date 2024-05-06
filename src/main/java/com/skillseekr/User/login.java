package com.skillseekr.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.skillseekr.Models.User.User;
import com.skillseekr.Services.User.ServiceUser;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.prefs.Preferences;
import javafx.scene.control.CheckBox;


public class login {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Pane loginPane;

    private ServiceUser userService;

    private Preferences preferences;

    @FXML
    private CheckBox rememberMeCheckBox;
    public login() {
        userService = new ServiceUser(); // Initialise ServiceUser
        preferences = Preferences.userNodeForPackage(login.class);
    }




    @FXML
    public void loginButton2() {
        try {
            String email = emailField.getText().trim();
            String password = passwordField.getText();

            // Vérifie si l'email est vide
            if (email.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter your email.");
                return;
            }

            // Vérifie si l'email contient '@'
            if (!email.contains("@")) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter a valid email address containing '@'.");
                return;
            }

            // Vérifie si le mot de passe est vide
            if (password.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please enter your password.");
                return;
            }

            // Vérifie si "Se souvenir de moi" est sélectionné pour la connexion automatique
            if (rememberMeCheckBox.isSelected()) {
                saveCredentials(email, password);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Please check 'Remember me' to sign in automatically.");
                return;
            }

            boolean authenticated = userService.authenticateUser(email, password);

            if (authenticated) {
                User Utilisateur =userService.getUserByEmail(email);
                SessionManager.setCurrentUser(Utilisateur);
                redirectToBackInterface();
            } else {
                // Vérifie si l'email est correct mais le mot de passe est incorrect
                if (userService.emailExists(email)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Your password is incorrect. Please try again.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Email does not exist. Please check your email or register.");
                }
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to authenticate user: " + e.getMessage());
        }


    }




    private void saveCredentials(String email, String password) {
        preferences.put("email", email);
        preferences.put("password", password);
        // You can add additional preferences if needed
    }

    private void redirectToBackInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Skillseekr/SSMain.fxml"));
            Parent root = loader.load();

            // Get the primary screen bounds
            Screen screen = Screen.getPrimary();
            double screenWidth = screen.getBounds().getWidth();
            double screenHeight = screen.getBounds().getHeight();

            Scene scene = new Scene(root, screenWidth, screenHeight);

            Stage stage = (Stage) loginPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "To Access SkillSeekr " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleRegisterClick(ActionEvent event) throws IOException {
        // Charger le fichier FXML signUp.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/User/signUp.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène pour la fenêtre signUp
        Scene signUpScene = new Scene(root);

        // Créer une nouvelle fenêtre pour la scène signUpScene
        Stage signUpStage = new Stage();
        signUpStage.setScene(signUpScene);
        signUpStage.setTitle("Sign Up");

        // Afficher la nouvelle fenêtre signUpStage sans fermer la fenêtre de login
        signUpStage.show();
    }


    @FXML
    private void handleForgotPasswordClick(ActionEvent event) {
        try {
            // Load the FXML file using getResourceAsStream
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ForgotPassword.fxml"));
            Parent root = loader.load();

            // Access the controller of the loaded FXML (if needed)
            ForgotPassword forgotPassword = loader.getController();

            // Create a new stage for the ForgotPassword window
            Stage forgotPasswordStage = new Stage();
            forgotPasswordStage.setScene(new Scene(root));
            forgotPasswordStage.setTitle("Forgot Password");

            // Set modality to APPLICATION_MODAL to make the window modal
            forgotPasswordStage.initModality(Modality.APPLICATION_MODAL);

            // Show the ForgotPassword window and wait for it to be closed
            forgotPasswordStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., show an error dialog)
        }
    }



}
