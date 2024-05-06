package com.skillseekr.Services.Hire;
import com.skillseekr.Services.IServices;
import com.skillseekr.Utils.MyJDBC;
import com.skillseekr.Models.Hire.Entretient;
import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


public class ServiceEntretient implements IServices<Entretient> {
    public Connection connection;
    public Statement statement;


    public ServiceEntretient() {
        connection = MyJDBC.getInstance().getConnection();
    }

    @Override
    public void add(Entretient entretient) throws SQLException {
        String query = "INSERT INTO entretient (date, type, resultat, id_rec_id) VALUES (?, ?, ?, ?)";
        PreparedStatement insertEntretientStatement = null;
        insertEntretientStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        insertEntretientStatement.setDate(1, new Date(entretient.getDate().getTime()));
        insertEntretientStatement.setString(2, entretient.getType());
        insertEntretientStatement.setString(3, entretient.getResultat());
        insertEntretientStatement.setInt(4, entretient.getId_rec_id());
        insertEntretientStatement.executeUpdate();

    }

    @Override
    public void update(Entretient entretient) throws SQLException {
        String query = "UPDATE offer SET date=?, type=?, resultat=?, id_rec_id=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(entretient.getDate().getTime()));
            preparedStatement.setString(2, entretient.getType());
            preparedStatement.setString(3, entretient.getResultat());
            preparedStatement.setInt(4, entretient.getId_rec_id());
            preparedStatement.executeUpdate();

        }

    }

    @Override
    public void delete(Entretient entretient) throws SQLException {
        String query = "DELETE FROM offer WHERE id = ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, entretient.getId());
            preparedStatement.executeUpdate();
        } finally {
            // Close the PreparedStatement
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public List<Entretient> show() throws SQLException {
        List<Entretient> entretients = new ArrayList<>();
        String query = "SELECT * FROM entretient";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Entretient entretient = new Entretient();
                entretient.setId(resultSet.getInt("id"));
                entretient.setDate((resultSet.getDate("date")));
                entretient.setType(resultSet.getString("type"));
                entretient.setResultat(resultSet.getString("resultat"));
                entretient.setId_rec_id(resultSet.getInt("id_rec_id"));

            }
        }
        return entretients;
    }

    @Override
    public void addReclamation(Entretient entretient, int userId) throws SQLException {

    }
}

