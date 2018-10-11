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
			scheduledExecutorService.schedule(myTask, 10, TimeUnit.SECONDS);//任务虽然是10秒后开始跑，但是一开始就创建了子线程去等待任务启动，此处ScheduledExecutorService默认最多创建4个子线程去跑任务，
			System.out.println("研究延迟跑线程");							//猜测任务会到缓冲队列计时而不是到子线程那才可以计时
		}
		scheduledExecutorService.shutdown();
	}

}
