package com.skillseekr.Offer;

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
import java.sql.Date; // For java.sql.Date
import java.time.LocalDate; // For LocalDate // For Offer
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.SelectionMode;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.Label;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javafx.scene.control.Alert;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextArea;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;


public class addOffer {
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
    private void populateLocationComboBox() {
        // Get all Location enum values
        Location[] locations = Location.values();

        // Create an ObservableList from the enum values
        ObservableList<Location> locationList = FXCollections.observableArrayList(locations);

        // Set the items of the locationComboBox
        locationComboBox.setItems(locationList);
    }

    private void populateMotiveComboBox() {
        // Get all Motive enum values
        Motive[] motives = Motive.values();

        // Create an ObservableList from the enum values
        ObservableList<Motive> motiveList = FXCollections.observableArrayList(motives);

        // Set the items of the motiveComboBox
        motiveComboBox.setItems(motiveList);
    }

    private void populateTypeComboBox() {
        // Get all Type enum values
        Type[] types = Type.values();

        // Create an ObservableList from the enum values
        ObservableList<Type> typeList = FXCollections.observableArrayList(types);

        // Set the items of the typeComboBox
        typeComboBox.setItems(typeList);
    }

    private void populateSkillsListView() throws SQLException {
        // Get all skills from the service
        List<Skill> allSkills = offerService.getAllSkills();

        // Create an ObservableList from the skills
        ObservableList<Skill> observableSkills = FXCollections.observableArrayList(allSkills);

        // Set the items of the skillsListView
        skillsListView.setItems(observableSkills);
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


    // Method to save the selected file to the specified directory
    private void saveFile(File file) {
        // Specify the directory where you want to save the file
        File destinationDirectory = new File("src/main/resources/Public");

        // Ensure the directory exists; create it if it doesn't
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs(); // Create directories if they don't exist
        }

        // Construct the destination file path
        File destinationFile = new File(destinationDirectory.getAbsolutePath() + File.separator + file.getName());

        // Copy the selected file to the destination directory
        try {
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Set the selectedFileName to the name of the saved file
            selectedFileName = destinationFile.getName();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleResetButtonAction(ActionEvent event) {
        // Clear all form fields
        titleTextField.setText("");
        authorTextField.setText("");
        descriptionTextField.setText("");
        created_at.setValue(null); // Clear DatePicker value
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
    private void initialize() {
        // Initialize ServiceOffer
        offerService = new ServiceOffer();
        // Initialize the allSkills field with an empty ObservableList
        this.allSkills = FXCollections.observableArrayList();

        // Populate ComboBoxes with enum values
        populateLocationComboBox();
        populateMotiveComboBox();
        populateTypeComboBox();

        // Populate skills ListView
        try {
            populateSkillsListView();
            allSkills = skillsListView.getItems(); // Initialize allSkills here
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create a toggle group
        toggleGroup = new ToggleGroup();

        // Assign the toggle group to the radio buttons
        toggleGroup = new ToggleGroup();

        // Assign the toggle group to the radio buttons
        draftRadioButton.setToggleGroup(toggleGroup);
        archivedRadioButton.setToggleGroup(toggleGroup);
        publishedRadioButton.setToggleGroup(toggleGroup);
        wipRadioButton.setToggleGroup(toggleGroup);

        // Set selection mode of the skills ListView to single selection
        skillsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Initialize searchInput after searchTextField has been initialized
        searchInput = searchTextField.getText().trim().toLowerCase();

        // Add a listener to the textProperty of the searchTextField
        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Update searchInput whenever the text in the searchTextField changes
                searchInput = newValue.trim().toLowerCase();

                // Call the method to handle real-time search
                handleRealTimeSearch();
            }
        });
    }

    // Method to handle real-time search
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

    @FXML
    public void handleSaveButtonAction(ActionEvent event) {
        // Get the values entered by the user
        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String author = authorTextField.getText();
        LocalDate createdAt = created_at.getValue(); // Using LocalDate from DatePicker
        Location location = locationComboBox.getValue();
        Motive motive = motiveComboBox.getValue();
        Type type = typeComboBox.getValue();
        Status status = null; // Initialize status variable

        // Determine the selected status based on radio button selection
        if (draftRadioButton.isSelected()) {
            status = Status.Draft;
        } else if (publishedRadioButton.isSelected()) {
            status = Status.Published;
        } else if (archivedRadioButton.isSelected()) {
            status = Status.Archived;
        } else if (wipRadioButton.isSelected()) {
            status = Status.WIP;
        }

        // Get the selected skills from the ListView
        ObservableList<Skill> selectedSkillsList = skillsListView.getSelectionModel().getSelectedItems();
        List<Skill> selectedSkills = new ArrayList<>(selectedSkillsList);

        // Create the Offer object
        Offer offer = new Offer();
        offer.setTitle(title);
        offer.setDescription(description);
        offer.setAuthor(author);
        offer.setCreated_at(Date.valueOf(createdAt)); // Convert LocalDate to java.sql.Date
        offer.setLocation(location);
        offer.setMotive(motive);
        offer.setType(type);
        offer.setStatus(status);
        offer.setFile_name(selectedFileName); // Set the file name
        offer.setSkills(selectedSkills); // Set the selected skills

        // Perform input validation
        String validationError = OfferInputValidation.validateAllFieldsNotNull(offer);
        if (validationError != null) {
            // Display an error message
            showAlert("Validation Error", validationError, Alert.AlertType.ERROR);
            return; // Exit the method if validation fails
        }

        try {
            // Save the offer using the ServiceOffer class
            offerService.add(offer);
            // Show success message
            showAlert("Success", "Offer Created successfully!", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, display an error message
            showAlert("Error", "An error occurred while creating the offer.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}