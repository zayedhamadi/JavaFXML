package DAO;

import java.awt.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Classes.Users;

public class ForgetPW {
    static Connection cn = LaConnexion.seConnecter();

    public static void chercherId(Users user) {
       
        
        String req = "SELECT id, username, email,password FROM users WHERE id=?";
        int id = user.getId();
        try {
            PreparedStatement ps = cn.prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                // Update the user object with retrieved data
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
            } else {
                // User not found, reset the user object
                user.setUsername("");
                user.setEmail("");
                user.setPassword("");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
}