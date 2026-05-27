package application.scheduledServices;

import application.model.Tamagot;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class HungerScheduledService extends ScheduledService<Integer>{
	private Tamagot fox;
	public HungerScheduledService(Tamagot fox) {
		this.fox=fox;
	}
	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>(){

			@Override
			protected Integer call() throws Exception {
				System.out.println("Hunger increasing. Current food: " + fox.food);
				fox.food -=10;
				if(fox.food <= 0) {
					fox.food = 0;
					System.out.println("Your fox is very hungry!");
				}
				return fox.food;
			}
			
		};
	}
	
};

