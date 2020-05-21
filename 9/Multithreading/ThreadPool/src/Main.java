import com.sbt.school.threadpool.CThreadPool;
import com.sbt.school.threadpool.FixedThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Yeeyy, Thread Pool's there");

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

//        t.start();
        System.out.println(t.getState());


        FixedThreadPool threadPool = new FixedThreadPool(4);

        Runnable task1 = () -> {
            try {
                System.out.println("Executing task1");
                Thread.sleep(10000);
                System.out.println("Task1 executed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable task2 = () -> {
            try {
                System.out.println("Executing task2");
                Thread.sleep(5000);
                System.out.println("Task2 executed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


        Runnable task3 = () -> {
            try {
                System.out.println("Executing task3");
                Thread.sleep(1000);
                System.out.println("Task3 executed");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        threadPool.start();

        threadPool.execute(task1);
        threadPool.execute(task2);
        threadPool.execute(task3);
        threadPool.execute(task2);
        threadPool.execute(task3);
        threadPool.execute(task3);




    }

}
