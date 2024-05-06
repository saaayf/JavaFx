package com.skillseekr.Offer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import com.skillseekr.Utils.OfferInputValidation;
import com.skillseekr.Models.Offers.Location;
import com.skillseekr.Models.Offers.Offer;
import com.skillseekr.Models.Offers.Motive;
import com.skillseekr.Models.Offers.Status;
import com.skillseekr.Models.Offers.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.skillseekr.Models.Offers.Skill;
import com.skillseekr.Services.Offers.ServiceOffer;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SelectionMode;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Label;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import javafx.scene.control.TextArea;


public class editOffer {

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;
    @FXML
    private Label selectedFileLabel;
    @FXML
    private ToggleGroup toggleGroup;


    @FXML
    private ComboBox<Location> locationComboBox;

    @FXML
    private ComboBox<Motive> motiveComboBox;

    @FXML
    private ComboBox<Type> typeComboBox;

    @FXML
    private RadioButton archivedRadioButton;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextArea descriptionTextField;

    @FXML
    private RadioButton draftRadioButton;

    @FXML
    private RadioButton publishedRadioButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button saveButton;
    @FXML
    private ListView<Skill> skillsListView;


    private ObservableList<Skill> allSkills;


    @FXML
    private TextField titleTextField;

    @FXML
    private Button uploadButton;

    @FXML
    private RadioButton wipRadioButton;

    @FXML
    private DatePicker created_at;

    private ServiceOffer offerService;
    private String selectedFileName;
    private String searchInput;
    private Offer offerToEdit;


    public void initData(Offer offer) {
        offerToEdit = offer;
        titleTextField.setText(offer.getTitle());
        authorTextField.setText(offer.getAuthor());
        descriptionTextField.setText(offer.getDescription());

        // Convert java.sql.Date to LocalDate
        LocalDate createdAt = Instant.ofEpochMilli(offer.getCreated_at().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        // Set the value to DatePicker
        created_at.setValue(createdAt);

        locationComboBox.setValue(offer.getLocation());
        motiveComboBox.setValue(offer.getMotive());
        typeComboBox.setValue(offer.getType());

        switch (offer.getStatus()) {
            case Draft:
                draftRadioButton.setSelected(true);
                break;
            case Archived:
                archivedRadioButton.setSelected(true);
                break;
            case Published:
                publishedRadioButton.setSelected(true);
                break;
            case WIP:
                wipRadioButton.setSelected(true);
                break;
        }

        List<Skill> selectedSkills = offer.getSkills();
        for (Skill skill : selectedSkills) {
            skillsListView.getSelectionModel().select(skill);
        }
    }

    @FXML
    private void handleUploadButtonAction(ActionEvent event) {
        // Implement file upload functionality here
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");

        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(null);

        // Check if a file was selected
        if (selectedFile != null) {
            // Update the selectedFileName variable
            selectedFileName = selectedFile.getName();

            // Update the label with the selected file name
            selectedFileLabel.setText(selectedFileName);

            // Perform further operations with the selected file
            // For example, you can save the file to the specified directory
            saveFile(selectedFile);
        }
    }
    private void saveFile(File file) {
        File destinationDirectory = new File("src/main/resources/public");
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }
        File destinationFile = new File(destinationDirectory.getAbsolutePath() + File.separator + file.getName());
        try {
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            selectedFileName = destinationFile.getName();
            // Optionally, update UI or perform other actions after saving the file
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, display an error message if file saving fails
            showAlert("Error", "An error occurred while saving the file.", Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        titleTextField.setText("");
        authorTextField.setText("");
        descriptionTextField.setText("");
        created_at.setValue(null);
        locationComboBox.getSelectionModel().clearSelection();
        motiveComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
        draftRadioButton.setSelected(false);
        archivedRadioButton.setSelected(false);
        publishedRadioButton.setSelected(false);
        wipRadioButton.setSelected(false);
        skillsListView.getItems().clear();
    }

    @FXML
    private void handleSaveButtonAction(ActionEvent event) {
        // Get the values entered by the user
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String author = authorTextField.getText();
        LocalDate createdAt = created_at.getValue();
        Location location = locationComboBox.getValue();
        Motive motive = motiveComboBox.getValue();
        Type type = typeComboBox.getValue();
        Status status = null;

        if (draftRadioButton.isSelected()) {
            status = Status.Draft;
        } else if (publishedRadioButton.isSelected()) {
            status = Status.Published;
        } else if (archivedRadioButton.isSelected()) {
            status = Status.Archived;
        } else if (wipRadioButton.isSelected()) {
            status = Status.WIP;
        }

        ObservableList<Skill> selectedSkillsList = skillsListView.getSelectionModel().getSelectedItems();
        List<Skill> selectedSkills = new ArrayList<>(selectedSkillsList);

        offerToEdit.setTitle(title);
        offerToEdit.setDescription(description);
        offerToEdit.setAuthor(author);
        offerToEdit.setCreated_at(Date.valueOf(createdAt));
        offerToEdit.setLocation(location);
        offerToEdit.setMotive(motive);
        offerToEdit.setType(type);
        offerToEdit.setStatus(status);
        offerToEdit.setSkills(selectedSkills);

        String validationError = OfferInputValidation.validateAllFieldsNotNull(offerToEdit);
        if (validationError != null) {
            showAlert("Validation Error", validationError, Alert.AlertType.ERROR);
            return;
        }

        try {
            offerService.update(offerToEdit);
            showAlert("Success", "Offer updated successfully!", Alert.AlertType.INFORMATION);
            // Optionally, update the UI elements or close the window after successful update
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while updating the offer.", Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        offerService = new ServiceOffer();
        populateLocationComboBox();
        populateMotiveComboBox();
        populateTypeComboBox();
        try {
            populateSkillsListView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        toggleGroup = new ToggleGroup();
        draftRadioButton.setToggleGroup(toggleGroup);
        archivedRadioButton.setToggleGroup(toggleGroup);
        publishedRadioButton.setToggleGroup(toggleGroup);
        wipRadioButton.setToggleGroup(toggleGroup);
        // Set selection mode of the skills ListView to single selection
        skillsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Update searchInput whenever the text in the searchTextField changes
                searchInput = newValue.trim().toLowerCase();

                // Call the method to handle real-time search
                handleRealTimeSearch();
            }
        });
    }

    private void populateLocationComboBox() {
        Location[] locations = Location.values();
        ObservableList<Location> locationList = FXCollections.observableArrayList(locations);
        locationComboBox.setItems(locationList);
    }

    private void populateMotiveComboBox() {
        Motive[] motives = Motive.values();
        ObservableList<Motive> motiveList = FXCollections.observableArrayList(motives);
        motiveComboBox.setItems(motiveList);
    }

    private void populateTypeComboBox() {
        Type[] types = Type.values();
        ObservableList<Type> typeList = FXCollections.observableArrayList(types);
        typeComboBox.setItems(typeList);
    }

    private void populateSkillsListView() throws SQLException {
        // Populate allSkills with skills obtained from the database
        allSkills = FXCollections.observableArrayList(offerService.getAllSkills());
        // Populate the ListView
        skillsListView.setItems(allSkills);
    }
    public void handleRealTimeSearch() {
        if (searchInput.isEmpty()) {
            // If search input is empty, display all skills
            skillsListView.setItems(allSkills);
        } else {
            // Filter skills based on search input
            ObservableList<Skill> filteredSkills = allSkills.filtered(skill ->
                    skill.getSkill().toLowerCase().contains(searchInput));
            skillsListView.setItems(filteredSkills);
        }

        // Ensure that selected items are maintained after filtering
        skillsListView.getSelectionModel().selectAll();
    }
}
