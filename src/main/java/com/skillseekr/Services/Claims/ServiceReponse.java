package com.skillseekr.Services.Claims;

import com.skillseekr.Models.Claims.Reponse;
import com.skillseekr.Services.IServices;
import com.skillseekr.Utils.MyJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceReponse implements IServices<Reponse> {
    private Connection connection;

    public ServiceReponse() {
        connection = MyJDBC.getInstance().getConnection();
    }

    @Override
    public void add(Reponse reponse) throws SQLException {
        String query = "INSERT INTO reponse (reclamation_id, subject, message, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, reponse.getReclamation_id());
            preparedStatement.setString(2, reponse.getSubject());
            preparedStatement.setString(3, reponse.getMessage());
            preparedStatement.setDate(4, new java.sql.Date(reponse.getCreated_at().getTime()));
            preparedStatement.executeUpdate();

            // Get the generated keys to set the idrep in the Reponse object
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reponse.setIdrep(generatedKeys.getInt(1));
            }
        }
    }


    @Override
    public void update(Reponse reponse) throws SQLException {
        String query = "UPDATE reponse SET reclamation_id=?, subject=?, message=?, created_at=? WHERE idrep=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponse.getReclamation_id());
            preparedStatement.setString(2, reponse.getSubject());
            preparedStatement.setString(3, reponse.getMessage());
            preparedStatement.setDate(4, new java.sql.Date(reponse.getCreated_at().getTime()));
            preparedStatement.setInt(5, reponse.getIdrep());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Reponse reponse) throws SQLException {
        String query = "DELETE FROM reponse WHERE idrep = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, reponse.getIdrep());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Reponse> show() throws SQLException {
        List<Reponse> reponses = new ArrayList<>();
        String query = "SELECT * FROM reponse";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Reponse reponse = new Reponse();
                reponse.setIdrep(resultSet.getInt("idrep"));
                reponse.setReclamation_id(resultSet.getInt("reclamation_id"));
                reponse.setSubject(resultSet.getString("subject"));
                reponse.setMessage(resultSet.getString("message"));
                reponse.setCreated_at(resultSet.getDate("created_at"));
                reponses.add(reponse);
            }
        }
        return reponses;
    }

    @Override
    public void addReclamation(Reponse reponse, int userId) throws SQLException {

    }
}
