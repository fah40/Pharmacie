package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class Etat {
    private User vendeur;
    private Genre genre;

    public Etat(User vendeur, Genre genre) {
        this.vendeur = vendeur;
        this.genre = genre;
    }

    public User getVendeur() {
        return vendeur;
    }

    public void setVendeur(User vendeur) {
        this.vendeur = vendeur;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO Etat (username, telephone, idGenre) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setInt(1, this.vendeur != null ? this.vendeur.getIdUser() : null);
            st.setInt(2, this.genre != null ? this.genre.getIdGenre() : null);

            try {
                st.executeUpdate();
                con.commit();
            } catch (Exception e) {
                con.rollback();
                throw new Exception("Échec de l'insertion de l'utilisateur", e);
            }
        } finally {
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }
    }

    public static User[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            String query = """
                        SELECT u.*, g.idGenre, g.nom AS genreNom
                        FROM users u
                        LEFT JOIN genre g ON u.idGenre = g.idGenre
                        ORDER BY u.idUser ASC
                    """;
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setUsername(rs.getString("username"));
                user.setTelephone(rs.getString("telephone"));

                // Récupération du genre
                if (rs.getInt("idGenre") != 0) {
                    Genre genre = new Genre(rs.getInt("idGenre"), rs.getString("genreNom"));
                    user.setGenre(genre);
                }

                users.add(user);
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return users.toArray(new User[0]);
    }

}
