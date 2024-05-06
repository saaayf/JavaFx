package com.skillseekr.Services.projets;

import com.skillseekr.Models.projet.Proposition;
import com.skillseekr.Utils.MyJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProposition implements IService<Proposition>{

    private Connection connection;

    public ServiceProposition() {
        connection = MyJDBC.getInstance().getConnection();
    }


    @Override
    public void add(Proposition proposition) throws SQLException {
        String sql = "insert into proposition (description,prop_budget,prop_delai,id_projet_id,user_id) VALUES (?,?,?,?,?)";
        System.out.println(sql);
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, proposition.getDescription());
        pst.setFloat(2, proposition.getPropBudget());
        pst.setDate(3, Date.valueOf(proposition.getPropDelai()));
        pst.setInt(4, proposition.getProjet().getId());
        pst.setInt(5, proposition.getIdUser());

        pst.executeUpdate();
        System.out.println("Proposition added");

    }

    @Override
    public void update(Proposition proposition) throws SQLException {

        String sql = "update proposition set description = ?,  prop_budget = ? , prop_delai = ? ,id_projet_id = ? ,  user_id = ?  where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, proposition.getDescription());
        preparedStatement.setFloat(2, proposition.getPropBudget());
        preparedStatement.setDate(3, Date.valueOf(proposition.getPropDelai()));
        preparedStatement.setInt(4, proposition.getProjet().getId());
        preparedStatement.setInt(5, proposition.getIdUser());
        preparedStatement.setInt(6, proposition.getId());

        preparedStatement.executeUpdate();
        System.out.println("proposition with id = " + proposition.getId() + " updated");

    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "delete from proposition where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        System.out.println("proposition with id = " + id + " deleted");

    }

    @Override
    public List<Proposition> getAll() throws SQLException {
        String sql = "select * from proposition";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Proposition> propositions = new ArrayList<>();
        while (rs.next()) {
            Proposition proposition = new Proposition();
               proposition.setId(rs.getInt("id"));
                proposition.setDescription(rs.getString("description"));
                proposition.setPropBudget(rs.getFloat("prop_budget"));
                proposition.setPropDelai(rs.getDate("prop_delai").toLocalDate());
                proposition.setIdUser(rs.getInt("user_id"));
                proposition.setProjet(new ServiceProjet().getById(rs.getInt("id_projet_id")));


            propositions.add(proposition);
        }
        return propositions;
    }

    @Override
    public Proposition getById(int id) throws SQLException {
        String sql = "SELECT * FROM proposition WHERE `id` = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        Proposition proposition = new Proposition();
        if (rs.next()) {
               proposition.setId(rs.getInt("id"));
                proposition.setDescription(rs.getString("description"));
                proposition.setPropBudget(rs.getFloat("prop_budget"));
                proposition.setPropDelai(rs.getDate("prop_delai").toLocalDate());
                proposition.setIdUser(rs.getInt("user_id"));
                proposition.setProjet(new ServiceProjet().getById(rs.getInt("id_projet_id")));

            System.out.println("proposition with id = " + id + " found");
            return proposition;

        }
        System.out.println("proposition with id = " + id + " not found ! ");
        return null;
    }

    public List<Proposition> getByIdProjet(int id) throws SQLException {
        String sql = "select * from proposition where id_projet_id = " + id ;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Proposition> propositions = new ArrayList<>();
        while (rs.next()) {
            Proposition proposition = new Proposition();
            proposition.setId(rs.getInt("id"));
            proposition.setDescription(rs.getString("description"));
            proposition.setPropBudget(rs.getFloat("prop_budget"));
            proposition.setPropDelai(rs.getDate("prop_delai").toLocalDate());
            proposition.setIdUser(rs.getInt("user_id"));
            proposition.setProjet(new ServiceProjet().getById(rs.getInt("id_projet_id")));
            propositions.add(proposition);
        }
        return propositions;
    }
}
