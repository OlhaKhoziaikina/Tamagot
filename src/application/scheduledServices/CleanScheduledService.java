package application.scheduledServices;

import application.model.Tamagot;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class CleanScheduledService extends ScheduledService<Integer>{
	private Tamagot fox;
	public CleanScheduledService(Tamagot fox) {
		this.fox=fox;
	}
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>(){

			@Override
			protected Integer call() throws Exception {
				System.out.println("Dirtiness increasing. Current cleanliness: " + fox.clean);
				fox.clean -=10;
				if(fox.clean <= 0) {
					fox.clean = 0;
					System.out.println("Your fox is very dirty!");
				}
				return fox.clean;
			}
			
		};
	}
	
};
