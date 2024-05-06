package com.skillseekr.Hire;
import javafx.scene.control.*;

import com.skillseekr.Models.Hire.Recrutement;
import com.skillseekr.Models.Offers.Offer;
import com.skillseekr.Models.Offers.Skill;
import com.skillseekr.Services.Hire.ServiceRecrutement;
import com.skillseekr.Services.Offers.ServiceOffer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class RecrutementController implements Initializable {
    public Button sortButton;
    @FXML
    private FlowPane calendar;

    @FXML
    private DatePicker date;

    @FXML
    private TableColumn<Recrutement, Date> dateColumn;

    @FXML
    private TextField description;

    @FXML
    private TableColumn<Recrutement, String> descriptionColumn;

    @FXML
    private TextField id;

    @FXML
    private TableColumn<Recrutement, Integer> idColumn;

    @FXML
    private GridPane pnlHost;

    @FXML
    private TextField statut;

    @FXML
    private TableColumn<Recrutement, String> statutColumn;

    @FXML
    private TextField titre;

    @FXML
    private TableColumn<Recrutement, String> titreColumn;

    @FXML
    private TableView<Recrutement> recruTableView;
    @FXML
    private TextField searchField;
    @FXML



    void  searchRecrutement() {
        ServiceRecrutement serviceRecrutement = new ServiceRecrutement();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        String searchText = searchField.getText(); // Obtenir le texte du champ de recherche

        // Populate TableView with filtered data
        try {
            List<Recrutement> recrutements = serviceRecrutement.searchByStatut(searchText);
            ObservableList<Recrutement> recruObservableList = FXCollections.observableArrayList(recrutements);
            recruTableView.setItems(recruObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }


    @FXML
    void AddRecrutement(ActionEvent event) {
        // Vérifier si tous les champs requis sont remplis
        if (titre.getText().isEmpty() || description.getText().isEmpty() || date.getValue() == null || statut.getText().isEmpty()) {
            // Afficher un message d'erreur ou effectuer une action appropriée si les champs requis ne sont pas remplis
            // Par exemple, afficher une alerte
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("\"Error\" \"Tous les champs doivent être remplis!\"");
            alert.showAndWait();
            return; // Arrêter l'exécution de la méthode


        }

        // Tous les champs sont remplis, ajouter le recrutement
        try {
            ServiceRecrutement serviceRecrutement = new ServiceRecrutement();
            Recrutement recrutement = new Recrutement(titre.getText(), description.getText(), Date.valueOf(date.getValue()), statut.getText());
            serviceRecrutement.add(recrutement);
            ShowRecrutement();
          //  SmsController.Sms();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    void ShowRecrutement() {
        ServiceRecrutement serviceRecrutement = new ServiceRecrutement();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Populate TableView with data
        try {
            List<Recrutement> recrutements = serviceRecrutement.show();
            ObservableList<Recrutement> recruObservableList = FXCollections.observableArrayList(recrutements);
            recruTableView.setItems(recruObservableList);
            recruTableView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldRecrutement, newRecrutement) -> {
                if (newRecrutement != null) {
                    id.setText(String.valueOf(newRecrutement.getId()));
                    titre.setText(newRecrutement.getTitre());
                    description.setText(newRecrutement.getDescription());
                    statut.setText(newRecrutement.getStatut());
                    date.setValue(newRecrutement.getDate().toLocalDate());
                } else {
                    id.clear();
                    titre.clear();
                    description.clear();
                    statut.clear();
                    date.setValue(null);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShowRecrutement();
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchRecrutement());

    }


    @FXML
    void DeleteRecrutement(ActionEvent event) {
        try {
            ServiceRecrutement serviceRecrutement = new ServiceRecrutement();
            Recrutement recrutement = new Recrutement(Integer.parseInt(id.getText()),titre.getText(),description.getText(),Date.valueOf(date.getValue()),statut.getText());
            serviceRecrutement.delete(recrutement);
            ShowRecrutement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void UpdateRecrutement(ActionEvent event) {
        try {
            ServiceRecrutement serviceRecrutement = new ServiceRecrutement();
            Recrutement recrutement = new Recrutement(Integer.parseInt(id.getText()),titre.getText(),description.getText(),Date.valueOf(date.getValue()),statut.getText());
            serviceRecrutement.update(recrutement);
            ShowRecrutement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void sortRecrutementByTitre() {
        ServiceRecrutement serviceRecrutement = new ServiceRecrutement();

        // Populate TableView with sorted data
        try {
            List<Recrutement> recrutements = serviceRecrutement.show();
            recrutements.sort(Comparator.comparing(Recrutement::getTitre));
            ObservableList<Recrutement> recruObservableList = FXCollections.observableArrayList(recrutements);
            recruTableView.setItems(recruObservableList);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., show an error message)
        }
    }

}



