import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestClass {

    private int age;
    private String name;
    private List<String> hobbies;


    public TestClass(int age, String name, String...hobbies) {
        this.age = age;
        this.name = name;

        if(hobbies.length > 0) {
            this.hobbies = Arrays.asList(hobbies);
        } else {
            this.hobbies = new ArrayList<>();
        }
    }

    public TestClass(int age, String name, List<String> hobbies) {
        this.age = age;
        this.name = name;

        this.hobbies = hobbies;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }
}
