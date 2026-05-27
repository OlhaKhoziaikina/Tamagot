package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class EssenAnimationController implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private ImageView backgroundEssen;
	@FXML
	private ImageView chickenLegImageView;
	@FXML
	private ImageView foxEatsImageView;

	Image chickenLeg = new Image(getClass().getResource("/Bilder/chicken_leg.png").toExternalForm());
	Image chickenLeg2 = new Image(getClass().getResource("/Bilder/chicken_leg_2.png").toExternalForm());
	Image chickenLeg3 = new Image(getClass().getResource("/Bilder/chicken_leg_3.png").toExternalForm());
	Image eatState1 = new Image(getClass().getResource("/Bilder/eatState1.png").toExternalForm());
	Image eatState2 = new Image(getClass().getResource("/Bilder/eatState2.png").toExternalForm());
	Image eatState0 = new Image(getClass().getResource("/Bilder/eatState4.png").toExternalForm());

	public void returnToMainScreen() throws IOException {
		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
		stage = (Stage) backgroundEssen.getScene().getWindow();
		scene = new Scene(root);
		
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		backgroundEssen.setImage(new Image(getClass().getResource("/Bilder/LittlePinkForest.jpg").toExternalForm()));
		
		chickenLegImageView.setImage(chickenLeg);
		foxEatsImageView.setImage(eatState0);
		Timeline essenTimeline = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>() {
			int i = 0;
			@Override
			public void handle(ActionEvent arg0) {
				
				if(foxEatsImageView.getImage() == eatState2 || foxEatsImageView.getImage() == eatState0) {
					foxEatsImageView.setImage(eatState1);
				} else {
					foxEatsImageView.setImage(eatState2);
					i+=1;
					System.out.println("kus"+i);
				}	
				if(i==1) {
					chickenLegImageView.setImage(chickenLeg2);
				} else if(i>1) {
					chickenLegImageView.setImage(chickenLeg3);
				}
			}
		}
				));
		essenTimeline.setCycleCount(4);
		essenTimeline.play();
		essenTimeline.setOnFinished(e -> {
	        PauseTransition pause = new PauseTransition(Duration.seconds(1));
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
