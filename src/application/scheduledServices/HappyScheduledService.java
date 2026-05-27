package application.scheduledServices;

import application.model.Tamagot;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class HappyScheduledService extends ScheduledService<Integer>{
	private Tamagot fox;
	public HappyScheduledService(Tamagot fox) {
		this.fox=fox;
	}
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>(){

			@Override
			protected Integer call() throws Exception {
				System.out.println("I want to play. Current happiness: " + fox.happiness);
				fox.happiness -=10;
				if(fox.happiness <= 0) {
					fox.happiness = 0;
					System.out.println("Your fox is sad!");
				}
				return fox.happiness;
			}
			
		};
	}
	
};
