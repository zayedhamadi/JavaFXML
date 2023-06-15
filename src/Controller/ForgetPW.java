package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import Classes.Users;

import DAO.*;


public class ForgetPW {
    @FXML
    private TextField passtxt;
    @FXML
    private TextField id;
    @FXML
    private TextField usernametxt;
    @FXML
    private TextField Email;
    @FXML
    private Button searchBtn;
    @FXML
    private Label idLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    AnchorPane s;
    public void searchPsw() {
        String idText = id.getText();
        int userId = Integer.parseInt(idText);

        Users user = new Users(userId, "", "");
        DAO.ForgetPW.chercherId(user);

        usernametxt.setText(user.getUsername());
        Email.setText(user.getEmail());
        passtxt.setText(user.getPassword());
    }

   
    @FXML
    public void retrivePsw() {
        // TODO: Implement the logic to retrieve the password based on the ID
    }

    @FXML
	public void backLogin(ActionEvent event) {
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
