import com.sbt.classes.SomeClass;
import com.sbt.classes.SomeSecClass;

public class Main {

    private static char ch = 'c';
    private static boolean b;

    private static byte by = 1;
    private static short sh;
    private static int i;
    private static long l;

    private static float f = 3.14f;
    private static double d = 2.86;

    private static SomeClass sc;

    public static void main(String[] args) {
        System.out.println(f + d);

        SomeClass innerSomeClass = new SomeClass();
        sc = innerSomeClass;

        sc.print("I'm printing tho");

        boolean dolboeb = true;

        SomeClass someClass = new SomeClass();
        SomeSecClass secClass = new SomeSecClass();

        System.out.println(dolboeb ? "Te dolboeb" : "Te not");

        if (someClass.getFalse() & secClass.getTrue()) {
            System.out.println("Okey");
        } else {
            System.out.println("No good");
        }

        Short aza = (short) 256;



    }

}
