package back.TopGestion.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class VenteMedicament {
    private int id;
    private Medicament medicament;
    private int quantiteVendue;
    private Date dateVente;
    private double prixUnitaire;
    private double total;
    private Client client;
    private User vendeur;
    private double commission;

    public User getVendeur() {
        return vendeur;
    }

    public void setVendeur(User idVendeur) {
        this.vendeur = idVendeur;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    // Getters and Setters
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

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

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // Méthode pour récupérer une vente par ID
    public static VenteMedicament getById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        VenteMedicament instance = null;

        try {
            String query = "SELECT * FROM VenteMedicament WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                instance = new VenteMedicament();
                instance.setId(rs.getInt("id"));
                instance.setMedicament(Medicament.getById(rs.getInt("idMedicament")));
                instance.setQuantiteVendue(rs.getInt("quantiteVendue"));
                instance.setDateVente(rs.getDate("dateVente"));
                instance.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                double tot = rs.getDouble("prixUnitaire") * rs.getInt("quantiteVendue");
                instance.setTotal(tot);
                instance.setClient(Client.getById(rs.getInt("idClient")));
                instance.setVendeur(User.getById(rs.getInt("Vendeur")));
                instance.setCommission(rs.getDouble("commission"));
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

    // Méthode pour récupérer toutes les ventes
    public static VenteMedicament[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<VenteMedicament> items = new ArrayList<>();

        try {
            String query = "SELECT * FROM VenteMedicament ORDER BY id ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                VenteMedicament item = new VenteMedicament();
                item.setId(rs.getInt("id"));
                item.setMedicament(Medicament.getById(rs.getInt("idMedicament")));
                item.setQuantiteVendue(rs.getInt("quantiteVendue"));
                item.setDateVente(rs.getDate("dateVente"));
                item.setPrixUnitaire(rs.getDouble("prixUnitaire"));
                item.setTotal(rs.getDouble("prixUnitaire") * rs.getInt("quantiteVendue"));
                item.setClient(Client.getById(rs.getInt("idClient")));
                item.setVendeur(User.getById(rs.getInt("idVendeur")));
                item.setCommission(rs.getDouble("commission"));

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

        return items.toArray(new VenteMedicament[0]);
    }

    // Méthode d'insertion
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO VenteMedicament (idMedicament, quantiteVendue, dateVente, prixUnitaire, idClient,idVendeur,commission) VALUES (?, ?, ?, ?, ?,?,?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.medicament.getIdMedicament());
            st.setInt(2, this.quantiteVendue);
            st.setDate(3, this.dateVente);
            st.setDouble(4, this.prixUnitaire);
            st.setInt(5, this.client.getIdClient());
            st.setInt(6, this.vendeur.getIdUser());
            st.setDouble(7, this.commission);

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

    // Méthode de mise à jour
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE VenteMedicament SET idMedicament = ?, quantiteVendue = ?, dateVente = ?, prixUnitaire = ?, idClient = ? , idVendeur= ? WHERE id = ?";
            st = con.prepareStatement(query);
            st.setInt(1, this.medicament.getIdMedicament());
            st.setInt(2, this.quantiteVendue);
            st.setDate(3, this.dateVente);
            st.setDouble(4, this.prixUnitaire);
            st.setInt(5, this.client.getIdClient());
            st.setInt(6, this.id);
            st.setInt(7, this.vendeur.getIdUser());

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Failed to update record", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    // Méthode de suppression
    public static void deleteById(int id) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM VenteMedicament WHERE id = ?";
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

    public static VenteMedicament[] search(int type, int idCategorie, int idVendeur, String datemin, String datemax) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<VenteMedicament> items = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM v_historiqueVente WHERE 1=1");
        System.out.println(datemin + "---" + datemax);
        try {
            // Ajout des conditions dynamiques
            if (type > 0) {
                if (type == 1) {
                    query.append(" AND ageMin >= 18");
                }
                if (type == 2) {
                    query.append(" AND ageMin >= 6 AND ageMax < 18");
                }
                if (type == 3) {
                    query.append(" AND ageMin >= 0 AND ageMax <= 5");
                }
            }
            if (idVendeur > 0) {
                query.append(" AND idvendeur = ?");
            }
            if (datemin != null && !datemin.isEmpty()) {
                query.append(" AND datevente >= ?");
            }
            if (datemax != null && !datemax.isEmpty()) {
                query.append(" AND datevente <= ?");
            }
            if (idCategorie > 0) {
                query.append(" AND idCategorie = ?");
            }

            // Connexion à la base de données
            con = MyConnect.getConnection();
            st = con.prepareStatement(query.toString());
            
            // Définir les paramètres
            int index=1;
            if (idVendeur > 0) {
                st.setInt(index++, idVendeur);
            }
            if (datemin != null && !datemin.isEmpty()) {
                st.setDate(index++, Date.valueOf(datemin));
            }
            if (datemax != null && !datemax.isEmpty()) {
                st.setDate(index++, Date.valueOf(datemax));
            }
            if (idCategorie > 0) {
                st.setInt(index++, idCategorie);
            }
            
            System.out.println(query);
            // Exécution de la requête
            rs = st.executeQuery();

            // Parcourir les résultats
            while (rs.next()) {
                VenteMedicament item = new VenteMedicament();
                item.setId(rs.getInt("id"));
                item.setMedicament(Medicament.getById(rs.getInt("idMedicament")));
                item.setQuantiteVendue(rs.getInt("quantiteVendue"));
                item.setDateVente(rs.getDate("dateVente"));
                item.setPrixUnitaire(rs.getDouble("prix"));
                item.setTotal(item.getPrixUnitaire() * rs.getInt("quantiteVendue"));
                item.setVendeur(User.getById(rs.getInt("idVendeur")));
                item.setCommission(rs.getDouble("commission"));

                // Récupérer l'objet Client associé
                int clientId = rs.getInt("idClient");
                if (clientId > 0) {
                    item.setClient(Client.getById(clientId));
                }

                items.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche des médicaments : " + e.getMessage());
        } finally {
            // Fermer les ressources
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return items.toArray(new VenteMedicament[0]);
    }

}
