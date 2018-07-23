package CompletableFutureTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AskThread implements Runnable {
    CompletableFuture<Integer> re = null;
 
    public AskThread(CompletableFuture<Integer> re) {
        this.re = re;
    }
 
    @Override
    public void run() {
        int myRe = 0;
        try {
            myRe = re.get() * re.get();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.out.println(myRe);
    }
 
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	//runnable接口方法 中用completableFuture 可以实现子线程计算 值要是没有就等有值才出结果 等待complete（20）
        final CompletableFuture<Integer> future = new CompletableFuture<Integer>();
        new Thread(new AskThread(future)).start();
        // 模拟长时间的计算过程
        Thread.sleep(10000);
        System.out.println("是异步的，等下回调");
        // 告知完成结果
//        System.out.println(future.get());
        future.complete(20);
    }
}
