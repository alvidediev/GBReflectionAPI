package ReflectionClasses;

import Annotations.AfterSuite;
import Annotations.BeforeSuite;
import Annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;

public class ReflectionTestClass {
    static Object reflectionInstance;

    public static void start(Class clazz) {
        Method beforeAnnotate = null;
        Method afterAnnotate = null;
        ArrayList<Method> methods = new ArrayList<>();

        if (!isBeforeAndAfterAnnotationsSingle(clazz)) {
            throw new RuntimeException("Более одной аннотации @BeforeSuite или @AfterSuite");
        }

        try {
            reflectionInstance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method methodsForArrayList : declaredMethods) {
            if (methodsForArrayList.getAnnotation(BeforeSuite.class) != null) {
                beforeAnnotate = methodsForArrayList;
            } else if (methodsForArrayList.getAnnotation(AfterSuite.class) != null) {
                afterAnnotate = methodsForArrayList;
            } else if (methodsForArrayList.getAnnotation(Test.class) != null) {
                methods.add(methodsForArrayList);
            }
        }

        methods.sort(Comparator.comparingInt(O -> O.getAnnotation(Test.class).priority()));
        if (beforeAnnotate != null) {
            methods.add(0, beforeAnnotate);
        }
        if (afterAnnotate != null) {
            methods.add(afterAnnotate);
        }
        for (Method tests : methods) {
            if (Modifier.isPrivate(tests.getModifiers())) {
                tests.setAccessible(true);
            }
        }
        try {
            methods.get(0).invoke(reflectionInstance);
            for (int i = 1; i < methods.size() - 1; i++) {
                methods.get(i).invoke(reflectionInstance, "Hallo, Sergey! Test " +
                        "case passed i hope");
            }
            methods.get(methods.size() - 1).invoke(reflectionInstance);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static boolean isBeforeAndAfterAnnotationsSingle(Class clazz) {
        int beforeAnnotationCounter = 0;
        int afterAnnotationCounter = 0;
        final Method[] declaredMethods = clazz.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (method.getAnnotation(BeforeSuite.class) != null) {
                beforeAnnotationCounter++;
            }
            if (method.getAnnotation(AfterSuite.class) != null) {
                afterAnnotationCounter++;
            }
        }
        return (beforeAnnotationCounter < 2 && afterAnnotationCounter < 2);
    }
}
