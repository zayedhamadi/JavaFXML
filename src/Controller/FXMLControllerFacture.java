package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import DAO.*;
import Classes.Facture;
import DAO.LaConnexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLControllerFacture implements Initializable {

	@FXML
	TableView<Facture> tv;

	@FXML
	TextField numFacture;

	@FXML
	DatePicker dateFacture;

	@FXML
	TextField total;

	@FXML
	ComboBox<String> refCli;

	@FXML
	TableColumn<Facture, String> colNumFacture;

	@FXML
	TableColumn<Facture, Date> colDateFacture;

	@FXML
	TableColumn<Facture, Float> colTotal;

	@FXML
	TableColumn<Facture, String> colRefCli;
	@FXML
	AnchorPane s;
	@FXML
	private Button ajouter;
	
	@FXML
	private Button supprimer;
	@FXML
	private Button modifier;	
	@FXML
    private Button gestionClient; 
	@FXML
	private Button btnLogout;
	static Connection cn = LaConnexion.seConnecter();

	ObservableList<Facture> observableList;
	
	@FXML
	private void handleModifier(ActionEvent event) {
	    modifier();
	}

	@FXML
	private void handleAjouter(ActionEvent event) {
		ajouter();
	}
	@FXML
	private void handleSupprimer(ActionEvent event) {
	    supprimer();
	}
	public void lister() {
		observableList = FXCollections.observableArrayList();
		tv.getItems().clear();
		try {
			ResultSet rs = cn.createStatement().executeQuery("select * from facture");
			while (rs.next()) {
				observableList.add(new Facture(rs.getString(1), rs.getDate(2), rs.getFloat(3), rs.getString(4)));
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
		tv.setItems(observableList);
	}

	ObservableList<String> observList;

	public int GetCodeCli() {
		int x = 1;
		observList = FXCollections.observableArrayList();

		try {
			ResultSet rs = cn.createStatement().executeQuery("select codeCli from client");
			while (rs.next()) {
				observList.add(rs.getString(1));

				System.out.println(rs.getString(x));
			}
		} catch (SQLException ex) {
			System.out.println("error" + ex.getMessage());
		}
		this.refCli.setItems(observList);
		return x;
	}

	

	private void showAlert(String message, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType, message, ButtonType.OK);
		alert.setHeaderText("Attention");
		alert.setTitle("Facture");
		alert.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		observableList = FXCollections.observableArrayList();
		colNumFacture.setCellValueFactory(new PropertyValueFactory<>("numFacture"));
		colDateFacture.setCellValueFactory(new PropertyValueFactory<>("dateFacture"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
		colRefCli.setCellValueFactory(new PropertyValueFactory<>("refCli"));
		tv.setItems(observableList);

		// Add a listener to the table view to detect selection changes
		tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				// Get the selected item from the table
				Facture selectedFacture = tv.getSelectionModel().getSelectedItem();

				// Set the values of the selected item to the text fields, combo box, and date
				// picker
				numFacture.setText(selectedFacture.getNumFacture());
				dateFacture.setValue(selectedFacture.getDateFacture().toLocalDate());
				total.setText(String.valueOf(selectedFacture.getTotal()));
				refCli.setValue(selectedFacture.getRefCli());
			}
		});
	    modifier.setOnAction(this::handleModifier);

		ajouter.setOnAction(this::handleAjouter);
		supprimer.setOnAction(this::handleSupprimer);
		this.GetCodeCli();
		lister();
	}

	private void clearFields() {
		numFacture.clear();
		dateFacture.setValue(null);
		total.clear();

	}
	public void ajouter() {

		LocalDate date1 = dateFacture.getValue();
		System.out.println(date1.toString());
		if (numFacture.getText().isEmpty() || total.getText().isEmpty()) {
			Alert dia3 = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs", ButtonType.OK);
			dia3.setHeaderText("Attention");
			dia3.setTitle("Facture");
			dia3.show();
		} else {
			String num = numFacture.getText();
			LocalDate date = dateFacture.getValue();
			System.out.println(date.toString());
			Date dateFacture = Date.valueOf(date);
			float t = Float.parseFloat(total.getText());
			String ref = refCli.getValue();
			Facture f = new Facture(num, dateFacture, t, ref);
			DAOFacture.ajouter(f);
			lister();
			clearFields();
		}
	}
	public void supprimer() {
		Facture selectedFacture = tv.getSelectionModel().getSelectedItem();

		if (selectedFacture != null) {
			// Show a confirmation dialog
			Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
			confirmationDialog.setTitle("Confirmation");
			confirmationDialog.setHeaderText("Supprimer la facture " + selectedFacture.getNumFacture());
			confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cette facture ?");
			confirmationDialog.showAndWait();

			if (confirmationDialog.getResult() == ButtonType.OK) {
				// Call the DAO method to delete the facture from the database
				if (DAOFacture.supprimer(selectedFacture)) {
					// Remove the facture from the table view
					tv.getItems().remove(selectedFacture);
					clearFields();
					System.out.println("Suppression réussie");
				} else {
					System.out.println("La suppression a échoué");
				}
			} else {
				System.out.println("Suppression annulée par l'utilisateur");
			}
		} else {
			System.out.println("Aucune facture sélectionnée");
		}
		lister();
	}
	public void modifier() {
	    Facture selectedFacture = tv.getSelectionModel().getSelectedItem();

	    if (selectedFacture != null) {
	        String newNumFacture = numFacture.getText();
	        LocalDate newDateFacture = dateFacture.getValue();
	        Date newDate = Date.valueOf(newDateFacture);
	        float newTotal = Float.parseFloat(total.getText());
	        String newRefCli = refCli.getValue();

	        Facture modifiedFacture = new Facture(newNumFacture, newDate, newTotal, newRefCli);

	        if (DAOFacture.modifier(modifiedFacture)) {
	            
	            selectedFacture.setNumFacture(newNumFacture);
	            
	            selectedFacture.setTotal(newTotal);
	            selectedFacture.setRefCli(newRefCli);
	            
	            clearFields();
	            System.out.println("Modification réussie");
	        } else {
	            System.out.println("Échec de la modification");
	        }
	    } else {
	        System.out.println("Sélectionnez une facture");
	    }
	    lister();
	}
	@FXML
	private void gestionClientClicked(ActionEvent event) {
	    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationDialog.setTitle("Confirmation");
	    confirmationDialog.setHeaderText("Go to Gestion Client");
	    confirmationDialog.setContentText("Are you sure you want to go to the Gestion Client screen?");

	    Optional<ButtonType> result = confirmationDialog.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLController.fxml"));
	            Parent gestionClientParent = loader.load();

	            FXMLControllerClient FXMLControllerClient = loader.getController();

	            Scene gestionClientScene = new Scene(gestionClientParent);
	            Stage stage = (Stage) s.getScene().getWindow();
	            stage.setScene(gestionClientScene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}



	 @FXML
	 private void logout(ActionEvent event) {
	     Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
	     confirmationDialog.setTitle("Confirmation");
	     confirmationDialog.setHeaderText("Logout");
	     confirmationDialog.setContentText("Are you sure you want to logout?");

	     Optional<ButtonType> result = confirmationDialog.showAndWait();
	     if (result.isPresent() && result.get() == ButtonType.OK) {
	         try {
	             Node source = (Node) event.getSource();
	             Stage currentStage = (Stage) source.getScene().getWindow();

	             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
	             Parent root = loader.load();
	             LoginController loginController = loader.getController();

	             Scene scene = new Scene(root);
	             currentStage.setScene(scene);
	             currentStage.setTitle("Login");
	             currentStage.show();
	         } catch (IOException e) {
	             showAlert("Failed to load FXMLLogin.fxml", Alert.AlertType.ERROR);
	             System.out.println("Failed to load FXMLLogin.fxml");
	         }
	     }
	 }

}
