package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Matiere {
    private int idMatiere;
    private String nom;
    private double quantiteStock;
    private int idUnite;

    // Getters and setters
    public int getIdMatiere() {
        return idMatiere;
    }

    public void setIdMatiere(int idMatiere) {
        this.idMatiere = idMatiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(double quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getUnite() throws Exception {
        Unite u = Unite.getById(this.idUnite);
        return u.getNom();
    }

    public int getIdUnite() {
        return idUnite;
    }

    public void setIdUnite(int idUnite) {
        this.idUnite = idUnite;
    }

    // CRUD Methods

    public static Matiere getById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Matiere matiere = null;

        try {
            String query = "SELECT * FROM matiere WHERE idmatiere = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                matiere = new Matiere();
                matiere.setIdMatiere(rs.getInt("idmatiere"));
                matiere.setNom(rs.getString("nom"));
                matiere.setQuantiteStock(rs.getDouble("quantiteStock"));
                matiere.setIdUnite(rs.getInt("idunite"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return matiere;
    }

    public static Matiere[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Matiere> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM matiere ORDER BY idmatiere ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Matiere matiere = new Matiere();
                matiere.setIdMatiere(rs.getInt("idmatiere"));
                matiere.setNom(rs.getString("nom"));
                matiere.setQuantiteStock(rs.getDouble("quantiteStock"));
                matiere.setIdUnite(rs.getInt("idunite"));
                list.add(matiere);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null) con.close();
        }

        return list.toArray(new Matiere[0]);
    }

    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO matiere (nom, idunite) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.idUnite);
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }

    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE matiere SET nom = ?, quantiteStock = ?, idunite = ? WHERE idmatiere = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setDouble(2, this.quantiteStock);
            st.setInt(3, this.idUnite);
            st.setInt(4, this.idMatiere);
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }

    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM matiere WHERE idmatiere = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) con.rollback();
            throw e;
        } finally {
            if (st != null) st.close();
            if (con != null) con.close();
        }
    }
}
