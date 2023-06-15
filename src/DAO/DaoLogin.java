package DAO;



import Classes.Users;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DaoLogin {
    static Connection cn = LaConnexion.seConnecter();

    public static boolean login(Users c) {
        String requte = "SELECT * FROM users WHERE email=? AND password=?";
        try {
            PreparedStatement pst = cn.prepareStatement(requte);
            pst.setString(1, c.getEmail());
            String crype = crypterMDP(c.getPassword());
            pst.setString(2, crype);

            ResultSet r = pst.executeQuery();

            if (r.next()) {
                
                Alert Success = new Alert(Alert.AlertType.INFORMATION, "You are connectiong right now", ButtonType.OK);
                Success.setHeaderText("Success");
                Success.setTitle("Client");
                Success.show();
                return true;
            } 
    }catch (SQLException e) {
            // Error occurred during login
            Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "fama mochkla  ", ButtonType.OK);
            dia3.setHeaderText("prob");
            dia3.setTitle("Facture");
            dia3.show();
        }
        return false;
    }
    private static String crypterMDP(String p) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(p.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xFF & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }
   
}
