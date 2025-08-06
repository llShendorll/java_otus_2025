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

        Method[] testMethods = getMethodsWithAnnotation(testClass, Test.class);
        Method[] beforeMethods = getMethodsWithAnnotation(testClass, Before.class);
        Method[] afterMethods = getMethodsWithAnnotation(testClass, After.class);
        totalTests = testMethods.length;

        for (Method testMethod : testMethods) {
            try {
                Object instance = testClass.getDeclaredConstructor().newInstance();
                if (runMethods(instance, beforeMethods)) {
                    runTestMethod(instance, testMethod);
                } else {
                    log.info("Тест " + testMethod.getName() + " не пройден - @Before method failed");
                    failedTests++;
                }
                runMethods(instance, afterMethods);
            } catch (Exception e) {
                log.info("Не удалось создать экземпляр класса " + testClass.getName() + " - " + e.getMessage());
                failedTests++;
            }
        }

        printTestStatistics();
    }

    private Method[] getMethodsWithAnnotation(Class<?> testClass, Class<? extends Annotation> annotationClass) {
        try {
            return Arrays.stream(testClass.getMethods())
                    .filter(method -> method.isAnnotationPresent(annotationClass))
                    .toArray(Method[]::new);
        } catch (SecurityException e) {
            return new Method[0];
        }
    }

    private boolean runMethods(Object instance, Method[] methods) {
        boolean passed = true;
        for (Method method : methods) {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                log.info("Метод " + method.getName() + " не выполнен - " + e.getMessage());
                passed = false;
            }
        }
        return passed;
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
