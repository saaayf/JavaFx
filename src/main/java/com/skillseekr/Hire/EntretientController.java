package com.skillseekr.Hire;

import com.skillseekr.Models.Hire.Entretient;
import com.skillseekr.Services.Hire.ServiceEntretient;
//import com.skillseekr.Utils.sendMail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class EntretientController implements Initializable {


    @FXML
    private DatePicker datee;

    @FXML
    private TableColumn<Entretient, Date> dateeColumn;

    @FXML
    private TextField type;

    @FXML
    private TableColumn<Entretient, String> typeColumn;

    @FXML
    private TextField ide;

    @FXML
    private TableColumn<Entretient, Integer> ideColumn;

    @FXML
    private GridPane pnlHost;

    @FXML
    private TextField resultat;

    @FXML
    private TableColumn<Entretient, String> resultatColumn;

    @FXML
    private TextField id_rec_id ;

    @FXML
    private TableColumn<Entretient, Integer> idrecColumn;

    @FXML
    private TableView<Entretient> recruTableView;

    @FXML
    void AddEntretien(ActionEvent event) {
        try {
            String typeText = type.getText();
            String resultatText = resultat.getText();
            String idRecIdText = id_rec_id.getText();

            // Validation des champs
            if (typeText.isEmpty() || resultatText.isEmpty() || idRecIdText.isEmpty()) {
                showAlert("Error", "Tous les champs doivent être remplis!");
                return;
            }

            // Validation pour id_rec_id si c'est un entier
            if (!idRecIdText.matches("\\d+")) {
                showAlert("Error", "L'identifiant de recrutement doit être un nombre entier.");
                return;
            }

            ServiceEntretient serviceEntretient = new ServiceEntretient();
            Entretient entretient = new Entretient(Date.valueOf(datee.getValue()), typeText, resultatText, Integer.parseInt(idRecIdText));
            serviceEntretient.add(entretient);
            ShowEntretient();
          //  sendMail.send ();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "Erreur lors de l'ajout de l'entretien.");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    void ShowEntretient() {
        ServiceEntretient serviceEntretient = new ServiceEntretient();

        ideColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        resultatColumn.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        idrecColumn.setCellValueFactory(new PropertyValueFactory<>("id_rec_id"));
        dateeColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Populate TableView with data
        try {
            List<Entretient> entretients = serviceEntretient.show();
            ObservableList<Entretient> entrObservableList = FXCollections.observableArrayList(entretients);
            recruTableView.setItems(entrObservableList);
            recruTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldEntretient, newEntretient) -> {
                if (newEntretient != null) {
                    ide.setText(String.valueOf(newEntretient.getId()));
                    type.setText(newEntretient.getType());
                    resultat.setText(newEntretient.getResultat());
                    id_rec_id.setText(String.valueOf(newEntretient.getId_rec_id()));
                    datee.setValue(newEntretient.getDate().toLocalDate());
                } else {
                    ide.clear();
                    type.clear();
                    resultat.clear();
                    id_rec_id.clear();
                    datee.setValue(null);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowEntretient();
    }

    @FXML
    void DeleteEntretien(ActionEvent event) {
        try {
            ServiceEntretient serviceEntretient = new ServiceEntretient();
            Entretient entretient = new Entretient(Integer.parseInt(ide.getText()),Date.valueOf(datee.getValue()),type.getText(),resultat.getText(),Integer.parseInt(id_rec_id.getText()));
            serviceEntretient.delete(entretient);
            ShowEntretient();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void UpdateEntretien(ActionEvent event) {
        try {
            ServiceEntretient serviceEntretient = new ServiceEntretient();
            Entretient entretient = new Entretient(Integer.parseInt(ide.getText()),Date.valueOf(datee.getValue()),type.getText(),resultat.getText(),Integer.parseInt(id_rec_id.getText()));
            serviceEntretient.update(entretient);
            ShowEntretient();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}