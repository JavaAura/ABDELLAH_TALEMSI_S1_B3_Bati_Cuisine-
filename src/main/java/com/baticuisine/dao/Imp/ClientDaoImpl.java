package main.java.com.baticuisine.dao.Imp;

import main.java.com.baticuisine.model.Client;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl {
    private Connection connection;

    public ClientDaoImpl(Connection connection) {
        this.connection = connection;
    }


    public void add(Client client) {
        String sql = "insert into clients(nom,prenom,adresse,telephone,est_professionnel) values(?,?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,client.getNom());
            ps.setString(2,client.getPrenom());
            ps.setString(3,client.getAdresse());
            ps.setString(4,client.getTelephone());
            ps.setBoolean(5,client.isEstProfessionnel());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Client client,int id) {
        String sql = "UPDATE clients SET nom = ?, adresse = ?, telephone = ?, est_professionnel = ? WHERE id_client = ?";
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1,client.getNom());
            ps.setString(2,client.getAdresse());
            ps.setString(3,client.getTelephone());
            ps.setBoolean(4,client.isEstProfessionnel());
            ps.setInt(5,id);
            ps.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id) {
        String sql = "delete from clients where id_client = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Client rechercherParId(int idClient) throws SQLException {
        Client client = null;
        String sql = "SELECT * FROM clients WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getString("nom"),rs.getString("prenom"), rs.getString("adresse"), rs.getString("telephone"));
                client.setEstProfessionnel(rs.getBoolean("est_professionnel"));

            }
        }
        return null;
    }
    public List<Client> rechercherTous() {
        String sql = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Client client = new Client(rs.getString("nom"), rs.getString("prenom"), rs.getString("adresse"), rs.getString("telephone"));
                client.setEstProfessionnel(rs.getBoolean("est_professionnel"));
                client.setId_client(rs.getInt("id_client"));
                clients.add(client);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return clients;
    }

    public Client rechercherParNom(String nomClient) {
        String sql = "SELECT * FROM clients WHERE nom = ?";
        Client client = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, nomClient);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                client = new Client(rs.getString("nom"),rs.getString("prenom"),rs.getString("adresse"),rs.getString("telephone"),rs.getBoolean("est_professionnel"));
                client.setId_client(rs.getInt("id_client"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return client;
    }
}
