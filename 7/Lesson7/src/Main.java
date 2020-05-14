import com.sbt.school.classloaders.ClassLoaderWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {


    /**
     * args: 0. DesiredClass
     * 1. DesiredMethod
     * 2. List of folders
     */

    public static void main(String[] args) {
        System.out.println("ClassLoaders HW");

        ClassLoaderWrapper wrapper = new ClassLoaderWrapper();

        if (args.length < 3) {
            throw new IllegalArgumentException("\nInput values format: java Main.java [DesiredClass] [DesiredMethod] [ListOfFolders]");
        }

        //todo add parser for parameters
        String desiredClass = args[0];
        String desiredMethod = args[1];

        try {
            List<String> paths = new ArrayList<>(args.length - 2);
            paths.addAll(Arrays.asList(args).subList(2, args.length));

            Class<?> classWithMethod = wrapper.findClassWithMethod(desiredClass, desiredMethod, paths);


            Method m = classWithMethod.getDeclaredMethod(desiredMethod);
            System.out.println(m.getName());
            m.invoke(classWithMethod.getDeclaredConstructor().newInstance());

        } catch (FileNotFoundException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

    }

}
