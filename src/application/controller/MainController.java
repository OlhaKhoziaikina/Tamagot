package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.model.Tamagot;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {
	private Stage stage;
	private Scene scene;
	private Parent root;

	private boolean illnessIsRunning = false;
	private boolean sadState = false;
	
	private Tamagot fox = Tamagot.getInstance();
	
	@FXML
	private ImageView background;
	@FXML
	private Button btnHungry, btnDirty, btnSad, btnIllness, btnPoo, btnPoo1;
	@FXML
	private ProgressBar progressHungry, progressClean, progressLife, progressHappy;
	@FXML
	private Button btnUpperClean, btnUpperMedicine, btnInfos, btnUpperFeed, btnUpperPlay;
	
	
	Image usual1 = new Image(getClass().getResource("/Bilder/UsualState1.png").toExternalForm());
	Image usual2 = new Image(getClass().getResource("/Bilder/UsualState2.png").toExternalForm());
	Image sad1 = new Image(getClass().getResource("/Bilder/sad1.png").toExternalForm());
	Image sad2 = new Image(getClass().getResource("/Bilder/sad2.png").toExternalForm());
//	Image sad1 = new Image(getClass().getResource("/Bilder/sad3.png").toExternalForm()); just another option of sad images
//	Image sad2 = new Image(getClass().getResource("/Bilder/sad4.png").toExternalForm());
	Image dead = new Image(getClass().getResource("/Bilder/rip.png").toExternalForm());

	@FXML
	ImageView pers = new ImageView();
	
	public void switchScene(ActionEvent event, String fxmlFile) throws IOException {
		root = FXMLLoader.load(getClass().getResource(fxmlFile));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root, 400, 300);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setResizable(false);
		stage.setScene(scene);
		stage.setTitle("Foxgotchi");
		stage.show();
	}
	
	public void switchToGame(ActionEvent event) throws IOException {
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/Game.fxml"));
	    Parent root = loader.load();
	    ControllerGame controllerGame = loader.getController();

	    // Pass the current stage and main scene to the ControllerGame
	    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene mainScene = currentStage.getScene(); // Get the current main scene

	    controllerGame.setStage(currentStage, mainScene);

	    // Set the new scene for the game
	    Scene gameScene = new Scene(root, 400, 300);
	    currentStage.setScene(gameScene);
	    currentStage.setTitle("P O N G");
	    currentStage.show();
	}
//	public void switchToGame(ActionEvent event) throws IOException {
//		switchScene(event, "/resources/Game.fxml");
//	}
//	public void switchToGame(ActionEvent event) throws IOException{
//		Group rootGroup = new Group();
//		Scene gameScene = new Scene(rootGroup);
//		Canvas canvas = new Canvas(400, 300);
//		rootGroup.getChildren().add(canvas);
//		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		stage.setScene(gameScene);
//		stage.setTitle("P O N G");
//		stage.show();
//	}
	public void switchToStates(ActionEvent event) throws IOException {
		switchScene(event, "/resources/States.fxml");
	}
	public void switchToEssen(ActionEvent event) throws IOException {
		switchScene(event, "/resources/Essen.fxml");
	}
	public void switchToClean(ActionEvent event) throws IOException {
		switchScene(event, "/resources/Cleaning.fxml");
	}
	public void switchToNo(ActionEvent event) throws IOException {
		switchScene(event, "/resources/sayNoNo.fxml");
	}
	public void switchToMed(ActionEvent event) throws IOException {
		switchScene(event, "/resources/givingMedicine.fxml");
	}
	
	@FXML
	public void feed(ActionEvent event) throws IOException {
		if (fox.food < 90) {
			switchToEssen(event);
			fox.food += 20;
			System.out.println("Fed! Current food level: " + fox.food);
		} else {
			fox.food = 100;
			System.out.println("Im not hungry");
			switchToNo(event);
		}
	}

	@FXML
	public void clean(ActionEvent event) throws IOException {
		if (fox.clean < 90) {
			switchToClean(event);
			fox.clean += 20;
			System.out.println("Cleaned! " + fox.clean);
		} else  {
			System.out.println("Its already clean here");
			switchToNo(event);
		}
	}

	@FXML
	public void givePills(ActionEvent event) throws IOException {
		if (fox.health < 90 && fox.clean > 50 && fox.food > 50) {
			switchToMed(event);
			fox.health += 20;
			System.out.println("Healthier now! " + fox.health);
		} else {
			System.out.println("I dont want");
			switchToNo(event);
		}
	}
	
	public void invisibleBtns() {
		btnHungry.setVisible(false);
		btnDirty.setVisible(false);
		btnIllness.setVisible(false);
		btnSad.setVisible(false);
		btnUpperClean.setVisible(false);
		btnUpperFeed.setVisible(false);
		btnUpperMedicine.setVisible(false);
		btnUpperPlay.setVisible(false);
	}
	private void updateButtonVisibility(int value, Button button, int thresholdLow, int thresholdHigh) {
	    if (value >= thresholdHigh) {
	        button.setVisible(false);
	    } else if (value <= thresholdLow) {
	        button.setVisible(true);
	    }
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		background.setImage(new Image(getClass().getResource("/Bilder/LittlePinkForest.jpg").toExternalForm()));
		pers.setImage(usual2);

		fox.startHungerTimer();
		fox.startCleanTimer();
		fox.startHappyTimer();

		new AnimationTimer() {
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {

				if ((fox.clean <= 30 || fox.food <= 30) && !illnessIsRunning) {
					fox.startIllnessTimer();
					illnessIsRunning = true;
				} else if (illnessIsRunning && (fox.clean > 50 && fox.food > 50)) {
					fox.stopIllnessTimer();
					illnessIsRunning = false;
				}

				if (now - lastUpdate >= 5e8) {
					progressHungry.setProgress(fox.food / 100.0);
					progressClean.setProgress(fox.clean / 100.0);
					progressLife.setProgress(fox.health / 100.0);
					progressHappy.setProgress(fox.happiness / 100.0);
					
					updateButtonVisibility(fox.food, btnHungry, 50, 60);
					updateButtonVisibility(fox.clean, btnDirty, 50, 60);
					updateButtonVisibility(fox.happiness, btnSad, 50, 60);
					updateButtonVisibility(fox.health, btnIllness, 50, 60);

					if (fox.clean >= 60) {
						btnPoo.setVisible(false);
						btnPoo1.setVisible(false);
					} else if (fox.clean <= 50 && fox.clean > 30) {
						btnPoo.setVisible(true);
						btnPoo1.setVisible(false);
					} else if (fox.clean <= 30) {
						btnPoo.setVisible(true);
						btnPoo1.setVisible(true);
					}
				}
			}
		}.start();

		AnimationTimer usualTimer = new AnimationTimer() {
			private long prevTime = 0;
			int ft = 0;

			@Override
			public void handle(long now) {
				if (prevTime == 0) {
					prevTime = now;
				}
				if (fox.food < 50 || fox.clean < 50) { // || fox.happiness < 50
					sadState = true;
				} else {
					sadState = false;
				}
				TranslateTransition move = new TranslateTransition();
				move.setNode(pers);
				move.setDuration(Duration.millis(500));
				RotateTransition rotate = new RotateTransition();
				rotate.setNode(pers);
				rotate.setDuration(Duration.millis(10));
				long dt = now - prevTime;

				if (dt > 1e9 && fox.health != 0) {
					if (ft == 0 || ft == 4) {
						rotate.setByAngle(180);
						rotate.setAxis(Rotate.Y_AXIS);
						rotate.play();
					}
					if ((pers.getImage() == usual1 || pers.getImage() == sad1) && (ft < 3)) {
						pers.setImage(sadState ? sad2 : usual2);
						move.setByX(70);
						move.setByY(70);
						move.play();
						ft = ft + 1;
						prevTime = now;
						System.out.println(ft);
					} else if ((pers.getImage() == usual2 || pers.getImage() == sad2) && (ft < 3)) {
						pers.setImage(sadState ? sad1 : usual1);
						move.setByX(70);
						move.setByY(-70);
						move.play();
						ft = ft + 1;
						prevTime = now;
						System.out.println(ft);
					} else if ((pers.getImage() == usual1 || pers.getImage() == sad1) && (ft == 3)) {
						pers.setImage(sadState ? sad2 : usual2);
						move.setByX(30);
						move.setByY(70);
						move.play();
						ft = ft + 1;
						prevTime = now;
						System.out.println(ft);
					} else if ((pers.getImage() == usual2 || pers.getImage() == sad2) && ft > 3 && ft < 8) {
						pers.setImage(sadState ? sad1 : usual1);
						move.setByX(-60);
						move.setByY(-70);
						move.play();
						ft = ft + 1;
						prevTime = now;
						System.out.println(ft);
					} else if ((pers.getImage() == usual1 || pers.getImage() == sad1) && ft > 3 && ft < 8) {
						pers.setImage(sadState ? sad2 : usual2);
						move.setByX(-60);
						move.setByY(70);
						move.play();
						ft = ft + 1;
						prevTime = now;
						System.out.println(ft);
					} else if (ft == 8) {
						ft = 0;
					}
				} else if (fox.health == 0) {
					pers.setImage(dead);
					pers.setTranslateX(0);
					pers.setTranslateY(0);
					pers.setLayoutX(150);
					pers.setLayoutY(200);
					fox.stopCleanTimer();
					fox.stopHungerTimer();
					fox.stopIllnessTimer();
					fox.stopHappyTimer();
					btnPoo.setVisible(false);
					btnPoo1.setVisible(false);
					invisibleBtns();
					btnInfos.setLayoutX(90);
					btnInfos.setLayoutY(250);
				}
			}
		};
		usualTimer.start();

	}

}
