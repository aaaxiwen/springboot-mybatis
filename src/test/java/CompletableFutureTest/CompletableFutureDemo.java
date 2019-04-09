package CompletableFutureTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//allOf是等待所有任务完成，构造后CompletableFuture完成

//anyOf是只要有一个任务完成，构造后CompletableFuture就完成
public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Long start = System.currentTimeMillis();
        // 结果集
        List<String> list = new ArrayList<>();
        //
        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream()
                .map(integer -> CompletableFuture.supplyAsync(() -> calc(integer), executorService)
                                .thenApply(h->Integer.toString(h))//下一步处理 流
                                .whenComplete((s, e) -> {//当抛出异常
                                    System.out.println("任务"+s+"完成!result="+s+"，异常 e="+e+","+new Date());
                                    list.add(s);
                                })
                ).toArray(CompletableFuture[]::new);
        // 封装后无返回值，必须自己whenComplete()获取
        CompletableFuture.allOf(cfs).join();
        System.out.println("list="+list+",耗时="+(System.currentTimeMillis()-start));
        
        CompletableFuture<Integer> supplyAsync = CompletableFuture.supplyAsync(() ->calc(1), executorService);//supplyAsync 参数是funtion函数 并CompletableFuture 有返回值
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(new MyTask(1), executorService);//runAsync  参数是实现runable接口的 并CompletableFuture 无返回值
        //supplyAsync  我new一个thread 参数是runable 效果是没有跑run  我也不知为什么??? 加了start 就。。。突然知道了  start（）是函数方法
        //CompletableFuture.supplyAsync(() ->new Thread(new MyTask(1)).start(), executorService);
        supplyAsync.get();
        runAsync.get();
        System.out.println("异步并且能监听结果");
    }

    public static Integer calc(Integer i) {
        try {
            if (i == 1) {
                Thread.sleep(3000);//任务1耗时3秒
            } else if (i == 5) {
                Thread.sleep(5000);//任务5耗时5秒
            } else {
                Thread.sleep(1000);//其它任务耗时1秒
            }
            System.out.println("task线程：" + Thread.currentThread().getName()
                    + "任务i=" + i + ",完成！+" + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}
