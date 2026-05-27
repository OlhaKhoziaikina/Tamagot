package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class sayNoController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	Image backgrnd = new Image(getClass().getResource("/Bilder/LittlePinkForest.jpg").toExternalForm());
	Image nono = new Image(getClass().getResource("/Bilder/noState.png").toExternalForm());
	Image nono2 = new Image(getClass().getResource("/Bilder/noState_2.png").toExternalForm());
	
	@FXML
	private ImageView backgroundSayNo, perImageView;
	
	public void returnToMainScreen() throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
		stage = (Stage) backgroundSayNo.getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		backgroundSayNo.setImage(backgrnd);
		perImageView.setImage(nono);
		Timeline noNo = new Timeline(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
			boolean b = true;
			@Override
			public void handle(ActionEvent event) {
				if(b) {
					perImageView.setImage(nono2);
				} else {
					perImageView.setImage(nono);
				}
				b = !b;
			}
		}));
		noNo.setCycleCount(4);
		noNo.setOnFinished(e -> {
			try {
                returnToMainScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		});
		noNo.play();
	}
	
}
