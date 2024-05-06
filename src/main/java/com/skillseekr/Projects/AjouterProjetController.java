package com.skillseekr.Projects;

import com.skillseekr.User.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Services.projets.ServiceProjet;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterProjetController {
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
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnRetour;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private ServiceProjet serviceProjet = new ServiceProjet();

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) throws SQLException, IOException {
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

        // the budget must contain only numbers and point
        else if (!tfBudget.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le budget doit être un nombre");
            alert.show();
        }
        else if (Float.parseFloat(tfBudget.getText()) < 0) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le budget doit être positif");
            alert.show();
        }
        else if(DatePickerDebut.getValue().isAfter(Datepickerfin.getValue())) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("La date de début doit être avant la date de fin");
            alert.show();
        }
        else if (tfprop.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le propriétaire doit contenir au moins 3 caractères");
            alert.show();
        }


        else {
            Projet projet = new Projet();
            projet.setTitre(tfTitre.getText());
            projet.setCompetences(tfCompet.getText());
            projet.setBudget(Float.parseFloat(tfBudget.getText()));
            projet.setDate_deb(DatePickerDebut.getValue());
            projet.setDate_fin(Datepickerfin.getValue());
            projet.setProprietaire(tfprop.getText());
            projet.setIdUser(SessionManager.getCurrentUser().getId());
            serviceProjet.add(projet);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Projet ajouté avec succès");
            alert.show();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ListProjetsFront.fxml"));
            ProjetsRouter.getInstance().showContent(loader.load());
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfBudget.clear();
        tfCompet.clear();
        tfprop.clear();
        tfTitre.clear();
        DatePickerDebut.getEditor().clear();
        Datepickerfin.getEditor().clear();
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects/ListProjetsFront.fxml"));
        ProjetsRouter.getInstance().showContent(loader.load());
    }
}
