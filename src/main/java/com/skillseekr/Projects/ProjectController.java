package com.skillseekr.Projects;

import com.skillseekr.User.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ProjectController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    @FXML
    HBox BtnMesProjets;

    @FXML
    private void handleUsersButtonClick() {
        Parent showUserPane = null ;

        try {
            if (SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN"))
            {
                showUserPane = FXMLLoader.load(getClass().getResource("/com/skillseekr/Projects/ProjetsList.fxml"));
            }
            else
            {
                showUserPane = FXMLLoader.load(getClass().getResource("/com/skillseekr/Projects/ListProjetsFront.fxml"));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProjetsRouter.getInstance().showContent(showUserPane);
    }

    @FXML
    private void  GoMesProjets()
    {
        Parent root = null ;
        try {
             root = FXMLLoader.load(getClass().getResource("/com/skillseekr/Projects/MesProjets.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProjetsRouter.getInstance().showContent(root);
    }

    @FXML void GoHome()
    {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane showUserPane = null;
        ProjetsRouter.getInstance().setContent(contentPane);
        contentPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/skillseekr/style.css")).toExternalForm());

       // BtnMesProjets.getScene().setUserAgentStylesheet("/style.css");
        if(SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN")) {
            this.BtnMesProjets.setVisible(false);
        }


        try {
            if (SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN"))
            {
                showUserPane = FXMLLoader.load(getClass().getResource("/com/skillseekr/Projects/ProjetsList.fxml"));
            }
            else
            {
                showUserPane = FXMLLoader.load(getClass().getResource("/com/skillseekr/Projects/ListProjetsFront.fxml"));

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ProjetsRouter.getInstance().showContent(showUserPane);
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
        Stage stage = (Stage) BtnMesProjets.getScene().getWindow();
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
