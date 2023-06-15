package Controller;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import Classes.Client;
import DAO.DAOClient;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;

public class FXMLControllerClient implements Initializable {
	@FXML
	AnchorPane s;
	@FXML
	private Button btnLogout;
	@FXML
	private TextField txtCode;
	@FXML
	private TextField txtNom;
	@FXML
	private TextField txtAdresse;
	@FXML
	private TextField txtEmail;
	@FXML
	private TableView<Client> tv;
	@FXML
	private TableColumn<Client, String> CodeClient;
	@FXML
	private TableColumn<Client, String> NomClient;
	@FXML
	private TableColumn<Client, String> AdresseClient;
	@FXML
	private TableColumn<Client, String> EmailClient;

	private ObservableList<Client> observableList;
	private DAOClient daoClient;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoClient = new DAOClient();
		observableList = FXCollections.observableArrayList();

		CodeClient.setCellValueFactory(new PropertyValueFactory<>("codeCli"));
		NomClient.setCellValueFactory(new PropertyValueFactory<>("nomCli"));
		AdresseClient.setCellValueFactory(new PropertyValueFactory<>("adresseCli"));
		EmailClient.setCellValueFactory(new PropertyValueFactory<>("emailCli"));

		tv.setItems(observableList);
		tv.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				Details(newSelection);
			} else {
				clearFields();
			}
		});

		lister();
	}

	private void Details(Client client) {
		txtCode.setText(client.getCodeCli());
		txtNom.setText(client.getNomCli());
		txtAdresse.setText(client.getAdresseCli());
		txtEmail.setText(client.getEmailCli());
	}

	private void lister() {
		observableList.addAll(daoClient.lister());
	}

	private void clearFields() {
		txtCode.clear();
		txtNom.clear();
		txtAdresse.clear();
		txtEmail.clear();
	}

	@FXML
	private void supprimer() {
		Client client = tv.getSelectionModel().getSelectedItem();
		if (client != null) {
			if (DAOClient.supprimer(client)) {
				observableList.remove(client);
			}
		}
	}

	@FXML
	private void ajouter() {
		String code = txtCode.getText();
		String nom = txtNom.getText();
		String adresse = txtAdresse.getText();
		String email = txtEmail.getText();

		if (!code.isEmpty() && !nom.isEmpty() && !adresse.isEmpty() && !email.isEmpty()) {
			// Check if the code already exists
			if (isCodeUnique(code)) {
				DAOClient.Ajouter(code, nom, adresse, email);
				Client client = new Client(code, nom, adresse, email);
				observableList.add(client);
				clearFields();
			} else {
				showAlert("Duplicate code. Please enter a unique code.", Alert.AlertType.ERROR);
			}
		}
	}

	private void showAlert(String message, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}

	private boolean isCodeUnique(String code) {

		for (Client client : observableList) {
			if (client.getCodeCli().equals(code)) {
				return false;
			}
		}
		return true;
	}

	@FXML
	private void modifier() {
		Client client = tv.getSelectionModel().getSelectedItem();
		if (client != null) {
			String nom = txtNom.getText();
			String adresse = txtAdresse.getText();
			String email = txtEmail.getText();

			if (!nom.isEmpty() && !adresse.isEmpty() && !email.isEmpty()) {
				client.setNomCli(nom);
				client.setAdresseCli(adresse);
				client.setEmailCli(email);

				if (daoClient.modifier(client)) {
					tv.refresh();
					clearFields();
				}
			}
		}
	}

	@FXML
	private void onGestionFactureClicked() {
	    Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
	    confirmationDialog.setTitle("Confirmation");
	    confirmationDialog.setHeaderText("Go to Gestion Facture");
	    confirmationDialog.setContentText("Are you sure you want to go to the Gestion Facture screen?");

	    Optional<ButtonType> result = confirmationDialog.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLFacture.fxml"));
	            Parent root = loader.load();

	            FXMLControllerFacture factureController = loader.getController();

	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.show();

	            // Close the current window (optional)
	            Stage currentStage = (Stage) tv.getScene().getWindow();
	            currentStage.close();
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