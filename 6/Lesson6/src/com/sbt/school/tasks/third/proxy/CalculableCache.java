package com.sbt.school.tasks.third.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class CalculableCache implements InvocationHandler {

    private final Calculable<?> delegate;
    private HashMap<List<CalcValue<?>>, CalcValue<?>> cache;

    public CalculableCache(Calculable<?> delegate) {
        this.delegate = delegate;
        this.cache = new HashMap<>();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Optional<Map.Entry<List<CalcValue<?>>, CalcValue<?>>> result =
                cache.entrySet().stream().filter(m -> m.getKey().containsAll(Arrays.asList(args))).findFirst();

        if (result.isPresent()) {
            System.out.println("from cache!!!");
            return result.get().getValue();
        }

        List<CalcValue<?>> calcValues;
        calcValues = Arrays.stream(args).map(o -> (CalcValue<?>)o).collect(Collectors.toList());

        HashMap.SimpleEntry<List<CalcValue<?>>, CalcValue<?>> entry =
                new HashMap.SimpleEntry<>(calcValues, (CalcValue<?>) method.invoke(delegate, args));

        cache.put(entry.getKey(), entry.getValue());

        return method.invoke(delegate, args);
    }
}
