import com.sbt.school.threadpool.CThreadPool;
import com.sbt.school.threadpool.FixedThreadPool;
import com.sbt.school.threadpool.ScalableThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Yeeyy, Thread Pool's there");

        System.out.println("\n========Testing FixedThreadPool============\n");

        testFixedPool();

        System.out.println("\n========Testing ScalableThreadPool============\n");

        testScalablePool();

        System.out.println("Over");

    }


    private static void testFixedPool() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        FixedThreadPool threadPool = new FixedThreadPool(2);

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
                Thread.sleep(4000);
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
        threadPool.execute(task2);
        threadPool.execute(task1);
        threadPool.execute(task3);
        threadPool.execute(task2);


        Thread.sleep(10);
        while(!threadPool.stop()) {
            Thread.sleep(10);
        }
    }



    private static void testScalablePool() throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        ScalableThreadPool threadPool = new ScalableThreadPool(2, 6);

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
                Thread.sleep(4000);
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
        threadPool.execute(task2);
        threadPool.execute(task1);
        threadPool.execute(task3);
        threadPool.execute(task2);


        Thread.sleep(10);
        while(!threadPool.stop()) {
            Thread.sleep(10);
        }
    }



}
