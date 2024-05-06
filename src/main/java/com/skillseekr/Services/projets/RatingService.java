package com.skillseekr.Services.projets;


import com.skillseekr.Models.projet.Rating;
import com.skillseekr.Utils.MyJDBC;

import java.sql.*;

public class RatingService {

    private Connection cnx;

    public RatingService()
    {
        this.cnx = MyJDBC.getInstance().getConnection();
    }

    public void create(Rating rating) throws SQLException {
        String req ="INSERT INTO `rating`(`id_user`, `id_projet`, `value`) VALUES (? , ?, ?)";
        PreparedStatement st= cnx.prepareStatement(req);
        st.setInt(1,rating.getIdUser());
        st.setInt(2,rating.getIdProjet());
        st.setString(3,rating.getValue().toString());
        st.executeUpdate();
        System.out.println("rating ajouté !");
    }

    public void update(Rating rating) throws SQLException{
        String req ="UPDATE `rating` SET `value`=? WHERE `id_user`=? AND `id_projet`=?";
        PreparedStatement st= cnx.prepareStatement(req);
        st.setString(1,rating.getValue().toString());
        st.setInt(2,rating.getIdUser());
        st.setInt(3,rating.getIdProjet());
        st.executeUpdate();
        System.out.println("rating updated");
    }

    public Double GetRatingParProjet(int id_projet , int id_user) throws SQLException{
        Double tot = 0.0 ;
        String req ="SELECT * FROM `rating` where id_projet ="+id_projet +" AND id_user ="+id_user;
        Statement st= cnx.createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()){
            Double one = Double.parseDouble(rs.getString("value"));
            tot += one ;
        }
        System.out.println("totale = " + tot);
        return tot;
    }






}
