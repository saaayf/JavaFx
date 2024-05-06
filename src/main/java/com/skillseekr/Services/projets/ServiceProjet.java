package com.skillseekr.Services.projets;

import com.skillseekr.Models.projet.Projet;
import com.skillseekr.Utils.MyJDBC;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProjet implements IService<Projet>{
    private Connection connection;

    public ServiceProjet() {
        connection = MyJDBC.getInstance().getConnection();
    }

    @Override
    public void add(Projet projet) throws SQLException {
        String sql = "insert into projet (titre,competences,budget,date_deb,date_fin,proprietaire,user_id) VALUES (?,?,?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, projet.getTitre());
        pst.setString(2, projet.getCompetences());
        pst.setFloat(3, projet.getBudget());
        pst.setDate(4, Date.valueOf(projet.getDate_deb()));
        pst.setDate(5, Date.valueOf(projet.getDate_fin()));
        pst.setString(6, projet.getProprietaire());
        pst.setInt(7, projet.getIdUser());
        pst.executeUpdate();
        System.out.println("Projet added");

    }

    @Override
    public void update(Projet projet) throws SQLException {
        String sql = "update projet set titre = ?,  competences = ? , budget = ? , date_deb = ? , date_fin  = ? , proprietaire =  ?  , user_id = ?    where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, projet.getTitre());
    preparedStatement.setString(2, projet.getCompetences());
    preparedStatement.setFloat(3, projet.getBudget());
    preparedStatement.setDate(4, Date.valueOf(projet.getDate_deb()));
    preparedStatement.setDate(5, Date.valueOf(projet.getDate_fin()));
    preparedStatement.setString(6, projet.getProprietaire());
    preparedStatement.setInt(7, projet.getIdUser());
    preparedStatement.setInt(8, projet.getId());

        preparedStatement.executeUpdate();
        System.out.println("projet with id = " + projet.getId() + " updated");


    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from projet where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("projet with id = " + id + " deleted");

    }

    @Override
    public List<Projet> getAll() throws SQLException {
        String sql = "select * from projet";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Projet> projets = new ArrayList<>();
        while (rs.next()) {
            Projet p = new Projet();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setCompetences(rs.getString("competences"));
            p.setBudget(rs.getFloat("budget"));
            p.setDate_deb(rs.getDate("date_deb").toLocalDate());
            p.setDate_fin(rs.getDate("date_fin").toLocalDate());
            p.setProprietaire(rs.getString("proprietaire"));
            p.setIdUser(rs.getInt("user_id"));
            projets.add(p);
        }
        return projets;
    }

    @Override
    public Projet getById(int id) throws SQLException {
        String sql = "SELECT * FROM projet WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Projet projet = new Projet();
        if (rs.next()) {
              projet.setId(rs.getInt("id"));
                projet.setTitre(rs.getString("titre"));
                projet.setCompetences(rs.getString("competences"));
                projet.setBudget(rs.getFloat("budget"));
                projet.setDate_deb(rs.getDate("date_deb").toLocalDate());
                projet.setDate_fin(rs.getDate("date_fin").toLocalDate());
                projet.setProprietaire(rs.getString("proprietaire"));
                projet.setIdUser(rs.getInt("user_id"));
            System.out.println("projet with id = " + id + " found");
            return projet;

        }
        System.out.println("projet with id = " + id + " not found ! ");
        return null;
    }

    public List<Projet> getProjetsByIdUser(int id) throws SQLException {
        String sql = "select * from projet where user_id = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        List<Projet> projets = new ArrayList<>();
        while (rs.next()) {
            Projet p = new Projet();
            p.setId(rs.getInt("id"));
            p.setTitre(rs.getString("titre"));
            p.setCompetences(rs.getString("competences"));
            p.setBudget(rs.getFloat("budget"));
            p.setDate_deb(rs.getDate("date_deb").toLocalDate());
            p.setDate_fin(rs.getDate("date_fin").toLocalDate());
            p.setProprietaire(rs.getString("proprietaire"));
            p.setIdUser(rs.getInt("user_id"));
            projets.add(p);
        }
        return projets;
    }
}
