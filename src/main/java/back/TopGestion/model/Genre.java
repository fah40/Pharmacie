package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Genre {
    private int idGenre;
    private String nom;

    // Constructeur par défaut
    public Genre() {
    }

    // Constructeur avec tous les champs
    public Genre(int idGenre, String nom) {
        this.idGenre = idGenre;
        this.nom = nom;
    }

    // Getters et Setters
    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode pour insérer un genre dans la base de données
    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO Genre (nom) VALUES (?)";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de l'insertion du genre", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    // Méthode pour récupérer un genre par son ID
    public static Genre getById(int idGenre) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Genre genre = null;

        try {
            String query = "SELECT * FROM Genre WHERE idGenre = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idGenre);
            rs = st.executeQuery();

            if (rs.next()) {
                genre = new Genre();
                genre.setIdGenre(rs.getInt("idGenre"));
                genre.setNom(rs.getString("nom"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return genre;
    }

    // Méthode pour récupérer tous les genres
    public static Genre[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<Genre> genres = new ArrayList<>();

        try {
            String query = "SELECT * FROM Genre ORDER BY idGenre ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                Genre genre = new Genre();
                genre.setIdGenre(rs.getInt("idGenre"));
                genre.setNom(rs.getString("nom"));

                genres.add(genre);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return genres.toArray(new Genre[0]);
    }

    // Méthode pour mettre à jour un genre
    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE Genre SET nom = ? WHERE idGenre = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.nom);
            st.setInt(2, this.idGenre);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de la mise à jour du genre", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    // Méthode pour supprimer un genre par son ID
    public static void deleteById(int idGenre) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM Genre WHERE idGenre = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idGenre);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de la suppression du genre", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    // Méthode pour récupérer le dernier genre ajouté
    public static Genre getLast() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        Genre genre = null;

        try {
            String query = "SELECT * FROM Genre ORDER BY idGenre DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                genre = new Genre();
                genre.setIdGenre(rs.getInt("idGenre"));
                genre.setNom(rs.getString("nom"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return genre;
    }
}
