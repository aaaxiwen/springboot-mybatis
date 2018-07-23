package CompletableFutureTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FutureTest {
	public static Integer calc(Integer para) {
        try {
            // 模拟一个长时间的执行
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }
        return para * para;
    }
	
	
 
    public static void main(String[] args) throws InterruptedException,
            ExecutionException {
    	//thenApply（）转换的是泛型中的类型，是同一个CompletableFuture；
    	//thenCompose（）用来连接两个CompletableFuture，是生成一个新的CompletableFuture。
        CompletableFuture<Void> fu = CompletableFuture
                .supplyAsync(() -> calc(50))
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        System.out.println("异步处理");
        fu.get();
    }
}
