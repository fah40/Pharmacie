package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Achatmatiere {
    private int idAchat;
    private int idmatiere;
    private double quantiteAchetee;
    private String dateAchat;
    private double prixUnitaire;

    // Getters et Setters
    public int getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(int idAchat) {
        this.idAchat = idAchat;
    }

    public int getIdmatiere() {
        return idmatiere;
    }

    public void setIdmatiere(int idmatiere) {
        this.idmatiere = idmatiere;
    }

    public double getQuantiteAchetee() {
        return quantiteAchetee;
    }

    public void setQuantiteAchetee(double quantiteAchetee) {
        this.quantiteAchetee = quantiteAchetee;
    }

    public String getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(String dateAchat) {
        this.dateAchat = dateAchat;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    // Méthode pour insérer un achat
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO Achatmatiere (idmatiere, quantiteAchetee, prixUnitaire) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.idmatiere);
            st.setDouble(2, this.quantiteAchetee);
            st.setDouble(3, this.prixUnitaire);

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

    // Méthode pour récupérer un achat par ID
    public static Achatmatiere getById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Achatmatiere achat = null;

        try {
            String query = "SELECT * FROM Achatmatiere WHERE idAchat = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                achat = new Achatmatiere();
                achat.setIdAchat(rs.getInt("idAchat"));
                achat.setIdmatiere(rs.getInt("idmatiere"));
                achat.setQuantiteAchetee(rs.getDouble("quantiteAchetee"));
                achat.setDateAchat(rs.getString("dateAchat"));
                achat.setPrixUnitaire(rs.getDouble("prixUnitaire"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return achat;
    }

    // Méthode pour récupérer tous les achats
    public static List<Achatmatiere> getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Achatmatiere> achats = new ArrayList<>();

        try {
            String query = "SELECT * FROM Achatmatiere ORDER BY idAchat ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Achatmatiere achat = new Achatmatiere();
                achat.setIdAchat(rs.getInt("idAchat"));
                achat.setIdmatiere(rs.getInt("idmatiere"));
                achat.setQuantiteAchetee(rs.getDouble("quantiteAchetee"));
                achat.setDateAchat(rs.getString("dateAchat"));
                achat.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                achats.add(achat);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return achats;
    }

    // Méthode pour mettre à jour un achat
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE Achatmatiere SET idmatiere = ?, quantiteAchetee = ?, prixUnitaire = ? WHERE idAchat = ?";
            st = con.prepareStatement(query);
            st.setInt(1, this.idmatiere);
            st.setDouble(2, this.quantiteAchetee);
            st.setDouble(3, this.prixUnitaire);
            st.setInt(4, this.idAchat);

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

    // Méthode pour supprimer un achat par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Achatmatiere WHERE idAchat = ?";
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
            if (con != null && !con.isClosed()) con.close();
        }
    }
}
