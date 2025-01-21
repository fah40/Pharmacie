package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Client {
    private int idClient;
    private String nom;
    private String adresse;
    private String telephone;

    // Constructeur par défaut
    public Client() {
    }

    // Constructeur avec tous les champs
    public Client(int idClient, String nom, String adresse, String telephone) {
        this.idClient = idClient;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
    }

    // Getters et Setters
    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
    
        try {
            String query = "INSERT INTO Client (nom, adresse, telephone) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setString(2, this.adresse);
            st.setString(3, this.telephone);
    
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de l'insertion du client", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }
    

    public static Client getById(int idClient) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Client client = null;
    
        try {
            String query = "SELECT * FROM Client WHERE idClient = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idClient);
            rs = st.executeQuery();
    
            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("idClient"));
                client.setNom(rs.getString("nom"));
                client.setAdresse(rs.getString("adresse"));
                client.setTelephone(rs.getString("telephone"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        return client;
    }

    public static Client[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Client> clients = new ArrayList<>();
    
        try {
            String query = "SELECT * FROM Client ORDER BY idClient ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();
    
            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getInt("idClient"));
                client.setNom(rs.getString("nom"));
                client.setAdresse(rs.getString("adresse"));
                client.setTelephone(rs.getString("telephone"));
    
                clients.add(client);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        return clients.toArray(new Client[0]);
    }

    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
    
        try {
            String query = "UPDATE Client SET nom = ?, adresse = ?, telephone = ? WHERE idClient = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setString(2, this.adresse);
            st.setString(3, this.telephone);
            st.setInt(4, this.idClient);
    
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to update record", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }

    public static void deleteById(int idClient) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
    
        try {
            String query = "DELETE FROM Client WHERE idClient = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idClient);
    
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to delete record", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }

    public static Client getLast() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Client client = null;
    
        try {
            String query = "SELECT * FROM Client ORDER BY idClient DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();
    
            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("idClient"));
                client.setNom(rs.getString("nom"));
                client.setAdresse(rs.getString("adresse"));
                client.setTelephone(rs.getString("telephone"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        return client;
    }
    
    
    
    
}
