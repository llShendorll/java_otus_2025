package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.logging.Logger;
import ru.otus.annotations.*;

public class UnitTestRunner {

    private static final Logger log = Logger.getLogger("InfoLogging");

    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;

    public void runAllTests(Class<?> testClass) {
        totalTests = 0;
        passedTests = 0;
        failedTests = 0;

        Method[] testMethods = getMethodsWithAnnotation(testClass);
        totalTests = testMethods.length;

        for (Method testMethod : testMethods) {
            try {
                Object instance = testClass.getDeclaredConstructor().newInstance();
                runMethodsWithAnnotation(instance, Before.class);
                runTestMethod(instance, testMethod);
                runMethodsWithAnnotation(instance, After.class);
            } catch (Exception e) {
                log.info("Не удалось создать экземпляр класса " + testClass.getName() + " - " + e.getMessage());
                failedTests++;
            }
        }

        printTestStatistics();
    }

    private Method[] getMethodsWithAnnotation(Class<?> testClass) {
        try {
            return Arrays.stream(testClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(Test.class))
                    .toArray(Method[]::new);
        } catch (SecurityException e) {
            return new Method[0];
        }
    }

    private void runMethodsWithAnnotation(Object instance, Class<? extends Annotation> annotationClass) {
        for (Method method : instance.getClass().getMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                try {
                    method.invoke(instance);
                } catch (Exception e) {
                    log.info("Метод " + method.getName() + " не выполнен - " + e.getMessage());
                }
            }
        }
    }

    private void runTestMethod(Object instance, Method method) {
        try {
            method.invoke(instance);
            passedTests++;
        } catch (Exception e) {
            log.info("Тест " + method.getName() + " не пройден - " + e.getMessage());
            failedTests++;
        }
    }

    private void printTestStatistics() {
        System.out.println("\n=== СТАТИСТИКА ВЫПОЛНЕНИЯ ТЕСТОВ ===");
        System.out.println("Всего тестов: " + totalTests);
        System.out.println("Успешно выполнено: " + passedTests);
        System.out.println("Упало с ошибкой: " + failedTests);
        System.out.println("=====================================");
    }
}
