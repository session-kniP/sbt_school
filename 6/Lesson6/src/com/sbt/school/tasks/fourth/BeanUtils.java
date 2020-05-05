package com.sbt.school.tasks.fourth;

import com.sbt.school.tasks.fourth.features.TechnicalFeature;

import javax.naming.directory.NoSuchAttributeException;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeanUtils {
    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method[] getters = Arrays.stream(from.getClass().getMethods()).filter(m -> m.getName().startsWith("get")).collect(Collectors.toList()).toArray(new Method[0]);

        Method[] setters = Arrays.stream(to.getClass().getMethods()).filter(m -> m.getName().startsWith("set")).collect(Collectors.toList()).toArray(new Method[0]);


        for (Method getter : getters) {
            for (Method setter : setters) {
                if (getter.getName().substring(3).equals(setter.getName().substring(3))) {
                    Class superClass = to.getClass();
                    Object result = from.getClass().getMethod(getter.getName()).invoke(from);

                    do {
                        try {
                            Method declaredMethod = superClass.getMethod(setter.getName(), getter.getReturnType());
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(to, result);

                            break;

                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    } while ((superClass = superClass.getSuperclass()) != null);
                }
            }
        }

    }


}
