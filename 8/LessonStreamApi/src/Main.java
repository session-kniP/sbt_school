import com.sbt.school.cstream.CStream;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {


    public static void main(String[] args) {

//        List<String> stringList = new ArrayList<>();
//        stringList.add("Average");
//        stringList.add("Hah");
//        stringList.add("baba");
//        stringList.add("Ava");
//        stringList.add("Arman");
//        stringList.add("Ab");
//        stringList.add("Lol");
//        stringList.add("Keke");


        String runHobby = "Run";
        Set<TestClass> testClasses = new HashSet<>();

        testClasses.add(new TestClass(12, "John", runHobby, "Music"));
        testClasses.add(new TestClass(23, "Rob", runHobby, "Kek", "Movies"));
        testClasses.add(new TestClass(25, "Matt", "Kek", "Movies", "Swimming"));
        testClasses.add(new TestClass(65, "William", runHobby, "Computer Games", "Movies"));
        testClasses.add(new TestClass(14, "Frank", "Kek", "Movies"));
        testClasses.add(new TestClass(33, "Tom", runHobby, "Movies", "Swimming"));
        testClasses.add(new TestClass(30, "Henry", runHobby));


        CStream<TestClass> stream = CStream.of(testClasses);

        Map<String, Integer> streamMap = stream
                .filter(el -> el.getAge() > 15 && el.getAge() < 60)
                .filter(el -> el.getHobbies().contains(runHobby))
                .transform(el -> new TestClass(el.getAge(), "Mr(Ms/Mrs) " + el.getName(), el.getHobbies()))
                .toMap(TestClass::getName, TestClass::getAge);


        streamMap.forEach((k, v) -> System.out.println(k + " | " + v));


    }

}
