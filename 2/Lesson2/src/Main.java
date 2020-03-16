import com.sbt.school.tasks.Tasks;
import com.sbt.school.tasks.Test;

import java.io.IOException;

public class Main {

    private static String filename = "unique_file.txt";

    public static void main(String[] args) throws IOException {

        Test.foo();

        if(true) return;

        System.out.println("Different words count is " + Tasks.task1(filename));
        Tasks.task2(filename);
        Tasks.task3(filename);
        Tasks.task4_1(filename);
        Tasks.task4_2(filename);
        Tasks.task6(filename);
    }

}
