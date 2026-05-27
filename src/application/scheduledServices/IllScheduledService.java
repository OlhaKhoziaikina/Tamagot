package application.scheduledServices;

import application.model.Tamagot;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class IllScheduledService extends ScheduledService<Integer>{
	private Tamagot fox;
	public IllScheduledService(Tamagot fox) {
		this.fox=fox;
	}
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>(){

			@Override
			protected Integer call() throws Exception {
				System.out.println("Health: " + fox.health);
				fox.health -=10;
				if(fox.health <= 0) {
					fox.health = 0;
					System.out.println("Your fox is very ill!");
				}
				return fox.health;
			}
			
		};
	}
}
