package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import Classes.*;
import DAO.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DAOSignUp {
	static Connection cn = LaConnexion.seConnecter();

	
	    ;

	    public static void ajouteUser(Users user) {
	        String query = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement ps = cn.prepareStatement(query)) {
	        	ps.setInt(1, user.getId());
	            ps.setString(2, user.getUsername());
	            ps.setString(3, user.getEmail());
	            String crypterMDP = crypterMDP(user.getPassword());
	            ps.setString(4, crypterMDP);

	            int nb = ps.executeUpdate();
	            if (nb > 0) {
	                showAlert("User added successfully.", Alert.AlertType.INFORMATION);
	                System.out.println("User added successfully.");
	            } else {
	                showAlert("Failed to add user.", Alert.AlertType.ERROR);
	                System.out.println("Failed to add user.");
	            }
	        } catch (SQLException e) {
	            showAlert("Failed to add user: " + e.getMessage(), Alert.AlertType.ERROR);
	            System.out.println("Failed to add user: " + e.getMessage());
	        }
	    }
	    private static String crypterMDP(String password) {
	        try {
	            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	            byte[] hashBytes = messageDigest.digest(password.getBytes());

	            StringBuilder stringBuilder = new StringBuilder();
	            for (byte b : hashBytes) {
	                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	            }

	            return stringBuilder.toString();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println("Encryption algorithm not found: " + e.getMessage());
	        }

	        return null;
	    }
	    private static void showAlert(String message, Alert.AlertType alertType) {
	        Alert showAlert = new Alert(alertType, message, ButtonType.OK);
	        showAlert.setHeaderText("User Registration");
	        showAlert.setTitle("User Registration");
	        showAlert.show();
	    }
	}