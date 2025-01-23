package back.TopGestion.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import back.TopGestion.bd.MyConnect;

public class User {
    private int idUser;
    private String username;
    private String telephone;

    // Constructeur par défaut
    public User() {
    }

    // Constructeur avec tous les champs
    public User(int idUser, String username, String telephone) {
        this.idUser = idUser;
        this.username = username;
        this.telephone = telephone;
    }

    // Getters et Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void insert() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "INSERT INTO users (username, telephone) VALUES (?, ?, ?)";
            st = con.prepareStatement(query);
            st.setString(1, this.username);
            st.setString(3, this.telephone);

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

    public static User getById(int idUser) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE idUser = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idUser);
            rs = st.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setUsername(rs.getString("username"));
                user.setTelephone(rs.getString("telephone"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return user;
    }

    public static User[] getAll() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            String query = "SELECT * FROM users ORDER BY idUser ASC";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setUsername(rs.getString("username"));
                user.setTelephone(rs.getString("telephone"));

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

    public void update() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "UPDATE users SET username = ?, telephone = ? WHERE idUser = ?";
            st = con.prepareStatement(query);
            st.setString(1, this.username);
            st.setString(3, this.telephone);
            st.setInt(4, this.idUser);

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

    public static void deleteById(int idUser) throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;

        try {
            String query = "DELETE FROM users WHERE idUser = ?";
            st = con.prepareStatement(query);
            st.setInt(1, idUser);

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

    public static User getLast() throws Exception {
        Connection con = MyConnect.getConnection();
        PreparedStatement st = null;
        ResultSet rs = null;
        User user = null;

        try {
            String query = "SELECT * FROM users ORDER BY idUser DESC LIMIT 1";
            st = con.prepareStatement(query);
            rs = st.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setUsername(rs.getString("username"));
                user.setTelephone(rs.getString("telephone"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (st != null)
                st.close();
            if (con != null && !con.isClosed())
                con.close();
        }

        return user;
    }
}
