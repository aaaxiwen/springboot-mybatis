package ThreadPoolExecutorTest;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleTest {
// synchronized (this) ，synchronized(对象的引用)和修饰方法synchronized  实现的效果一样  锁了一个对象 也就是说一个对象对应用一把锁
// synchronized(.class)和修饰方法 synchronized static 锁 了全局类	 所有类共用一把锁
	public static void main(String[] args) {
		ScheduleInstance scheduleInstance = ScheduleInstance.getInstace();
		ScheduledExecutorService scheduledExecutorService = scheduleInstance.getScheduledExecutorService();
		
		for(int i=0;i<15;i++) {
			MyTask myTask = new MyTask(i);
			scheduledExecutorService.schedule(myTask, 10, TimeUnit.SECONDS);
			System.out.println("研究延迟跑线程");
		}
		scheduledExecutorService.shutdown();
	}

}
