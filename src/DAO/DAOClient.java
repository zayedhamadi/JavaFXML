package DAO;

import Classes.Client;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public class DAOClient {
	
	public static void Ajouter(String p1,String p2,String p3,String p4) {
	      
	    Connection cn =LaConnexion.seConnecter() ;
	       String requete = "insert into client values(?,?,?,?)"; 
	                   
	        try{
	            PreparedStatement pst=cn.prepareStatement(requete);
	           pst.setString(1,p1); 
	           pst.setString(2,p2);
	           pst.setString(3,p3);
	           pst.setString(4,p4);
	                     
	            int n=pst.executeUpdate();
	             if (n>=1)
	                   System.out.println("ajout réussi");
	             
	          
	        }catch(SQLException ex)
	        {
	            System.out.println("problème de requête"+ex.getMessage());
	        }}
	
 static Connection cn = LaConnexion.seConnecter();

    public static ArrayList<Client> lister() {
        ArrayList<Client> cl = new ArrayList<>();
        String requete = "select * from Client; ";
        Client c;
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);

            while (rs.next()) {
                c = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                cl.add(c);
            }
            
            System.err.print("consultation ok");
            System.out.println();
            System.out.println();
        } catch (SQLException e) {
            System.out.println("pbleme de consultation" + e.getMessage());
        }
        return cl;
    }

    public static TreeSet<Client> listerParVille(String ville) {
        TreeSet<Client> cl = new TreeSet<>();
        String requete = "SELECT * FROM Client WHERE adresseCli LIKE ? ";
        Client c;
        try {
            PreparedStatement st = cn.prepareStatement(requete);
            st.setString(1, ville + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                c = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                cl.add(c);
            }
            System.out.print("Consultation par ville OK");
        } catch (SQLException e) {
            System.out.println("Problème de consultation par ville : " + e.getMessage());
        }
        return cl;
    }

    public static TreeMap<String, Client> trouver(String val) {
        TreeMap<String, Client> res = new TreeMap<String, Client>();
        Connection cn = LaConnexion.seConnecter();
        String requete = "select * from client where nomCli like ? ";
        try {
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, val + "%");
            ResultSet rs = pst.executeQuery();
            String code;
            String nom, adresse, email;
            Client c;
            if (rs != null) {
                while (rs.next()) {
                    code = rs.getString("codeCli");
                    nom = rs.getString("nomCli");
                    adresse = rs.getString("adresseCli");
                    email = rs.getString("emailCli");
                    c = new Client(code, nom, adresse, email);
                    res.put(code, c);
                    System.out.print("Consultation par nom OK");
                }
            }
        } catch (SQLException ex) {
            System.err.println("probleme de req select" + ex.getMessage());
        }

        return res;

    }

    public static Client chercher(String CodeCli) {
        Client c = null;
        String requete = "SELECT * FROM Client WHERE codeCli ='" + CodeCli + "';";
        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                c = new Client(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
                System.out.print("Consultation par code OK");
            }

        } catch (SQLException e) {
            System.out.println("Problème de consultation par ville : " + e.getMessage());
        }
        return c;
    }

    public static boolean ajouterv1(Client c) {
        String requete = "insert into client values(?,?,?,?)";
        try {
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, c.getCodeCli());
            pst.setNString(2, c.getNomCli());
            pst.setString(3, c.getCodeCli());
            pst.setNString(4, c.getNomCli());

            int n = pst.executeUpdate();
            if (n >= 1) {System.out.println();
                System.out.println("ajout réussi");
            }
            return true;

        } catch (SQLException ex) {System.out.println();
            System.out.println("problème de requête d'ajout" + ex.getMessage());
        }

        return false;
    }
    public boolean modifier(Client client) {
        Connection connection = LaConnexion.seConnecter(); 
        try {
            String query = "UPDATE client SET nomCli = ?, adresseCli = ?, emailCli = ? WHERE codeCli = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getNomCli());
            statement.setString(2, client.getAdresseCli());
            statement.setString(3, client.getEmailCli());
            statement.setString(4, client.getCodeCli());
            int rowsUpdated = statement.executeUpdate();
            statement.close(); System.err.println("saret modification");
            return rowsUpdated > 0;
           
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
    }

    public static boolean changerAdresse(Client c, String adr) {
        Connection cn = LaConnexion.seConnecter();
        String requete = "update client set adresseCli=? where codeCli=?;";

        try {
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, adr);
            pst.setString(2, c.getCodeCli());

            int n = pst.executeUpdate();
            if (n >= 1) {
                System.out.println("Modif réussi");
            }
            return true;

        } catch (SQLException ex) {
            System.out.println("problème de requête Modif" + ex.getMessage());
        }
        return false;
    }

    public static boolean supprimer(Client c) {
        Connection cn = LaConnexion.seConnecter();
        String requete = "delete from client where codeCli=?;";
        try {
            PreparedStatement pst = cn.prepareStatement(requete);
            pst.setString(1, c.getCodeCli());
            int n = pst.executeUpdate();
            if (n >= 1) {
                System.err.println("suppression réussie");
           System.out.println(); }
            return true;

        } catch (SQLException ex) {
            System.out.println("problème de requête de suppression" + ex.getMessage());
        }
        return false;
    }}
    /*     public static boolean ajouterv2(Client c) {
        String requete = "insert into client values('" + c.getCodeCli() + "','" + c.getNomCli() + "','" + c.getCodeCli()+ "','" + c.getNomCli()+ "');";
        try {
            Statement pst = cn.createStatement();


            int n = pst.executeUpdate(requete);
            if (n >= 1) {
            	System.out.println();
                System.out.println("ajout réussi");
            }
            return true;

        } catch (SQLException ex) {System.out.println();
            System.out.println("problème de requête d'ajout" + ex.getMessage());
        }

        return false;
    }*/