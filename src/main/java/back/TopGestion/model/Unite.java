package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Unite {
    private int idUnite;
    private String nom;

    // Getters et Setters
    public int getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(int idUnite) {
        this.idUnite = idUnite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public static Unite getById(int idU) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Unite instance = null;

        try {
            String query = "SELECT * FROM unite WHERE idUnite = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idU);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Unite();
                instance.setIdUnite(rs.getInt("idunite"));
                instance.setNom(rs.getString("nom"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return instance;
    }

    // Méthode pour obtenir toutes les unités
    public static Unite[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Unite> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM unite ORDER BY idunite ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Unite item = new Unite();
                item.setIdUnite(rs.getInt("idunite"));
                item.setNom(rs.getString("nom"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Unite[0]);
    }

    // Méthode pour insérer une nouvelle unité
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO unite (nom) VALUES (?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to insert record", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }

    // Méthode pour mettre à jour une unité existante
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "UPDATE unite SET nom = ? WHERE idunite = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.idUnite);
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

    // Méthode pour supprimer une unité par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM unite WHERE idunite = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw new Exception("Failed to delete record", e);
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }
}
