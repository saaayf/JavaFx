package com.skillseekr.Offer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.skillseekr.Models.Offers.*;
import java.io.IOException;
import java.net.URL;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;




public class OfferController {
    @FXML
    private HBox logout;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private Button AddOffer;
    @FXML
    private Button BrowseOffer;

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == AddOffer) {
            loadStage("/com/Skillseekr/Offer/addOffer.fxml");
        }
    }
    @FXML
    private void handleButtonBrowse(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == BrowseOffer) {
            loadPage("/com/Skillseekr/Offer/Browse.fxml");
        }

    }

    // Method to load a new stage
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
    private void loadPage(String fxml) {
        try {
            URL resourceUrl = getClass().getResource(fxml);
            if (resourceUrl == null) {
                throw new IllegalArgumentException("FXML file not found: " + fxml);
            }
            Parent root = FXMLLoader.load(resourceUrl);
            Scene scene = BrowseOffer.getScene(); // Get the scene from any UI element, assuming all UI elements are in the same scene
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleUsersButtonClick() {
        try {
            AnchorPane showOfferPane = FXMLLoader.load(getClass().getResource("/com/Skillseekr/Offer/showOffer.fxml"));
            contentPane.getChildren().setAll(showOfferPane);
        } catch (IOException e) {
            e.printStackTrace();
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
}
