package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Categorie {
    private int idCategorie;
    private String nom;

    // Getters and Setters
    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Static methods for database operations
    public static Categorie getById(int idCategorie) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Categorie instance = null;

        try {
            String query = "SELECT * FROM Categorie WHERE idCategorie = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idCategorie);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Categorie();
                instance.setIdCategorie(rs.getInt("idCategorie"));
                instance.setNom(rs.getString("nom"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return instance;
    }

    public static Categorie[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Categorie> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Categorie ORDER BY idCategorie ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Categorie item = new Categorie();
                item.setIdCategorie(rs.getInt("idCategorie"));
                item.setNom(rs.getString("nom"));

                items.add(item);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Categorie[0]);
    }

    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO Categorie (nom) VALUES (?)";
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

    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE Categorie SET nom = ? WHERE idCategorie = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.idCategorie);

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

    public static void deleteById(int idCategorie) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Categorie WHERE idCategorie = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idCategorie);

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

    public static void main(String[] args) throws Exception {
        Categorie[] all= Categorie.getAll();

        for (Categorie etat : all) {
            System.out.println(etat.getNom());
        }
        
    }
}
