package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Medicament {
    int idMedicament;
    String nom;
    Maladie maladie;
    Categorie categorie;
    String description;
    int ageMin;
    int ageMax;
    double prix;

    // Getters et Setters
    public int getIdMedicament() {
        return idMedicament;
    }

    public void setIdMedicament(int idMedicament) {
        this.idMedicament = idMedicament;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Maladie getMaladie() {
        return maladie;
    }

    public void setMaladie(Maladie mld) {
        this.maladie = mld;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie cat) {
        this.categorie = cat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Méthodes pour les opérations CRUD
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO Medicament (nom, idMaladie, idCategorie, description, ageMin, ageMax, prix) VALUES (?, ?, ?, ?, ?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.maladie.getIdMaladie());
            st.setInt(3, this.categorie.getIdCategorie());
            st.setString(4, this.description);
            st.setInt(5, this.ageMin);
            st.setInt(6, this.ageMax);
            st.setDouble(7, this.prix);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de l'insertion du médicament", e);
            }
        } finally {
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    }

    public static Medicament getById(int idMedicament) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Medicament instance = null;

        try {
            String query = "SELECT * FROM Medicament WHERE idMedicament = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idMedicament);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new Medicament();
                instance.setIdMedicament(rs.getInt("idMedicament"));
                instance.setNom(rs.getString("nom"));
                instance.setMaladie(Maladie.getById(rs.getInt("idMaladie")));
                instance.setCategorie(Categorie.getById(rs.getInt("idCategorie")));
                instance.setDescription(rs.getString("description"));
                instance.setAgeMin(rs.getInt("ageMin"));
                instance.setAgeMax(rs.getInt("ageMax"));
                instance.setPrix(rs.getDouble("prix"));
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return instance;
    }

    public static Medicament[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Medicament> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM Medicament ORDER BY idMedicament ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Medicament item = new Medicament();
                item.setIdMedicament(rs.getInt("idMedicament"));
                item.setNom(rs.getString("nom"));
                item.setMaladie(Maladie.getById(rs.getInt("idMaladie")));
                item.setCategorie(Categorie.getById(rs.getInt("idCategorie")));
                item.setDescription(rs.getString("description"));
                item.setAgeMin(rs.getInt("ageMin"));
                item.setAgeMax(rs.getInt("ageMax"));
                item.setPrix(rs.getDouble("prix"));

                items.add(item);
            }
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Medicament[0]);
    }
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE Medicament SET nom = ?, idMaladie = ?, description = ?, ageMin = ?, ageMax = ?, idCategorie = ? WHERE idMedicament = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.maladie.getIdMaladie());
            st.setString(3, this.description);
            st.setInt(4, this.ageMin);
            st.setInt(5, this.ageMax);
            st.setInt(6, this.categorie.getIdCategorie());
            st.setInt(8, this.idMedicament);

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

    public static void deleteById(int idMedicament) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Medicament WHERE idMedicament = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idMedicament);

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

    public static Medicament[] search(int idMaladie, int ageMin, int ageMax) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Medicament> items = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Medicament WHERE 1=1");

        try {
            // Construction de la requête avec les conditions dynamiques
            if (idMaladie > 0) {
                query.append(" AND idMaladie = ?");
            }
            if (ageMin > 0) {
                query.append(" AND ageMin <= ?");
            }
            if (ageMax > 0) {
                query.append(" AND ageMax >= ?");
            }

            // Connexion à la base de données
            con = MyConnect.getConnection();
            st = con.prepareStatement(query.toString());

            // Définition des paramètres dans la requête
            int paramIndex = 1;
            if (idMaladie > 0) {
                st.setInt(paramIndex++, idMaladie);
            }
            if (ageMin > 0) {
                st.setInt(paramIndex++, ageMin);
            }
            if (ageMax > 0) {
                st.setInt(paramIndex++, ageMax);
            }

            // Exécution de la requête
            rs = st.executeQuery();

            // Traitement des résultats
            while (rs.next()) {
                Medicament item = new Medicament();
                item.setIdMedicament(rs.getInt("idMedicament"));
                item.setNom(rs.getString("nom"));
                item.setMaladie(Maladie.getById(rs.getInt("idMaladie")));
                item.setDescription(rs.getString("description"));
                item.setAgeMin(rs.getInt("ageMin"));
                item.setAgeMax(rs.getInt("ageMax"));
                item.setCategorie(Categorie.getById(rs.getInt("idCategorie")));

                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche des médicaments : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }

        return items.toArray(new Medicament[0]);
    }

    public boolean IsConseiller(int id) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        boolean exists = false;
    
        try {
            String query = "SELECT 1 FROM Conseiller WHERE id = ? AND EXTRACT(YEAR FROM date) = EXTRACT(YEAR FROM now()) AND EXTRACT(MONTH FROM date) = EXTRACT(MONTH FROM now())";
            con = MyConnect.getConnection();
            st = con.prepareStatement(query);
            st.setInt(1, id);
    
            rs = st.executeQuery();
    
            if (rs.next()) {
                exists = true;
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la vérification de l'existence du conseiller : " + e.getMessage(), e);
        } finally {
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        return exists;
    }
}
