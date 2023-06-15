package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;
import DAO.DAOConnexion;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import Classes.Facture;
import Classes.*;
public class DAOFacture {
	static Connection cn = LaConnexion.seConnecter();

	
	    
	  public static boolean ajouter(Facture c) { 
		    String requte = "INSERT INTO facture VALUES (?, ?, ?, ?)";

		    try {
		        PreparedStatement pst = cn.prepareStatement(requte);
		     pst.setString(1, c.getNumFacture());      
		     pst.setDate(2, c.getDateFacture());       
		     pst.setFloat(3, c.getTotal());           
		     pst.setString(4, c.getRefCli());          
		        
		        System.out.println(pst);
		        int n = pst.executeUpdate();
		        if (n >= 1) {
		            System.out.println("Ajout réussi");
		            Alert Ajout = new Alert(Alert.AlertType.INFORMATION, "ADDED SUCCESSFULY  ", ButtonType.OK);
		            Ajout.setHeaderText("Ajout Fait");
		            Ajout.setTitle("Facture");
		            Ajout.show();
		            return true;
		        }
		    } catch (SQLException e) {
		        System.out.println("Problème de requête: " + e.getMessage());

		        Alert Ajout = new Alert(Alert.AlertType.ERROR, "NUM EXISTS ALREADY" + e.getMessage(), ButtonType.OK);
		        Ajout.setHeaderText("Echec d ajout");
		        Ajout.setTitle("Facture ajoutee");
		        Ajout.show();
		    }
		    return false;
		}
		    
		public static boolean modifier(Facture c) {
		    String requete = "UPDATE facture SET dateFacture = ?, refCli = ?, total = ? WHERE numFacture = ?";
		    try {
		        PreparedStatement ps = cn.prepareStatement(requete);
		        ps.setString(4, c.getNumFacture());
		        ps.setString(2, c.getRefCli());
		        ps.setDate(1, c.getDateFacture());
		        ps.setFloat(3, c.getTotal());
		        int n = ps.executeUpdate();
		        if (n >= 1) {
		            System.out.println("Modification réussie");
		             Alert modification = new Alert(Alert.AlertType.INFORMATION, "MODIFIED SUCCESSFULY  ", ButtonType.OK);
		             modification.setHeaderText("doneee modification");
		             modification.setTitle("Facture modifie ");
		             modification.show();
		            return true;
		        }
		    } catch (SQLException e) {
		        System.out.println("Problème de requête: " + e.getMessage());
		    }
		    return false;
		}


		 public static boolean supprimer(Facture c) {

		    String requete = "DELETE FROM facture WHERE numFacture=?";
		    try {
		        PreparedStatement ps = cn.prepareStatement(requete);
		        ps.setString(1, c.getNumFacture());
		        int n = ps.executeUpdate();
		        if (n >= 1) {
		            System.out.println("Suppression réussie");
		             Alert dia3 = new Alert(Alert.AlertType.INFORMATION, "DELETED SUCCESSFULY  ", ButtonType.OK);
		        dia3.setHeaderText("Success");
		        dia3.setTitle("Facture");
		        dia3.show();
		            return true;
		        }
		    } catch (SQLException e) {
		        System.out.println("Problème de requête: " + e.getMessage());
		    }
		    return false;
		}
}