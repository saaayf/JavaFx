package com.skillseekr.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import javafx.scene.input.MouseEvent;



public class Back {
    @FXML
    private AnchorPane contentPane;

    @FXML
    private HBox logout;
    @FXML
    private Button AddUser;


    @FXML
    private void handleUsersButtonClick() {
        try {
            AnchorPane showUserPane = FXMLLoader.load(getClass().getResource("/com/Skillseekr/User/showUser.fxml"));
            contentPane.getChildren().setAll(showUserPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void handleLogout(MouseEvent event) {
        // Close the current stage
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();

        try {
            // Load the login page content
            Parent loginPage = FXMLLoader.load(getClass().getResource("/com/skillseekr/User/login.fxml"));

            // Get the current scene and set the login page as its root
            Scene scene = new Scene(loginPage);

            // Set the scene in the current stage and show it
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == AddUser) {
            loadStage("/com/Skillseekr/User/addUser.fxml");
        }
    }
    @FXML
    private void handleProfile() {
        try {
            // Charger le fichier FXML editProfile.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/com/Skillseekr/User/editProfile.fxml"));
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour la scène d'édition de profil
            Stage editProfileStage = new Stage();
            editProfileStage.setScene(scene);
            editProfileStage.setTitle("Profile");

            // Afficher la fenêtre d'édition de profil
            editProfileStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer l'exception si le chargement du fichier editProfile.fxml échoue
        }
    }

    private void loadStage(String fxml) {
        try {
            URL resourceUrl = getClass().getResource(fxml);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("FXML file not found: " + fxml);
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
