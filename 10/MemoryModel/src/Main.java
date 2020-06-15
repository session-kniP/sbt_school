import com.sbt.school.manager.Context;
import com.sbt.school.manager.ExecutionManager;
import com.sbt.school.manager.ExecutionManagerImpl;
import com.sbt.school.manager.RunnableTask;
import com.sbt.school.task.Task;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class Main {

    static Task<String> task;


    // !execution manager tests with exception by design!
    public static void main(String[] args) throws InterruptedException {
        System.out.println("------------Testing first task-------------\n");
        testTask();

        System.out.println("\n------------Testing second task-------------\n");
        testExecutionManager();
        return;
    }


    private static void testTask() throws InterruptedException {
        //correct
        Callable<String> c = () -> {
            Thread.sleep(3000);
            return Thread.currentThread().getName() + " ended task!!!";
        };

        task = new Task<>(c);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> task.get()).start();
        }

        Thread.sleep(15000);
        System.out.println("\n\n");

        //with exception
        c = () -> {
            Thread.sleep(4000);
            throw new RuntimeException("Err", new Throwable());
        };

        task = new Task<>(c);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> task.get()).start();
        }

        Thread.sleep(15000);
        System.out.println("\n\n");
    }


    private static void testExecutionManager() throws InterruptedException {
        RunnableTask<String> task = new RunnableTask<>(() -> {
            try {
                System.out.println("TASK1 - Executing something... " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Exception While Sleeping", e);
                }
                return "Success at TASK1 " + Thread.currentThread().getName();
            } catch (RuntimeException e) {
                throw new RuntimeException("Exception while executing custom task", e);
            }

        });

        RunnableTask<String> task2 = new RunnableTask<>(() -> {
            try {
                System.out.println("TASK2 - Executing something... " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                    throw new RuntimeException("RE");
                } catch (InterruptedException e) {
                    throw new RuntimeException("Exception While Sleeping", e);
                }
//                return "Success at TASK2 " + Thread.currentThread().getName();
            } catch (RuntimeException e) {
                throw new RuntimeException("Exception while executing custom task", e);
            }

        });


        RunnableTask<String> task3 = new RunnableTask<>(() -> {
            try {
                System.out.println("TASK3 - Executing something... " + Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException("Exception While Sleeping", e);
                }
                return "Success at TASK3 " + Thread.currentThread().getName();
            } catch (RuntimeException e) {
                throw new RuntimeException("Exception while executing custom task", e);
            }

        });


        RunnableTask<String>[] taskArray = new RunnableTask[]{task, task2, task3};

        Runnable callback = () -> System.out.println("And here's callback!!!");

        ExecutionManagerImpl<String> stringExecutionManager = new ExecutionManagerImpl<>(2);

        Thread.sleep(50);

        do {
            Context context = stringExecutionManager.execute(callback, taskArray);
            System.out.println(
                            "Completed " + context.getCompletedTaskCount() +
                            " | failed " + context.getFailedTaskCount() +
                            " | interrupted " + context.getInterruptedTaskCount()
            );
            Thread.sleep(1000);
            context.interrupt();
        } while (!stringExecutionManager.isFinished());

        Context context = stringExecutionManager.execute(callback, taskArray);
        System.out.println("That's the end");
        System.out.println(
                "Completed " + context.getCompletedTaskCount() +
                        " | failed " + context.getFailedTaskCount() +
                        " | interrupted " + context.getInterruptedTaskCount()
        );
    }

}
