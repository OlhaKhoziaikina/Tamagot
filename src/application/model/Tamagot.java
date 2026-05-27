package application.model;

import application.scheduledServices.CleanScheduledService;
import application.scheduledServices.HappyScheduledService;
import application.scheduledServices.HungerScheduledService;
import application.scheduledServices.IllScheduledService;
import javafx.util.Duration;

public class Tamagot {
	private static Tamagot instance;
	private HungerScheduledService hungerService;
	private CleanScheduledService cleanService;
	private IllScheduledService illnessService;
	private HappyScheduledService happyService;
	public int food;
	public int clean;
	public int health;
	public int happiness;
	
	public static Tamagot getInstance() {
        if (instance == null) {
            instance = new Tamagot(100, 100, 100, 100);
        }
        return instance;
    }
	public Tamagot(int food, int clean, int health, int happiness) { //Durations here to change time of the timers
		this.food=food;
		this.clean=clean;
		this.health=health;
		this.happiness=happiness;
		this.hungerService = new HungerScheduledService(this);
		this.hungerService.setPeriod(Duration.seconds(10));
		this.cleanService = new CleanScheduledService(this);
		this.cleanService.setPeriod(Duration.seconds(15));
		this.illnessService = new IllScheduledService(this);
		this.illnessService.setPeriod(Duration.seconds(10));
		this.happyService = new HappyScheduledService(this);
		this.happyService.setPeriod(Duration.seconds(15));
		System.out.println("new one fox");
	}

	public void startHungerTimer() {
		if(hungerService.getState() == javafx.concurrent.Worker.State.READY ||
		    hungerService.getState() == javafx.concurrent.Worker.State.FAILED){
			hungerService.start();
		}
	}
	public void stopHungerTimer() {
		hungerService.cancel();
	}
	public void startCleanTimer() {
		if(cleanService.getState() == javafx.concurrent.Worker.State.READY ||
			cleanService.getState() == javafx.concurrent.Worker.State.FAILED) {
			cleanService.start();
			}
		}
	
	public void stopCleanTimer() {
		cleanService.cancel();
	}
	public void startIllnessTimer() {
		if(illnessService.getState() == javafx.concurrent.Worker.State.READY ||
			illnessService.getState() == javafx.concurrent.Worker.State.FAILED){
			illnessService.start();
		} else if (illnessService.getState() == javafx.concurrent.Worker.State.CANCELLED){
            illnessService.reset();
            illnessService.start();
        }
	}
	public void stopIllnessTimer() {
		illnessService.cancel();
	}
	public void startHappyTimer() {
		if(happyService.getState() == javafx.concurrent.Worker.State.READY ||
		    happyService.getState() == javafx.concurrent.Worker.State.FAILED){
			happyService.start();
		}
	}
	public void stopHappyTimer() {
		happyService.cancel();
	}
	@Override
	public String toString() {
		return "Tamagot: food=" + food + ", clean="+clean;
	}
	
}
