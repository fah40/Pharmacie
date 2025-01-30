package back.TopGestion.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class ModifPrix {
    private int idHistorique;
    private Medicament medicament;
    private double prix;
    private String dateModif;

    // Getters and Setters
    public int getIdHistorique() {
        return idHistorique;
    }

    public void setIdHistorique(int idHistorique) {
        this.idHistorique = idHistorique;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDateModif() {
        return dateModif;
    }

    public void setDateModif(String dateModif) {
        this.dateModif = dateModif;
    }

    // Méthode d'insertion
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO HistoriquePrix (idMedicament, prix, dateModif) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.medicament.getIdMedicament());
            st.setDouble(2, this.prix);
            st.setDate(3, Date.valueOf(this.dateModif));

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to insert record", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    // Méthode pour récupérer un historique de modification de prix par ID
    public static ModifPrix getById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        ModifPrix instance = null;

        try {
            String query = "SELECT * FROM HistoriquePrix WHERE idHistorique = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new ModifPrix();
                instance.setIdHistorique(rs.getInt("idHistorique"));
                instance.setMedicament(Medicament.getById(rs.getInt("idMedicament")));
                instance.setPrix(rs.getDouble("prix"));
                instance.setDateModif(rs.getString("dateModif"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return instance;
    }

    // Méthode pour récupérer toutes les modifications de prix
    public static ModifPrix[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<ModifPrix> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM HistoriquePrix ORDER BY idHistorique ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                ModifPrix item = new ModifPrix();
                item.setIdHistorique(rs.getInt("idHistorique"));
                item.setMedicament(Medicament.getById(rs.getInt("idMedicament")));
                item.setPrix(rs.getDouble("prix"));
                item.setDateModif(rs.getString("dateModif"));

                items.add(item);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return items.toArray(new ModifPrix[0]);
    }

    // Méthode de suppression par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM HistoriquePrix WHERE idHistorique = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to delete record", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }
}
