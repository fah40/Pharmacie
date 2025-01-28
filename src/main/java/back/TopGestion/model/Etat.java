package back.TopGestion.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Etat {
    private Genre genre;
    private double commission;
    private double pctg;

    public double getPctg() {
        return pctg;
    }

    public void setPctg(double pctg) {
        this.pctg = pctg;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public static Etat[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Etat> etats = new ArrayList<>();

        try {
            String query = "WITH total_commission AS (\r\n" + //
                                "    SELECT SUM(commission) AS total\r\n" + //
                                "    FROM v_historiqueVente\r\n" + //
                                ")\r\n" + //
                                "SELECT \r\n" + //
                                "    idGenre, \r\n" + //
                                "    SUM(commission) AS commission, \r\n" + //
                                "    ROUND(SUM(commission) * 100.0 / (SELECT total FROM total_commission), 2) AS pourcentage\r\n" + //
                                "FROM \r\n" + //
                                "    v_historiqueVente \r\n" + //
                                "GROUP BY \r\n" + //
                                "    idGenre;";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Etat etat = new Etat();
                etat.setGenre(Genre.getById(rs.getInt("idgenre")));
                etat.setCommission(rs.getDouble("commission"));
                etat.setPctg(rs.getDouble("pourcentage"));
                etats.add(etat);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return etats.toArray(new Etat[0]);
    }

    public static Etat[] search(String datemin, String datemax) throws Exception {
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Etat> etats = new ArrayList<>();
    
        try {
            // Requête SQL dynamique avec condition WHERE 1=1 si datemin et datemax sont null
            StringBuilder query = new StringBuilder(
                "WITH total_commission AS (" +
                "    SELECT SUM(commission) AS total " +
                "    FROM v_historiqueVente " +
                "    WHERE 1=1"
            );
    
            // Ajouter les conditions dynamiques
            if (datemin != null && !datemin.isEmpty()) {
                query.append(" AND datevente >= ?");
            }
            if (datemax != null && !datemax.isEmpty()) {
                query.append(" AND datevente <= ?");
            }
    
            query.append(") " +
                         "SELECT " +
                         "    idGenre, " +
                         "    SUM(commission) AS commission, " +
                         "    ROUND(SUM(commission) * 100.0 / (SELECT total FROM total_commission), 2) AS pourcentage " +
                         "FROM " +
                         "    v_historiqueVente " +
                         "WHERE 1=1");
    
            // Ajouter les mêmes conditions à la requête principale
            if (datemin != null && !datemin.isEmpty()) {
                query.append(" AND datevente >= ?");
            }
            if (datemax != null && !datemax.isEmpty()) {
                query.append(" AND datevente <= ?");
            }
    
            query.append(" GROUP BY idGenre");
    
            // Connexion à la base de données
            con = MyConnect.getConnection();
            st = con.prepareStatement(query.toString());
    
            // Définir les paramètres dans la requête
            int paramIndex = 1;
            if (datemin != null && !datemin.isEmpty()) {
                st.setDate(paramIndex++, Date.valueOf(datemin));
            }
            if (datemax != null && !datemax.isEmpty()) {
                st.setDate(paramIndex++, Date.valueOf(datemax));
            }
            if (datemin != null && !datemin.isEmpty()) {
                st.setDate(paramIndex++, Date.valueOf(datemin));
            }
            if (datemax != null && !datemax.isEmpty()) {
                st.setDate(paramIndex++, Date.valueOf(datemax));
            }
    
            // Exécution de la requête
            rs = st.executeQuery();
    
            // Traitement des résultats
            while (rs.next()) {
                Etat etat = new Etat();
                etat.setGenre(Genre.getById(rs.getInt("idGenre"))); // Obtenir l'objet Genre par ID
                etat.setCommission(rs.getDouble("commission"));    // Définir la commission
                etat.setPctg(rs.getDouble("pourcentage"));         // Définir le pourcentage
    
                // Ajouter l'objet Etat à la liste
                etats.add(etat);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de la recherche des états : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            if (rs != null) rs.close();
            if (st != null) st.close();
            if (con != null && !con.isClosed()) con.close();
        }
    
        // Retourner les résultats sous forme de tableau
        return etats.toArray(new Etat[0]);
    }

    public static void main(String[] args) throws Exception {
        Etat[] all= Etat.getAll();

        for (Etat etat : all) {
            System.out.println(etat.pctg);
        }
        
    }

}
