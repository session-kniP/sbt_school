import com.sbt.school.classloaders.input.Flag;
import com.sbt.school.classloaders.input.FlagList;
import com.sbt.school.classloaders.input.InputParameters;
import com.sbt.school.classloaders.parser.TerminalParameter;
import com.sbt.school.classloaders.parser.TerminalParameterGroup;
import com.sbt.school.classloaders.wrapper.ClassLoaderWrapper;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {


    /**
     * args: 0. DesiredClass
     * 1. DesiredMethod
     * 2. List of folders
     */

    public static void main(String[] args) {
        System.out.println("ClassLoaders HW");
        
        FlagList flagList = new FlagList();
        flagList.addFlag(new Flag("h", "Help", Flag.Type.HELP));
        flagList.addFlag(new Flag("c", "ClassName", Flag.Type.CLASS_NAME));
        flagList.addFlag(new Flag("m", "MethodName", Flag.Type.METHOD_NAME));
        flagList.addFlag(new Flag("a", "Args", Flag.Type.ARGS));
        flagList.addFlag(new Flag("p", "Path", Flag.Type.PATH));

        InputParameters parameters = new InputParameters(args, flagList);

        try {
            parameters.checkParameters();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            printHelp(flagList);
            return;
        }

        ClassLoaderWrapper wrapper = new ClassLoaderWrapper();

        try {


            TerminalParameterGroup classNameGroup = parameters.getGroup(Flag.Type.CLASS_NAME);
            TerminalParameterGroup methodNameGroup = parameters.getGroup(Flag.Type.METHOD_NAME);
            TerminalParameterGroup argsGroup = parameters.getGroup(Flag.Type.ARGS);
            TerminalParameterGroup pathGroup = parameters.getGroup(Flag.Type.PATH);

            Class<?>[] classesWithMethod = new Class<?>[classNameGroup.getParameters().size()];

            if (classesWithMethod.length > 1) {
//                for()


            } else {
                classesWithMethod[0] = wrapper.findClassWithMethod(
                        classNameGroup.getParameters().get(0).getValue(),
                        methodNameGroup.getParameters().get(0).getValue(),
                        pathGroup.getStringParameters());
            }


            for (Class<?> c : classesWithMethod) {
                for (String methodName : methodNameGroup.getStringParameters()) {
                    Method m = c.getDeclaredMethod(methodName);
                    System.out.println(String.format("--From class '%s' executing method '%s'--", c.getName(), m.getName()));
                    m.invoke(c.getDeclaredConstructor().newInstance());
                    System.out.println("!--'" + m.getName() + "' Execution ends--");
                }
            }

        } catch (FileNotFoundException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

    }


    public static void printHelp(FlagList flagList) {
        System.out.println("Input values format: java Main.java [[flag][args...]...]");
        System.out.println("Flags allowed:");

        for (Flag f : flagList.getFlags()) {
            System.out.println(f);
        }

    }

}
