package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControllerGame implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Timeline tl;

    // game constants
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int PLAYER_HEIGHT = 70;
    private static final int PLAYER_WIDTH = 10;
    private static final double BALL_R = 15;

    // game state
    private double ballXPos = WIDTH / 2;
    private double ballYPos = HEIGHT / 2;
    private int ballXSpeed = 2;
    private int ballYSpeed = 2;

    private double player1YPos = HEIGHT / 2;
    private double player2YPos = HEIGHT / 2;

    private final double player1XPos = 0;
    private final double player2XPos = WIDTH - PLAYER_WIDTH;

    private int scoreP1 = 0;
    private int scoreP2 = 0;

    private boolean gameStarted = false;

    @FXML
    private Canvas canvas;

    @FXML
    private StackPane sp;

    public void setStage(Stage stage, Scene scene) {
        this.stage = stage;
        this.scene = scene;
    }

    // ---------------- SCENE SWITCH ----------------

    private void stopGame() {
        if (tl != null) {
            tl.stop();
            tl = null;
        }
    }

    public void switchBack(ActionEvent event) throws IOException {
        stopGame();

        root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/application/application.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Foxgotchi");
        stage.show();
    }

    public void switchBack2() throws IOException {
        stopGame();

        if (stage != null && scene != null) {
            stage.setScene(scene);
            stage.setTitle("Foxgotchi");
            stage.show();
        }
    }

    public void switchBackToTamagot(ActionEvent event) throws IOException {
        stopGame();

        root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        scene = new Scene(root);
        scene.getStylesheets().add(
                getClass().getResource("/application/application.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.setTitle("Foxgotchi");
        stage.show();
    }
    
    private void goToMainMenu() {
        stopGame();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
            Stage stage = (Stage) canvas.getScene().getWindow();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(
                    getClass().getResource("/application/application.css").toExternalForm()
            );

            stage.setScene(scene);
            stage.setTitle("Foxgotchi");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- INIT ----------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();

        canvas.setOnMouseMoved(e -> player1YPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
    }

    // ---------------- GAME LOOP ----------------

    private void run(GraphicsContext gc) {

        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));

        if (gameStarted) {

            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;

            // AI
            if (ballXPos < WIDTH - WIDTH / 4) {
                player2YPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                player2YPos += (ballYPos > player2YPos + PLAYER_HEIGHT / 2) ? 2 : -2;
            }

        } else {
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("Click to start", WIDTH / 2, HEIGHT / 2);

            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;

            ballXSpeed = new Random().nextBoolean() ? 2 : -2;
            ballYSpeed = new Random().nextBoolean() ? 2 : -2;
        }

        // wall bounce
        if (ballYPos <= 0 || ballYPos >= HEIGHT) {
            ballYSpeed *= -1;
        }

        // scoring
        if (ballXPos < player1XPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }

        if (ballXPos > player2XPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }

        // collision
        boolean hitLeft = (ballXPos <= player1XPos + PLAYER_WIDTH)
                && ballYPos >= player1YPos
                && ballYPos <= player1YPos + PLAYER_HEIGHT;

        boolean hitRight = (ballXPos + BALL_R >= player2XPos)
                && ballYPos >= player2YPos
                && ballYPos <= player2YPos + PLAYER_HEIGHT;

        if (hitLeft || hitRight) {
            ballXSpeed *= -1;
            ballYSpeed *= -1;

            ballXSpeed += Math.signum(ballXSpeed);
            ballYSpeed += Math.signum(ballYSpeed);
        }

        // draw objects
        gc.fillText(scoreP1 + "   :   " + scoreP2, WIDTH / 2, 30);

        gc.fillRect(player1XPos, player1YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        gc.fillRect(player2XPos, player2YPos, PLAYER_WIDTH, PLAYER_HEIGHT);

        gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);

        // stop condition (optional)
        if (scoreP1 >= 3 || scoreP2 >= 3) {
            stopGame();
            gameStarted = false;
            goToMainMenu();
        }
    }
}










//package application.controller;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.Random;
//import java.util.ResourceBundle;
//
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.canvas.Canvas;
//import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import javafx.scene.text.TextAlignment;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//public class ControllerGame implements Initializable {
//	
//	private Stage stage;
//	private Scene scene;
//	private Parent root;
//	private boolean tlBeenden = false;
//
//	private static final int width = 400;
//	private static final int height = 300;
//	private static final int PLAYER_HEIGHT = 70;
//	private static final int PLAYER_WIDTH = 10;
//	private static final double BALL_R = 15;
//	private int ballXSpeed = 1;
//	private int ballYSpeed = 1;
//	private double player1YPos = height / 2;
//	private double player2YPos = height / 2;
//	private double ballXPos = width / 2;
//	private double ballYPos = width / 2;
//	private int scoreP1 = 0;
//	private int scoreP2 = 0;
//	private boolean gameStarted;
//	private double player1XPos = 0;
//	private double player2XPos = width - PLAYER_WIDTH;
//
//	@FXML
//	private Canvas canvas;
//	@FXML
//	private StackPane sp;
//	public void setStage(Stage stage, Scene scene) {
//        this.stage = stage;
//        this.scene = scene;
//    }
//	
//	public void switchBackToTamagot(ActionEvent event) throws IOException {
//		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
//		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//		scene = new Scene(root);
//		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
//		stage.setScene(scene);
//		stage.setTitle("Foxgotchi");
//		stage.show();
//	}
//	public void switchBack(ActionEvent event) throws IOException {
//		root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
//		stage = (Stage) sp.getScene().getWindow();
//		scene = new Scene(root);
////		sp.getChildren().add(canvas);
////		scene.setRoot(sp);
//		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
//		stage.setScene(scene);
//		stage.setTitle("Foxgotchi");
//		stage.show();
//	}
//	@FXML
//	public void switchBack2() throws IOException {
//		if (stage != null) {
//            stage.setScene(scene);
//            stage.setTitle("Foxgotchi");
//            stage.show();
//        } else {
//        	System.err.println("Stage is null, cannot switch scene.");
//        }
//    }
//	
////	public void switchBack2() throws IOException {
////
////	    root = FXMLLoader.load(getClass().getResource("/resources/Main.fxml"));
////
////	    stage = (Stage) sp.getScene().getWindow();
////
////	    scene = new Scene(root);
////
////	    scene.getStylesheets().add(
////	        getClass().getResource("/application/application.css").toExternalForm()
////	    );
////
////	    stage.setScene(scene);
////	    stage.setTitle("Foxgotchi");
////	    stage.show();
////	}
//
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		
//		GraphicsContext gc = canvas.getGraphicsContext2D();
//		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc, e)));
//		tl.setCycleCount(Timeline.INDEFINITE);
//		// mouse control
//		canvas.setOnMouseMoved(e -> player1YPos = e.getY());
//		canvas.setOnMouseClicked(e -> gameStarted = true);
//		tl.play();
//		System.out.println("Canvas: " + canvas);
//		System.out.println("Scene: " + (canvas != null ? canvas.getScene() : "null"));
//		System.out.println("Stage: " + (canvas.getScene() != null ? canvas.getScene().getWindow() : "null"));
//		if(tlBeenden) {
//			tl.stop();
//		}
//	}
//
//	private void run(GraphicsContext gc, ActionEvent event) {
//		// set background color
//		gc.setFill(Color.BLACK);
//		gc.fillRect(0, 0, width, height);
//
//		// set text color
//		gc.setFill(Color.WHITE);
//		gc.setFont(Font.font(25));
//
//		if (gameStarted) {
//			
//			// set ball movement
//			ballXPos += ballXSpeed;
//			ballYPos += ballYSpeed;
//
//			// computer player
//			if (ballXPos < width - width / 4) {
//				player2YPos = ballYPos - PLAYER_HEIGHT / 2;
//			} else {
//				player2YPos = ballYPos > player2YPos + PLAYER_HEIGHT / 2 ? player2YPos += 1 : player2YPos - 1;
//			}
//
//			// ball
//			gc.fillOval(ballXPos, ballYPos, BALL_R, BALL_R);
//		} else {
//			//start text
//			gc.setStroke(Color.WHITE);
//			gc.setTextAlign(TextAlignment.CENTER);
//			gc.strokeText("Game starts on Click", width/2, height/2);
//			//reset the ball start position
//			ballXPos = width/2;
//			ballYPos = height/2;
//			
//			//reset speed and direction
//			ballXSpeed = new Random().nextInt(2) == 0 ? 1:-1;
//			ballYSpeed = new Random().nextInt(2) == 0 ? 1:-1;
//		}
//		//ball stays on canvas
//		if (ballYPos > height || ballYPos < 0) ballYSpeed *=-1;
//		
//		//computer gets a point
//		if(ballXPos < player1XPos - PLAYER_WIDTH) {
//			scoreP2++;
//			gameStarted = false;
//		}
//		
//		//player gets a point
//		if(ballXPos > player2XPos + PLAYER_WIDTH) {
//			scoreP1++;
//			gameStarted = false;
//		}
//		
//		
//		//increase the ball speed
//		if( ((ballXPos + BALL_R > player2XPos) && ballYPos >= player2YPos && ballYPos <= player2YPos + PLAYER_HEIGHT) ||
//		((ballXPos < player1XPos + PLAYER_WIDTH) && ballYPos >= player1YPos && ballYPos <= player1YPos + PLAYER_HEIGHT)) {
//			ballYSpeed += 1*Math.signum(ballYSpeed);
//			ballXSpeed += Math.signum(ballXSpeed);
//			ballXSpeed *= -1;
//			ballYSpeed *= -1;
//		}
//		
//		//draw score
//		gc.fillText(scoreP1 + "\t\t\t\t\t\t"	+ scoreP2, width/2, 70);
//		
//		//draw players
//		gc.fillRect(player1XPos, player1YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
//		gc.fillRect(player2XPos, player2YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
//		// Check if any player has reached score of 5
//	    if (scoreP1 == 1 || scoreP2 == 1) {
//	    	tlBeenden = true;
//	        try {
//	            switchBack2();
//	            return; // Stop further execution of the game loop
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
//	    }
//
//	}
//}
