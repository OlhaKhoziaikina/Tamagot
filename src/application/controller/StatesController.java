package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Tamagot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class StatesController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	private Tamagot fox = Tamagot.getInstance();
	
	@FXML
	private ImageView backgroundStates;
	@FXML
	private Label lblHungerState;
	@FXML
	private Label lblHappyState;
	@FXML
	private Label lblCleanState;
	@FXML
	private Label lblHealthState;
	
	@FXML
	public void switchBackToTamagot(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		backgroundStates.setImage(new Image(getClass().getResource("/Bilder/open_book.png").toExternalForm()));
		lblHungerState.setText("Food: "+fox.food);
		lblHappyState.setText("Happiness: "+fox.happiness);
		lblCleanState.setText("Clean: "+fox.clean);
		lblHealthState.setText("Health: "+fox.health);
	}
}
