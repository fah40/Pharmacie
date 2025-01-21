package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import back.TopGestion.bd.MyConnect;

public class Conseiller {
    int id;
    Medicament medicament; // Remplacer idMedicament par un objet Medicament
    String date;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    // Méthode pour obtenir le dernier conseiller ajouté
    public static Conseiller getLast() throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Conseiller instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM Conseiller ORDER BY id DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Conseiller();
                instance.setId(rs.getInt("id"));
                // Récupérer l'objet Medicament au lieu de l'ID
                Medicament medicament = Medicament.getById(rs.getInt("idMedicament"));
                instance.setMedicament(medicament);
                instance.setDate(rs.getString("date"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return instance;
    }

    // Méthode pour obtenir un conseiller par son ID
    public static Conseiller getById(int id) throws Exception {
        PreparedStatement st = null;
        ResultSet rs = null;
        Conseiller instance = null;

        Connection con = MyConnect.getConnection();
        try {
            String query = "SELECT * FROM Conseiller WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Conseiller();
                instance.setId(rs.getInt("id"));
                // Récupérer l'objet Medicament au lieu de l'ID
                Medicament medicament = Medicament.getById(rs.getInt("idMedicament"));
                instance.setMedicament(medicament);
                instance.setDate(rs.getString("date"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return instance;
    }

    // Méthode pour obtenir tous les conseillers
    public static Conseiller[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Conseiller> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Conseiller ORDER BY id ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Conseiller item = new Conseiller();
                item.setId(rs.getInt("id"));
                // Récupérer l'objet Medicament au lieu de l'ID
                Medicament medicament = Medicament.getById(rs.getInt("idMedicament"));
                item.setMedicament(medicament);
                item.setDate(rs.getString("date"));
                items.add(item);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Conseiller[0]);
    }

    // Méthode pour insérer un nouveau conseiller
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            // Vérification de l'objet Medicament et de la date
            if (this.medicament == null || this.date == null || this.date.isEmpty()) {
                throw new IllegalArgumentException("Medicament ou date manquants");
            }
    
            String query = "INSERT INTO Conseiller (idMedicament, date) VALUES (?, ?)";

            Date sqlDate = Date.valueOf(this.date);
            st = con.prepareStatement(query);
            st.setInt(1, this.medicament.getIdMedicament()); // Assurez-vous que medicament est non null
            st.setDate(2, sqlDate); // Vérifiez que la date est valide
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            e.printStackTrace(); // Loggez l'exception pour plus de détails
            throw new Exception("Failed to insert record", e);
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }
    

    // Méthode pour mettre à jour un conseiller existant
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        try {
            String query = "UPDATE Conseiller SET idMedicament = ?, date = ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, this.medicament.getIdMedicament()); // Récupérer l'ID du médicament
            st.setString(2, this.date);
            st.setInt(3, this.id);
            st.executeUpdate();
            con.commit();
        } catch (Exception e) {
            con.rollback();
            throw new Exception("Failed to update record", e);
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }

    // Méthode pour supprimer un conseiller par ID
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Conseiller WHERE id = ?";
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

    public static Conseiller[] search(String date,int annee) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Conseiller> items = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM v_conseillerMedicament WHERE 1=1");
    
        try {
            int year = 0;
            int month = 0;
            if (date != null && !date.isEmpty()) {
                // Extraction de l'année et du mois
                String[] parts = date.split("-");
                year = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
    
                query.append(" AND EXTRACT(YEAR FROM date) = ? AND EXTRACT(MONTH FROM date) = ?");
            }

            if (annee != 0) {
                query.append(" AND EXTRACT(YEAR FROM date) = ? ");
            }
    
            // Connexion à la base de données
            con = MyConnect.getConnection();
            st = con.prepareStatement(query.toString());
            
            int paramIndex = 1;
            // Définir les paramètres uniquement si la date est présente
            if (date != null && !date.isEmpty() ) {
                st.setInt(paramIndex++, year);
                st.setInt(paramIndex++, month);

            }
            if (annee != 0) {
                st.setInt(paramIndex++, annee);
            }
    
            // Exécution de la requête
            rs = st.executeQuery();
    
            // Traitement des résultats
            while (rs.next()) {
                Conseiller item = new Conseiller();
                item.setId(rs.getInt("id"));
                // Récupérer l'objet Medicament au lieu de l'ID
                Medicament medicament = Medicament.getById(rs.getInt("idMedicament"));
                if (medicament != null) {
                    item.setMedicament(medicament);
                } else {
                    // Gérer le cas où le Medicament n'existe pas
                    item.setMedicament(new Medicament()); // Ou un autre traitement approprié
                }
                item.setDate(rs.getString("date"));
                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche des conseillers : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        return items.toArray(new Conseiller[0]);
    }    
}
