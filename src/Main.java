import com.sbt.school.Lessons;
import javafx.util.Pair;

import java.io.IOException;

public class Main {


    //Та самая любая HelloWorld
    public static void main(String[] args) throws IOException {

        System.out.println("Hello, World!!!");  //Конечно же вывод "Hello, World на экран"

        Lessons lessons = new Lessons();    //class with tasks

//        System.out.println("Answer for task 2001: " + lessons.task2001());
//        System.out.println("Answer for task 2001: " + lessons.task2002());
//        Pair p = lessons.task2006();
//        System.out.println("Answer for task 2006: " + p.getKey() + " " + p.getValue());

//        System.out.println("Answer for task 2008: " + lessons.task2018());

        lessons.task2044();
    }


}
