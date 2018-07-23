package ThreadPoolExecutorTest;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class ScheduleInstance {
	private ScheduledExecutorService scheduledExecutorService;
	private ScheduledFuture<?>scheduledFuture;
	
	private ScheduleInstance(){
		this.scheduledExecutorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		
	}
	private static class SingletonHolder{
		static final ScheduleInstance INSTANCE = new ScheduleInstance();
	}
	
	public static ScheduleInstance getInstace() {
		return SingletonHolder.INSTANCE;
	}

	public ScheduledExecutorService getScheduledExecutorService() {
		return scheduledExecutorService;
	}

	
	
}
