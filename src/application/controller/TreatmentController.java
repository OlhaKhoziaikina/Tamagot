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

public class TreatmentController implements Initializable{
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private ImageView background, perImageView, medImageView;
	
	Image backgrnd = new Image(getClass().getResource("/Bilder/LittlePinkForest.jpg").toExternalForm());
	Image doc1 = new Image(getClass().getResource("/Bilder/byDoctor1.png").toExternalForm());
	Image doc2 = new Image(getClass().getResource("/Bilder/byDoctor2.png").toExternalForm());
	Image medicine = new Image(getClass().getResource("/Bilder/cactus_med.png").toExternalForm());
	
	public void returnToMainScreen() throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
		stage = (Stage) background.getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setImage(backgrnd);
		perImageView.setImage(doc1);
		medImageView.setImage(medicine);
		Timeline treat = new Timeline(new KeyFrame(Duration.millis(750), new EventHandler<ActionEvent>() {
			boolean b = true;
			@Override
			public void handle(ActionEvent event) {
				if(b) {
					perImageView.setImage(doc2);
				} else {
					perImageView.setImage(doc1);
				}
				b = !b;
			}
		}));
		treat.setCycleCount(4);
		treat.setOnFinished(e -> {
			try {
                returnToMainScreen();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
		});
		treat.play();
	}
}
