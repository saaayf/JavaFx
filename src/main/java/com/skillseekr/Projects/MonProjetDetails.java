package com.skillseekr.Projects;

import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Services.projets.ServiceProjet;
import com.skillseekr.User.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class MonProjetDetails{
    @javafx.fxml.FXML
    private Button BtnModif;
    @javafx.fxml.FXML
    private Button BtnSupprimer;
    @javafx.fxml.FXML
    private Button BtnRetour;
    @javafx.fxml.FXML
    private TextField tfTitre;
    @javafx.fxml.FXML
    private TextField tfCompet;
    @javafx.fxml.FXML
    private TextField tfBudget;
    @javafx.fxml.FXML
    private TextField tfprop;
    @javafx.fxml.FXML
    private DatePicker DatePickerDebut;
    @javafx.fxml.FXML
    private DatePicker Datepickerfin;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private ServiceProjet serviceProjet = new ServiceProjet();

    private Projet projet;
    @javafx.fxml.FXML
    private Button BtnPropo;

    @javafx.fxml.FXML
    public void OnModifClicked(ActionEvent actionEvent) {
        if (tfTitre.getText().isEmpty() || tfCompet.getText().isEmpty() || tfBudget.getText().isEmpty() || tfprop.getText().isEmpty() || DatePickerDebut.getValue() == null || Datepickerfin.getValue() == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        }
        else if (tfTitre.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le titre doit contenir au moins 3 caractères");
            alert.show();
        }
        else if (tfCompet.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Les compétences doivent contenir au moins 3 caractères");
            alert.show();
        }
        else if (!tfBudget.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le budget doit être un nombre");
            alert.show();
        }
        else if (tfprop.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le nom du propriétaire doit contenir au moins 3 caractères");
            alert.show();
        }
        else if (tfprop.getText().length() > 255) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le nom du propriétaire doit contenir au maximum 255 caractères");
            alert.show();
        }
        else {
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Voulez-vous vraiment modifier ce projet ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.CANCEL) {
                return;
            }
            try {
                this.projet.setBudget(Float.parseFloat(tfBudget.getText()));
                this.projet.setCompetences(tfCompet.getText());
                this.projet.setProprietaire(tfprop.getText());
                this.projet.setTitre(tfTitre.getText());
                this.projet.setDate_deb(DatePickerDebut.getValue());
                this.projet.setDate_fin(Datepickerfin.getValue());
                serviceProjet.update(projet);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Projet modifié avec succès");
                alert.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @javafx.fxml.FXML
    public void OnSupprimerClicked(ActionEvent actionEvent) {
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Voulez-vous vraiment supprimer ce projet ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.CANCEL) {
            return;
        }
        try {
            serviceProjet.delete(projet.getId());
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Projet supprimé avec succès");
            alert.show();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ProjetsList.fxml"));
            ProjetsRouter.getInstance().showContent(loader.load());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        if(SessionManager.getCurrentUser().getRoles().contains("ROLE_ADMIN")){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ProjetsList.fxml"));
            ProjetsRouter.getInstance().showContent(loader.load());
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ListProjetsFront.fxml"));
            ProjetsRouter.getInstance().showContent(loader.load());
        }
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
        this.tfTitre.setText(projet.getTitre());
        this.tfCompet.setText(projet.getCompetences());
        this.tfBudget.setText(String.valueOf(projet.getBudget()));
        this.tfprop.setText(projet.getProprietaire());
        this.DatePickerDebut.setValue(projet.getDate_deb());
        this.Datepickerfin.setValue(projet.getDate_fin());
    }

    @javafx.fxml.FXML
    public void GoProp(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/MypropositionList.fxml"));
        Parent root = loader.load();
        MyPropositonListController controller = loader.getController();
        controller.setProjet(projet);
        ProjetsRouter.getInstance().showContent(root);
    }
}
