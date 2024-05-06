package com.skillseekr.Services.Claims;

import com.skillseekr.Models.Claims.Reclamation;
import com.skillseekr.Services.IServices;
import com.skillseekr.Utils.MyJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReclamation implements IServices<Reclamation> {
    private Connection connection;

    public ServiceReclamation() {
        connection = MyJDBC.getInstance().getConnection();
    }

    public void addReclamation(Reclamation reclamation, int userId) throws SQLException {
        // Set the user id to the id_user attribute in the Reclamation model
        reclamation.setUser_id(userId);

        // Now, insert the Reclamation into the database
        String insertQuery = "INSERT INTO reclamation (user_id, title, content) VALUES (?, ?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, reclamation.getUser_id());
            insertStatement.setString(2, reclamation.getTitle());
            insertStatement.setString(3, reclamation.getContent());
            insertStatement.executeUpdate();


        }
    }


    @Override
    public void add(Reclamation reclamation) throws SQLException {

    }

    @Override
    public void update(Reclamation reclamation) throws SQLException {
        // Check if idrec is not null before using it
        if (reclamation.getIdrec() != null) {
            String query = "UPDATE reclamation SET user_id=?, title=?, content=? WHERE idrec=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, reclamation.getUser_id());
                preparedStatement.setString(2, reclamation.getTitle());
                preparedStatement.setString(3, reclamation.getContent());
                preparedStatement.setInt(4, reclamation.getIdrec());
                preparedStatement.executeUpdate();
            }
        } else {
            // Handle the case where idrec is null
            System.out.println("Cannot update reclamation: idrec is null");
        }
    }


    @Override
    public void delete(Reclamation reclamation) throws SQLException {
        String query = "DELETE FROM reclamation WHERE idrec = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reclamation.getIdrec());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Reclamation> show() throws SQLException {
        List<Reclamation> reclamations = new ArrayList<>();
        String query = "SELECT * FROM reclamation";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Reclamation reclamation = new Reclamation();
                reclamation.setIdrec(resultSet.getInt("idrec"));
                reclamation.setUser_id(resultSet.getInt("user_id"));
                reclamation.setTitle(resultSet.getString("title"));
                reclamation.setContent(resultSet.getString("content"));
                reclamations.add(reclamation);
            }
        }
        return reclamations;
    }
}
