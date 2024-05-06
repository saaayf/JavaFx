package com.skillseekr.Claims;

import com.skillseekr.Models.Claims.Reclamation;
import com.skillseekr.Services.Claims.ServiceReclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class editReclamation {

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField user_idTextField;

    @FXML
    private TextArea ContentTextField;

    @FXML
    private Button saveButton;

    private ServiceReclamation serviceReclamation = new ServiceReclamation(); // Initialize the service

    // Method to initialize data in the fields
    public void initData(Reclamation reclamation) {
        // Set values in the fields based on the Reclamation object
        titleTextField.setText(reclamation.getTitle());
        user_idTextField.setText(String.valueOf(reclamation.getUser_id()));
        ContentTextField.setText(reclamation.getContent());
    }

    // Method to handle saving changes
    @FXML
    private void handleSaveButtonAction() {
        String title = titleTextField.getText();
        String user_id = user_idTextField.getText();
        String content = ContentTextField.getText();

        // Create a new Reclamation object with the updated data
        Reclamation reclamation = new Reclamation(Integer.parseInt(user_id), title, content);

        // Update the Reclamation using the service
        try {
            serviceReclamation.update(reclamation);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any potential exceptions here
        }
    }

}
