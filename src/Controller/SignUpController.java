package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import DAO.DAOSignUp;
import Classes.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

public class SignUpController implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
  

    @FXML
    private TextField IDField;

    @FXML
    private Button signUpButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button backButton;

    @FXML
    AnchorPane s;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize any necessary components or logic here
    }

    @FXML
    private void signUp(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int id = Integer.parseInt(IDField.getText());

      
        Users newUser = new Users(id, username, email, password);

        
        DAOSignUp.ajouteUser(newUser);
    }
    @FXML
    private void backToLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();

            Stage currentStage = (Stage) s.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Login");
            currentStage.show();
        } catch (IOException e) {
            showAlert("Failed to load login.fxml", Alert.AlertType.ERROR);
            System.out.println("Failed to load login.fxml");
        }
    }

    private void showAlert(String message, Alert.AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
