package com.skillseekr;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.time.ZonedDateTime;

public class CalendarController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Text year;

    @FXML
    private Text month;

    @FXML
    private FlowPane calendar;

    private ZonedDateTime dateFocus;
    private ZonedDateTime today;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
        addButtonsToCalendar();
    }

    public void addButtonsToCalendar() {
        FlowPane buttonPane = new FlowPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.getChildren().addAll(backButton, month, year, forwardButton);

        calendar.getChildren().add(buttonPane);
    }

    public void drawCalendar() {
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(dateFocus.getMonth().toString());

        calendar.getChildren().clear();

        int daysInMonth = dateFocus.toLocalDate().lengthOfMonth();
        int firstDayOfWeek = dateFocus.withDayOfMonth(1).getDayOfWeek().getValue();

        List<CalendarActivity> calendarActivities = getCalendarActivitiesFromDatabase(dateFocus);

        for (int i = 1; i <= daysInMonth; i++) {
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(80, 80);

            Rectangle rectangle = new Rectangle(80, 80, Color.TRANSPARENT);
            rectangle.setStroke(Color.rgb(112, 181, 250));
            rectangle.setStrokeWidth(1);

            Text dateText = new Text(String.valueOf(i));
            stackPane.getChildren().addAll(rectangle, dateText);

            for (CalendarActivity activity : calendarActivities) {
                if (activity.getOfferDate().getDayOfMonth() == i) {
                    Rectangle card = new Rectangle(80, 80, Color.PINK);
                    TextFlow textFlow = new TextFlow(); // Create a TextFlow container for the text
                    textFlow.setMaxWidth(80); // Set the maximum width for truncation
                    textFlow.setTextAlignment(TextAlignment.CENTER); // Center-align the text within the TextFlow

                    Text titleText = new Text(activity.getOfferTitle());
                    titleText.setFill(Color.BLACK);
                    textFlow.getChildren().add(titleText);

                    StackPane.setAlignment(textFlow, Pos.CENTER); // Center-align the TextFlow within the StackPane

                    stackPane.getChildren().addAll(card, textFlow);
                }
                if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == i) {
                    rectangle.setStroke(Color.rgb(53, 134, 245)); // Darker blue stroke for today
                }
            }

            calendar.getChildren().add(stackPane);
        }

        calendar.getChildren().addAll(backButton, month, year, forwardButton);
    }

    private List<CalendarActivity> getCalendarActivitiesFromDatabase(ZonedDateTime zonedDateTime) {
        List<CalendarActivity> calendarActivities = new ArrayList<>();

        // Connect to the database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skillseekr", "root", "")) {
            // Prepare the SQL statement to retrieve offer titles and created_at values for the given year and month
            String sql = "SELECT title, created_at FROM offer WHERE YEAR(created_at) = ? AND MONTH(created_at) = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, zonedDateTime.getYear());
            statement.setInt(2, zonedDateTime.getMonthValue());

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Process the results and create CalendarActivity objects
            while (resultSet.next()) {
                String offerTitle = resultSet.getString("title");
                LocalDateTime offerDate = resultSet.getTimestamp("created_at").toLocalDateTime();

                CalendarActivity activity = new CalendarActivity(offerDate, offerTitle);
                calendarActivities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return calendarActivities;
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        updateYearAndMonth();
        System.out.println("New dateFocus after going back one month: " + dateFocus);
        calendar.getChildren().clear(); // Clear the calendar before redrawing
        drawCalendar(); // Redrawthe calendar
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        updateYearAndMonth();
        System.out.println("New dateFocus after going forward one month: " + dateFocus);
        calendar.getChildren().clear(); // Clear the calendar before redrawing
        drawCalendar(); // Redraw the calendar
    }

    private void updateYearAndMonth() {
        month.setText(dateFocus.getMonth().toString());
        year.setText(String.valueOf(dateFocus.getYear()));
    }
}