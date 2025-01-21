package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Maladie {
    int idMaladie;
    String nom;
    String description;

    // Getters et Setters
    public int getIdMaladie() {
        return idMaladie;
    }

    public void setIdMaladie(int idMaladie) {
        this.idMaladie = idMaladie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Méthode pour obtenir la dernière maladie ajoutée
    public static Maladie getLast() throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Maladie instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM Maladie ORDER BY idMaladie DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Maladie();
                instance.setIdMaladie(rs.getInt("idMaladie"));
                instance.setNom(rs.getString("nom"));
                instance.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return instance;
    }

    // Méthode pour obtenir une maladie par son ID
    public static Maladie getById(int id) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Maladie instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM Maladie WHERE idMaladie = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Maladie();
                instance.setIdMaladie(rs.getInt("idMaladie"));
                instance.setNom(rs.getString("nom"));
                instance.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }

        return instance;
    }

    // Méthode pour obtenir toutes les maladies
    public static Maladie[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Maladie> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Maladie ORDER BY idMaladie ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Maladie item = new Maladie();
                item.setIdMaladie(rs.getInt("idMaladie"));
                item.setNom(rs.getString("nom"));
                item.setDescription(rs.getString("description"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Maladie[0]);
    }

    // Méthode pour insérer une nouvelle maladie
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "INSERT INTO maladie (nom, description) VALUES (?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setString(2, this.description);
            st.executeUpdate();
            con.commit();    
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Failed to insert record", e);
        } finally {
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }
    }

    // Méthode pour mettre à jour une maladie existante
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "UPDATE Maladie SET nom = ?, description = ? WHERE idMaladie = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setString(2, this.description);
            st.setInt(3, this.idMaladie);
            st.executeUpdate();
            con.commit();
        }catch (Exception e) {
            con.rollback();
            throw new Exception("Failed to update record", e);
        } finally {
            if (st != null) st.close();
            if (con != null && !false) con.close();
        }
    }

    // Méthode pour supprimer une maladie par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Maladie WHERE idMaladie = ?";
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
