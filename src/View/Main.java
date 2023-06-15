package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import DAO.*;
import View.*;
public class Main extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		Scene scene = new Scene(root);
		 scene.getStylesheets().add(getClass().getResource("facture.css").toExternalForm());
		stage.setScene(scene);
		stage.setTitle("------------Gestion des clients------------");
		stage.show();
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		launch(args);

	}

}	