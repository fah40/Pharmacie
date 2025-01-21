package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Production {
    private int idProduction;
    private int idMedicament;
    private Laboratoire laboratoire;
    private int quantiteProduite;
    private double prix;

    private String dateProduction;

    // Getters and Setters

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getMaladie() throws Exception {
        Medicament med = Medicament.getById(this.idMedicament);
        return med.getNom();
    }

    public int getIdProduction() {
        return idProduction;
    }

    public void setIdProduction(int idProduction) {
        this.idProduction = idProduction;
    }

    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public Laboratoire getLaboratoire() {
        return laboratoire;
    }

    public void setLaboratoire(Laboratoire laboratoire) {
        this.laboratoire = laboratoire;
    }

    public int getQuantiteProduite() {
        return quantiteProduite;
    }

    public void setQuantiteProduite(int quantiteProduite) {
        this.quantiteProduite = quantiteProduite;
    }

    public String getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(String dateProduction) {
        this.dateProduction = dateProduction;
    }

    // Retrieve the latest production record
    public static Production getLast() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Production instance = null;

        try {
            String query = "SELECT * FROM Production ORDER BY idProduction DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Production();
                instance.setIdProduction(rs.getInt("idProduction"));
                instance.setIdMedicament(rs.getInt("idMedicament"));
                instance.setQuantiteProduite(rs.getInt("quantiteProduite"));
                instance.setDateProduction(rs.getString("dateProduction"));
                instance.setPrix(rs.getDouble("prix"));
                instance.setLaboratoire(Laboratoire.getById(rs.getInt("idLaboratoire")));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null)
                con.close();
        }

        return instance;
    }

    // Retrieve all production records
    public static Production[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Production> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Production ORDER BY idProduction ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Production item = new Production();
                item.setIdProduction(rs.getInt("idProduction"));
                item.setIdMedicament(rs.getInt("idMedicament"));
                item.setQuantiteProduite(rs.getInt("quantiteProduite"));
                item.setDateProduction(rs.getString("dateProduction"));
                item.setPrix(rs.getDouble("prix"));
                item.setLaboratoire(Laboratoire.getById(rs.getInt("idLaboratoire")));
                items.add(item);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null)
                con.close();
        }

        return items.toArray(new Production[0]);
    }

    // Insert a new production record
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO Production (idMedicament, quantiteProduite, idLaboratoire, prix) VALUES (?, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.idMedicament);
            st.setInt(2, this.quantiteProduite);
            st.setInt(3, this.laboratoire.getId());
            st.setDouble(4, this.prix);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to insert production record", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null)
                con.close();
        }
    }

    // Delete a production record by ID
    public static void deleteById(int idProduction) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Production WHERE idProduction = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idProduction);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to delete production record", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null)
                con.close();
        }
    }
}
