package Controller;

import DAO.DaoLogin;
import Classes.Users;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;
import javafx.scene.Node;

public class LoginController {
	@FXML
	private StackPane s;

	@FXML
	private TextField email;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginButton;
	@FXML
	private ImageView eyeImage;
	private boolean isPasswordVisible = false;
	private TextField visiblePasswordField;
	@FXML
	private void initialize() {
	    eyeImage.setOnMouseClicked(event -> togglePasswordVisibility());

	    visiblePasswordField = new TextField();
	    visiblePasswordField.setManaged(false);
	    visiblePasswordField.setVisible(false);
	    visiblePasswordField.textProperty().bindBidirectional(password.textProperty());
	    visiblePasswordField.promptTextProperty().bind(password.promptTextProperty());
	    visiblePasswordField.setStyle("-fx-background-color: transparent; -fx-border-color: #0596ff; -fx-border-width: 0px 0px 2px 0px;");
	    visiblePasswordField.setLayoutX(password.getLayoutX());
	    visiblePasswordField.setLayoutY(password.getLayoutY());
	    visiblePasswordField.setPrefSize(password.getPrefWidth(), password.getPrefHeight());
	    s.getChildren().add(visiblePasswordField);
	}

	private void togglePasswordVisibility() {
	    if (isPasswordVisible) {
	        password.setVisible(true);
	        password.setManaged(true);
	        visiblePasswordField.setVisible(false);
	        visiblePasswordField.setManaged(false);
	        isPasswordVisible = false;
	        StackPane.setAlignment(eyeImage, Pos.CENTER_RIGHT);
	    } else {
	        password.setVisible(false);
	        password.setManaged(false);
	        visiblePasswordField.setVisible(true);
	        visiblePasswordField.setManaged(true);
	        isPasswordVisible = true;
	        StackPane.setAlignment(eyeImage, Pos.CENTER_LEFT);
	    }
	}
	@FXML
	private void login() {
	    String userEmail = email.getText();
	    String userPassword = password.getText();

	    if (!userEmail.contains("@")) {
	    	alert("Invalid email address.", Alert.AlertType.ERROR);
	        return;
	    }

	    
	    Users user = new Users(userEmail, userPassword);

	    
	    boolean isAuthenticated = DaoLogin.login(user);

	    if (isAuthenticated) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/FXMLController.fxml"));
	            Parent root = loader.load();
	            FXMLControllerClient clientController = loader.getController();

	            // Access the current stage
	            Stage currentStage = (Stage) s.getScene().getWindow();

	            // Replace the content of the current scene with the content of FXMLClient.fxml
	            Scene scene = new Scene(root);
	            currentStage.setScene(scene);
	            currentStage.setTitle("Gestion Client");
	            currentStage.show();
	        } catch (IOException e) {
	        	alert("Failed to load FXMLClient.fxml", Alert.AlertType.ERROR);
	            e.printStackTrace();
	        }
	    } else {
	    	alert("Invalid email or password.", Alert.AlertType.ERROR);
	    }
	}

	private void alert(String message, Alert.AlertType alertType) {
	    Alert alert = new Alert(alertType, message, ButtonType.OK);
	    alert.setHeaderText("Login Error");
	    alert.setTitle("Login Error");

	    alert.setOnCloseRequest(event -> email.requestFocus());

	    
	    alert.show();
	}

	@FXML
	private void signUp() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/SignUpController.fxml"));
	        Parent root = loader.load();
	        SignUpController signUpController = loader.getController();

	        
	        Stage currentStage = (Stage) s.getScene().getWindow();

	      
	        Scene scene = new Scene(root);
	        currentStage.setScene(scene);
	        currentStage.setTitle("Sign Up");
	        currentStage.show();
	    } catch (IOException e) {
	        alert("Failed to load SignUp.fxml", Alert.AlertType.ERROR);
	        e.printStackTrace();
	    }
	}

	 @FXML
		public void forget(ActionEvent event) {
	    	 try {
	             FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/forgetpw.fxml"));
	             Parent root = loader.load();
	             ForgetPW ForgetPW = loader.getController();

	 	        
	 	        Stage currentStage = (Stage) s.getScene().getWindow();

	 	      
	 	        Scene scene = new Scene(root);
	 	        currentStage.setScene(scene);
	 	        currentStage.setTitle("forget");
	 	        currentStage.show();
	 	    } catch (IOException e) {
	 	        alert("Failed to load forgetpw.fxml", Alert.AlertType.ERROR);
	 	        e.printStackTrace();
	 	    }
	     }
	 private void showAlert(String message, Alert.AlertType alertType) {
			Alert alert = new Alert(alertType);
			alert.setHeaderText(null);
			alert.setContentText(message);
			alert.showAndWait();
		}
}
/*
private void showAlert(String message, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
*/