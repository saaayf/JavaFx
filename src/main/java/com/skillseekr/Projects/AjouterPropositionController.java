package com.skillseekr.Projects;

import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Models.projet.Proposition;
import com.skillseekr.Models.projet.PropositionSuggestion;
import com.skillseekr.Services.projets.ServiceProposition;
import com.skillseekr.Services.projets.VertexAIService;
import com.skillseekr.User.SessionManager;
import impl.org.controlsfx.skin.AutoCompletePopup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;





public class AjouterPropositionController implements Initializable {
    private Projet projet;
    @javafx.fxml.FXML
    private TextArea tfDesc;
    @javafx.fxml.FXML
    private TextField tfBudget;
    @javafx.fxml.FXML
    private DatePicker DateDelai;
    @javafx.fxml.FXML
    private Button BtnAjouter;
    @javafx.fxml.FXML
    private Button BtnAnnuler;
    @javafx.fxml.FXML
    private Button BtnRetour;

    private Alert alert = new Alert(Alert.AlertType.NONE);

    private ServiceProposition serviceProposition = new ServiceProposition();

    private  Map<String,String> suggestionMap = null;

    List<PropositionSuggestion> suggestions = null;

    ObservableList<String> suggestionTitles = null;


    public void setProjet(Projet projet) {
        this.projet = projet;
        tfDesc.setWrapText(true);

        try {
            this.suggestionMap = VertexAIService.getSuggestions(projet.getTitre(),projet.getCompetences());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // show the map
        System.out.println(suggestionMap);
        // convert the map<string;string> to a list of PropositionSuggestion
         this.suggestions = suggestionMap.entrySet().stream()
                .map(entry -> new PropositionSuggestion(entry.getKey(), entry.getValue()))
                .toList();
        ObservableList<PropositionSuggestion> ObsSuggestion  = FXCollections.observableArrayList(suggestions);



        AutoCompletePopup<PropositionSuggestion> popup = new AutoCompletePopup<>();
        popup.getSuggestions().addAll(ObsSuggestion);
        // Listen for changes in the TextField
        tfDesc.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                // Filter suggestions based on current text
                List<PropositionSuggestion> filteredSuggestions = this.suggestions.stream()
                        .filter(suggestion -> suggestion.getTitle().toLowerCase().contains(newValue.toLowerCase()))
                        .toList();
                // Update popup content with filtered suggestions
                if(filteredSuggestions.size() > 0) {
                    popup.getSuggestions().setAll(filteredSuggestions);

                }
                else
                {
                    popup.getSuggestions().setAll(filteredSuggestions);

                }
                tfDesc.setOnKeyPressed(event -> popup.show(tfDesc));
            }
            else
            {
                popup.getSuggestions().setAll(this.suggestions);
                tfDesc.setOnKeyPressed(event -> popup.show(tfDesc));
            }

        });

        popup.setOnSuggestion(suggestion -> {
            // Set the TextField text to the selected suggestion description
            tfDesc.setText(suggestion.getSuggestion().getDescription());
            // Hide the popup
            popup.hide();
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @javafx.fxml.FXML
    public void OnAjouterClicked(ActionEvent actionEvent) {
        if (tfDesc.getText().isEmpty() || tfBudget.getText().isEmpty() || DateDelai.getValue() == null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.show();
        } else if (tfDesc.getText().length() < 3) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("La description doit contenir au moins 3 caractères");
            alert.show();
        } else if (!tfBudget.getText().matches("[0-9]+(\\.[0-9][0-9]?)?")) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Le budget doit être un nombre");
            alert.show();
        } else {
            try {
                Proposition proposition = new Proposition();
                proposition.setDescription(tfDesc.getText());
                proposition.setPropBudget(Float.parseFloat(tfBudget.getText()));
                proposition.setPropDelai(DateDelai.getValue());
                proposition.setProjet(projet);
                proposition.setIdUser(SessionManager.getCurrentUser().getId());
                serviceProposition.add(proposition);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Proposition ajoutée avec succès");
                alert.show();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects//PropositionsList.fxml"));
                Parent root = loader.load();
                PropositionsListController controller = loader.getController();
                controller.setProjet(projet);
                ProjetsRouter.getInstance().showContent(root);
            } catch (Exception e) {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Erreur lors de l'ajout de la proposition");
                alert.show();
            }
        }
    }

    @javafx.fxml.FXML
    public void OnAnnulerClicked(ActionEvent actionEvent) {
        tfBudget.clear();
        tfDesc.clear();
        DateDelai.setValue(null);
    }

    @javafx.fxml.FXML
    public void OnRetourClicked(ActionEvent actionEvent) throws SQLException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/skillseekr/Projects//PropositionsList.fxml"));
        Parent root = loader.load();
        PropositionsListController controller = loader.getController();
        controller.setProjet(projet);
        ProjetsRouter.getInstance().showContent(root);
    }


}
