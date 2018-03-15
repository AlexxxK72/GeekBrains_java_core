import AnnoTest.AfterSuite;
import AnnoTest.BeforeSuite;
import AnnoTest.Test;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class UnitTest {
    public static void start(Class classTest){
        testProcess(classTest);
    }

    public static void start(String className){
        try {
            Class classTest = Class.forName(className);
            testProcess(classTest);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void testProcess(Class classTest) {

        Method beforeMethod = null;
        Method afterMethod = null;
        HashMap<Method, Integer> testMethods = new HashMap<>();

        for (Method m : classTest.getMethods()) {
            if (m.getAnnotation(BeforeSuite.class) != null) {
                if (beforeMethod == null) beforeMethod = m;
                else throw new RuntimeException("Больше одного метода с аннотацией - BeforeSuite");
            }
            if (m.getAnnotation(AfterSuite.class) != null) {
                if (afterMethod == null) afterMethod = m;
                else throw new RuntimeException("Больше одного метода с аннотацией - AfterSuite");
            }
            if (m.getAnnotation(Test.class) != null) {
                testMethods.put(m, m.getAnnotation(Test.class).priority());
            }
        }
        List<Map.Entry<Method, Integer>> list = new ArrayList(testMethods.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Method, Integer>>() {
            @Override
            public int compare(Map.Entry<Method, Integer> a, Map.Entry<Method, Integer> b) {
                return a.getValue() - b.getValue();
            }
        });

        ArrayList<Method> resultMethods = new ArrayList<>();

        for (Map.Entry<Method, Integer> entry : list) {
            resultMethods.add(entry.getKey());
        }


        try {
            if (beforeMethod != null) beforeMethod.invoke(new Object[]{});
            for (int i = resultMethods.size() - 1; i >= 0; i--) {

                resultMethods.get(i).invoke(null,new Object[]{10, 15});

            }
            if (afterMethod != null) afterMethod.invoke(new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
