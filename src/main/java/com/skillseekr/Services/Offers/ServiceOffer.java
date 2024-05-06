package com.skillseekr.Services.Offers;

import com.skillseekr.Services.IServices;
import com.skillseekr.Utils.MyJDBC;
import com.skillseekr.Models.Offers.Offer;
import com.skillseekr.Models.Offers.Motive;
import com.skillseekr.Models.Offers.Type;
import com.skillseekr.Models.Offers.Location;
import com.skillseekr.Models.Offers.Status;
import com.skillseekr.Models.Offers.Skill;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;

public class ServiceOffer implements IServices<Offer> {

    public Connection connection;
    public Statement statement;


    public ServiceOffer() {
        connection = MyJDBC.getInstance().getConnection();
    }

    @Override
    public void add(Offer offer) throws SQLException {
        String query = "INSERT INTO offer (title, description, author, created_at, motive, type, location, status, file_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement insertOfferStatement = null;
        try {
            insertOfferStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insertOfferStatement.setString(1, offer.getTitle());
            insertOfferStatement.setString(2, offer.getDescription());
            insertOfferStatement.setString(3, offer.getAuthor());
            insertOfferStatement.setDate(4, new java.sql.Date(offer.getCreated_at().getTime()));
            insertOfferStatement.setString(5, offer.getMotive().toString());
            insertOfferStatement.setString(6, offer.getType().toString());
            insertOfferStatement.setString(7, offer.getLocation().toString());
            insertOfferStatement.setString(8, offer.getStatus().toString());
            insertOfferStatement.setString(9, offer.getFile_name());

            insertOfferStatement.executeUpdate();

            try (ResultSet generatedKeys = insertOfferStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int offerId = generatedKeys.getInt(1);
                    insertOfferSkills(offerId, offer.getSkills());
                }
            }
        } finally {
            // Close PreparedStatement
            if (insertOfferStatement != null) {
                insertOfferStatement.close();
            }
        }
    }


    private void insertOfferSkills(int id, List<Skill> skills) throws SQLException {
        PreparedStatement preparedStatement = null; // Declare PreparedStatement outside try block
        if (skills == null) {
            return; // Skip insertion if skills is null
        }
        String query = "INSERT INTO offer_skills (id, skill) VALUES (?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            for (Skill skill : skills) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, skill.getSkill());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } finally {
            // Close the PreparedStatement
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }



    @Override
    public void update(Offer offer) throws SQLException {
        String query = "UPDATE offer SET title=?, description=?, author=?, created_at=?, motive=?, type=?, location=?, status=?, file_name=? WHERE id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, offer.getTitle());
            preparedStatement.setString(2, offer.getDescription());
            preparedStatement.setString(3, offer.getAuthor());
            preparedStatement.setDate(4, new java.sql.Date(offer.getCreated_at().getTime()));
            preparedStatement.setString(5, offer.getMotive().toString());
            preparedStatement.setString(6, offer.getType().toString());
            preparedStatement.setString(7, offer.getLocation().toString());
            preparedStatement.setString(8, offer.getStatus().toString());
            preparedStatement.setString(9, offer.getFile_name());
            preparedStatement.setInt(10, offer.getId());

            preparedStatement.executeUpdate();

            // Update skills
            updateOfferSkills(offer.getId(), offer.getSkills());
        }
    }


    private void updateOfferSkills(int id, List<Skill> skills) throws SQLException {
        String deleteQuery = "DELETE FROM offer_skills WHERE id = ?";
        PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();

        insertOfferSkills(id, skills);
    }

    @Override
    public void delete(Offer offer) throws SQLException {
        String deleteOfferSkillsQuery = "DELETE FROM offer_skills WHERE id = ?";
        try (PreparedStatement deleteOfferSkillsStatement = connection.prepareStatement(deleteOfferSkillsQuery)) {
            deleteOfferSkillsStatement.setInt(1, offer.getId());
            deleteOfferSkillsStatement.executeUpdate();
        }

        String deleteOfferQuery = "DELETE FROM offer WHERE id = ?";
        try (PreparedStatement deleteOfferStatement = connection.prepareStatement(deleteOfferQuery)) {
            deleteOfferStatement.setInt(1, offer.getId());
            deleteOfferStatement.executeUpdate();
        }
    }
    @Override
    public List<Offer> show() throws SQLException {
        List<Offer> offers = new ArrayList<>();
        String query = "SELECT * FROM offer";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setTitle(resultSet.getString("title"));
                offer.setDescription(resultSet.getString("description"));
                offer.setAuthor(resultSet.getString("author"));
                offer.setCreated_at(resultSet.getDate("created_at"));
                offer.setMotive(Motive.valueOf(resultSet.getString("motive")));
                offer.setType(Type.valueOf(resultSet.getString("type")));
                offer.setLocation(Location.valueOf(resultSet.getString("location")));
                offer.setStatus(Status.valueOf(resultSet.getString("status")));
                offer.setFile_name(resultSet.getString("file_name"));

                // Retrieve skills for the current offer
                List<Skill> skills = retrieveSkills(offer.getId());
                offer.setSkills(skills);

                offers.add(offer);
            }
        } finally {
            // Close the Statement
            if (statement != null) {
                statement.close();
            }
        }
        return offers;
    }

    @Override
    public void addReclamation(Offer offer, int userId) throws SQLException {

    }

    //add show one Offer for the cards

    // Method to retrieve skills associated with an offer from the database
    private List<Skill> retrieveSkills(int id) throws SQLException {
        List<Skill> skills = new ArrayList<>();
        String query = "SELECT skill FROM offer_skills WHERE id = ?";
        PreparedStatement preparedStatement = null; // Declare outside try block

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String skillName = resultSet.getString("skill");
                    Skill skill = new Skill(skillName);
                    skills.add(skill);
                }
            }
        } finally {
            // Close the PreparedStatement
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            // The try-with-resources block will automatically close the ResultSet
        }
        return skills;
    }
    public List<Skill> getAllSkills() throws SQLException {
        List<Skill> skills = new ArrayList<>();
        String query = "SELECT skill FROM skill;"; // Assuming the table name for skills is "skills"
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                String skillName = resultSet.getString("skill"); // Replace "skill_name" with the actual column name for skills
                Skill skill = new Skill(skillName);
                skills.add(skill);
            }
        }

        return skills;
    }

    public List<Offer> getPublishedOffers() throws SQLException {
        List<Offer> publishedOffers = new ArrayList<>();
        String query = "SELECT * FROM offer WHERE status = 'Published'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setTitle(resultSet.getString("title"));
                offer.setDescription(resultSet.getString("description"));
                offer.setAuthor(resultSet.getString("author"));
                offer.setCreated_at(resultSet.getDate("created_at"));
                offer.setMotive(Motive.valueOf(resultSet.getString("motive")));
                offer.setType(Type.valueOf(resultSet.getString("type")));
                offer.setLocation(Location.valueOf(resultSet.getString("location")));
                offer.setStatus(Status.valueOf(resultSet.getString("status")));
                offer.setFile_name(resultSet.getString("file_name"));

                // Retrieve skills for the current offer
                List<Skill> skills = retrieveSkills(offer.getId());
                offer.setSkills(skills);

                publishedOffers.add(offer);
            }
        } finally {
            // Close the Statement
            if (statement != null) {
                statement.close();
            }
        }
        return publishedOffers;
    }

    public List<Offer> getPublishedInternshipOffers() throws SQLException {
        List<Offer> publishedInternshipOffers = new ArrayList<>();
        String query = "SELECT * FROM offer WHERE status = 'Published' AND type = 'Internship'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setTitle(resultSet.getString("title"));
                offer.setDescription(resultSet.getString("description"));
                offer.setAuthor(resultSet.getString("author"));
                offer.setCreated_at(resultSet.getDate("created_at"));
                offer.setMotive(Motive.valueOf(resultSet.getString("motive")));
                offer.setType(Type.valueOf(resultSet.getString("type")));
                offer.setLocation(Location.valueOf(resultSet.getString("location")));
                offer.setStatus(Status.valueOf(resultSet.getString("status")));
                offer.setFile_name(resultSet.getString("file_name"));

                // Retrieve skills for the current offer
                List<Skill> skills = retrieveSkills(offer.getId());
                offer.setSkills(skills);

                publishedInternshipOffers.add(offer);
            }
        } finally {
            // Close the Statement
            if (statement != null) {
                statement.close();
            }
        }
        return publishedInternshipOffers;
    }

    public List<Offer> getPublishedMissionOffers() throws SQLException {
        List<Offer> publishedMissionOffers = new ArrayList<>();
        String query = "SELECT * FROM offer WHERE status = 'Published' AND type = 'Mission'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Offer offer = new Offer();
                offer.setId(resultSet.getInt("id"));
                offer.setTitle(resultSet.getString("title"));
                offer.setDescription(resultSet.getString("description"));
                offer.setAuthor(resultSet.getString("author"));
                offer.setCreated_at(resultSet.getDate("created_at"));
                offer.setMotive(Motive.valueOf(resultSet.getString("motive")));
                offer.setType(Type.valueOf(resultSet.getString("type")));
                offer.setLocation(Location.valueOf(resultSet.getString("location")));
                offer.setStatus(Status.valueOf(resultSet.getString("status")));
                offer.setFile_name(resultSet.getString("file_name"));

                // Retrieve skills for the current offer
                List<Skill> skills = retrieveSkills(offer.getId());
                offer.setSkills(skills);

                publishedMissionOffers.add(offer);
            }
        } finally {
            // Close the Statement
            if (statement != null) {
                statement.close();
            }
        }
        return publishedMissionOffers;
    }


}