package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CleaningAnimationController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private ImageView backgroundCleaning, mopImageView;
	
	Image broomCleaning = new Image(getClass().getResource("/Bilder/broom_cleaning.png").toExternalForm());
	
	public void returnToMainScreen() throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
		stage = (Stage) backgroundCleaning.getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		backgroundCleaning.setImage(new Image(getClass().getResource("/Bilder/LittlePinkForest.jpg").toExternalForm()));
		mopImageView.setImage(broomCleaning);
		Timeline cleaningTimeline = new Timeline();
		cleaningTimeline.getKeyFrames().addAll(
				new KeyFrame(
				Duration.millis(500),
				new KeyValue(mopImageView.xProperty(), 80),
				new KeyValue(mopImageView.yProperty(), 20),
				new KeyValue(mopImageView.rotateProperty(), -30)
				),
				new KeyFrame(
				Duration.millis(1000),
				new KeyValue(mopImageView.xProperty(), 150),
				new KeyValue(mopImageView.yProperty(), -20),
				new KeyValue(mopImageView.rotateProperty(), 30)
				),
				new KeyFrame(
					Duration.millis(1500),
					new KeyValue(mopImageView.xProperty(), 250),
					new KeyValue(mopImageView.yProperty(), 30),
					new KeyValue(mopImageView.rotateProperty(), -30)
				));
		cleaningTimeline.setCycleCount(2);
		cleaningTimeline.setAutoReverse(true);
		cleaningTimeline.play();
		cleaningTimeline.setOnFinished(e -> {
	        PauseTransition pause = new PauseTransition(Duration.millis(50));
	        pause.setOnFinished(event -> {
	            try {
	                returnToMainScreen();
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        });
	        pause.play();
	    });
	}
}
