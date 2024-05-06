package com.skillseekr.Offer;

import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.skillseekr.Models.Offers.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javafx.scene.control.TableCell;
import com.skillseekr.Services.Offers.ServiceOffer;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import java.sql.SQLException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.IOUtils;
import javafx.scene.control.Pagination;
import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.beans.value.ObservableValue;
import org.apache.poi.ss.usermodel.Cell;




public class showOffer {
    @FXML
    private Button exportButton;

    @FXML
    private Pagination pagination;

    @FXML
    private ContextMenu contextMenu;

    @FXML
    private MenuItem deleteMenuItem;
    private ServiceOffer serviceOffer;
    @FXML
    private Button AddOffer;

    @FXML
    private TableView<Offer> offerTableView;

    @FXML
    private TableColumn<Offer, Integer> idColumn;

    @FXML
    private TableColumn<Offer, String> titleColumn;

    @FXML
    private TableColumn<Offer, String> descriptionColumn;

    @FXML
    private TableColumn<Offer, String> authorColumn;

    @FXML
    private TableColumn<Offer, Date> createdAtColumn;

    @FXML
    private TableColumn<Offer, Motive> motiveColumn;

    @FXML
    private TableColumn<Offer, Type> typeColumn;

    @FXML
    private TableColumn<Offer, Location> locationColumn;

    @FXML
    private TableColumn<Offer, Status> statusColumn;

    @FXML
    private TableColumn<Offer, String> fileNameColumn;

    @FXML
    private TableColumn<Offer, List<Skill>> skillsColumn;

    @FXML
    private TableColumn<Offer, Void> actionColumn;
    @FXML
    private Button downloadButton;

    @FXML
    private void initialize() {
        // Initialize TableView columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("created_at"));
        motiveColumn.setCellValueFactory(new PropertyValueFactory<>("motive"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        fileNameColumn.setCellValueFactory(new PropertyValueFactory<>("file_name"));
        skillsColumn.setCellValueFactory(new PropertyValueFactory<>("skills"));

        // Set up action column
        setupActionColumn();
        // Load data into TableView
        loadDataIntoTableView();
        refreshTablePeriodically();
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button downloadButton = new Button("Download");

            {
                // Set button styles
                editButton.setStyle("-fx-background-color: #77dd77; -fx-text-fill: white;");
                downloadButton.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");

                // Add download logic here
                downloadButton.setOnAction((event) -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    if (offer.getFile_name() == null) {
                        // Show alert error
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("No attached file to download for this offer");
                        alert.showAndWait();
                    } else {
                        try {
                            downloadMatchingFile(offer.getFile_name(), getMatchingDownloadUrl(offer.getFile_name()));
                        } catch (IOException e) {
                            e.printStackTrace();
                            // Show error message to user
                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Attachement is Downloaded Successfully");
                        alert.showAndWait();
                    }
                });

                editButton.setOnAction((event) -> {
                    Offer offer = getTableView().getItems().get(getIndex());
                    loadEditOfferPage(offer);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox(5); // 5 is the space between buttons
                    hbox.getChildren().add(editButton);
                    hbox.getChildren().add(downloadButton);
                    setGraphic(hbox);
                }
            }
        });
    }

    // Method to download the file
    public static void downloadMatchingFile(String fileName, String downloadUrl) throws IOException {
        // Check if downloaded file already exists (optional)
        File downloadedFile = new File(System.getProperty("user.home") + "/Downloads/SkillSeekrDD/" + fileName);
        if (downloadedFile.exists()) {
            System.out.println("File " + fileName + " already exists in Downloads folder.");
            return;
        }

        // Download the file
        URL url = new URL(downloadUrl);
        IOUtils.copy(url.openStream(), new FileOutputStream(downloadedFile));
        System.out.println("File " + fileName + " downloaded successfully.");
    }

    // Method to retrieve download URL based on filename (replace with your logic)
    private static String getMatchingDownloadUrl(String fileName) {
        String filePath = new File("src/main/resources/Public/" + fileName).toURI().toString();
        return filePath;
    }


    // Method to load the edit offer page with the targeted offer
    private void loadEditOfferPage(Offer offer) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/Skillseekr/Offer/editOffer.fxml"));
            Parent root = loader.load();
            editOffer controller = loader.getController();
            controller.initData(offer); // Pass the offer data to the controller
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to load data into TableView
    private void loadDataIntoTableView() {
        try {
            serviceOffer = new ServiceOffer(); // Initialize the service
            List<Offer> offers = serviceOffer.show(); // Fetch all offers from the service

            int itemsPerPage = 7; // Number of offers to display per page
            int pageCount = (int) Math.ceil((double) offers.size() / itemsPerPage); // Calculate the number of pages

            pagination.setPageCount(pageCount); // Set the total number of pages

            // Add a listener to handle page changes
            pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> {
                int startIndex = newValue.intValue() * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, offers.size());
                List<Offer> offersPerPage = offers.subList(startIndex, endIndex);
                offerTableView.getItems().setAll(offersPerPage);
            });

            // Initially display the first page of offers
            List<Offer> initialOffers = offers.subList(0, Math.min(itemsPerPage, offers.size()));
            offerTableView.getItems().setAll(initialOffers);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show error message)
        }
    }


    // Method to load a new stage
    @FXML
    public void deleteSelectedOffer() {
        Offer selectedOffer = offerTableView.getSelectionModel().getSelectedItem();
        if (selectedOffer != null) {
            try {
                serviceOffer.delete(selectedOffer);
                offerTableView.getItems().remove(selectedOffer);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Method to refresh the table
    public void refreshTable() {
        try {
            serviceOffer = new ServiceOffer(); // Initialize the service
            List<Offer> offers = serviceOffer.show(); // Fetch all offers from the service

            int itemsPerPage = 7; // Number of offers to display per page
            int pageCount = (int) Math.ceil((double) offers.size() / itemsPerPage); // Calculate the number of pages

            pagination.setPageCount(pageCount); // Set the total number of pages

            int currentPageIndex = pagination.getCurrentPageIndex();
            int startIndex = currentPageIndex * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, offers.size());
            List<Offer> offersPerPage = offers.subList(startIndex, endIndex);
            offerTableView.getItems().setAll(offersPerPage);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., show error message)
        }
    }

    // Method to refresh the table every 3 seconds
    public void refreshTablePeriodically() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); // Sleep for 5 seconds
                    Platform.runLater(this::refreshTable); // Refresh the table on the JavaFX thread
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @FXML
    public void exportTable() {
        // Create a FileChooser dialog
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export Table");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Spreadsheet", "*.xlsx"));

        // Get the current window
        Window window = exportButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(window);

        if (file != null) {
            try {
                // Call a method to export the table data to the selected file
                exportTableData(offerTableView, file); // Pass offerTableView and file as arguments
                // Show success message to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Export Success");
                alert.setHeaderText("Table exported successfully");
                alert.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                // Show error message to the user
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Export Error");
                alert.setHeaderText("An error occurred during table export");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }
    @FXML
    public void exportTableData(TableView<Offer> offerTableView, File file) throws IOException {
        String filePath = file.getAbsolutePath();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Table Data");

        ObservableList<TableColumn<Offer, ?>> columns = offerTableView.getColumns();
        ObservableList<Offer> items = offerTableView.getItems();

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.size(); i++) {
            TableColumn<Offer, ?> column = columns.get(i);
            String columnName = column.getText();
            Cell headerCell = headerRow.createCell(i);
            headerCell.setCellValue(columnName);
        }

        // Create data rows
        for (int row = 0; row < items.size(); row++) {
            Offer offer = items.get(row);
            Row dataRow = sheet.createRow(row + 1);
            for (int col = 0; col < columns.size(); col++) {
                TableColumn<Offer, ?> column = columns.get(col);
                ObservableValue<?> cellObservableValue = column.getCellObservableValue(offer);
                Object cellValue = (cellObservableValue != null) ? cellObservableValue.getValue() : null;
                Cell dataCell = dataRow.createCell(col);
                if (cellValue != null) {
                    dataCell.setCellValue(cellValue.toString());
                }
            }
        }

        // Auto-size columns
        for (int i = 0; i < columns.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to the file
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            workbook.write(fileOutputStream);
        }

        // Close the workbook
        workbook.close();
    }
}