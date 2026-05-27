package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerScene_test implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	Circle myCircle = new Circle();
	private double x;
	private double y;
	
	public void btnHi(ActionEvent e) {
		System.out.println("Hello");
		myCircle.setCenterX(x+=5);
		myCircle.setCenterY(y+=5);
	}
	public void switchToGame(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Game.fxml"));
		stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private Button exitBtn;
	@FXML
	private AnchorPane scene1pane;
	
	public void exit(ActionEvent event) {
		stage = (Stage) scene1pane.getScene().getWindow();
		System.out.println("Bye!");
		stage.close();
	}
	
	@FXML
	private ImageView buggyUsual;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// translate
		TranslateTransition move = new TranslateTransition();
		move.setNode(buggyUsual);
		move.setDuration(Duration.millis(2000));
		move.setCycleCount(TranslateTransition.INDEFINITE);
		move.setByX(200);
		move.setByY(200);
		move.setAutoReverse(true);
		move.play();
	}
}
