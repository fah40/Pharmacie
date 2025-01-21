package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Consommationmatiere {
    int idConsommation;
    int idProduction;
    int idmatiere;
    double quantiteConsommee;

    // Getters et Setters
    public int getIdConsommation() {
        return idConsommation;
    }

    public void setIdConsommation(int idConsommation) {
        this.idConsommation = idConsommation;
    }

    public int getIdProduction() {
        return idProduction;
    }

    public void setIdProduction(int idProduction) {
        this.idProduction = idProduction;
    }

    public int getIdmatiere() {
        return idmatiere;
    }

    public void setIdmatiere(int idmatiere) {
        this.idmatiere = idmatiere;
    }

    public double getQuantiteConsommee() {
        return quantiteConsommee;
    }

    public void setQuantiteConsommee(double quantiteConsommee) {
        this.quantiteConsommee = quantiteConsommee;
    }

    // Méthode pour obtenir toutes les consommations de matières
    public static Consommationmatiere[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Consommationmatiere> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Consommationmatiere ORDER BY idConsommation ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Consommationmatiere item = new Consommationmatiere();
                item.setIdConsommation(rs.getInt("idConsommation"));
                item.setIdProduction(rs.getInt("idProduction"));
                item.setIdmatiere(rs.getInt("idmatiere"));
                item.setQuantiteConsommee(rs.getDouble("quantiteConsommee"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Consommationmatiere[0]);
    }

    // Méthode pour insérer une nouvelle consommation de matière
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO Consommationmatiere (idProduction, idmatiere, quantiteConsommee) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.idProduction);
            st.setInt(2, this.idmatiere);
            st.setDouble(3, this.quantiteConsommee);
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to insert record", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }
    }

    // Méthode pour mettre à jour une consommation de matière existante
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "UPDATE Consommationmatiere SET idProduction = ?, idmatiere = ?, quantiteConsommee = ? WHERE idConsommation = ?";
            st = con.prepareStatement(query);
            st.setInt(1, this.idProduction);
            st.setInt(2, this.idmatiere);
            st.setDouble(3, this.quantiteConsommee);
            st.setInt(4, this.idConsommation);
            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to update record", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }
    }

    // Méthode pour supprimer une consommation de matière par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Consommationmatiere WHERE idConsommation = ?";
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
