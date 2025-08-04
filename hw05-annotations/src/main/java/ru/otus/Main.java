package ru.otus;

public class Main {
    public static void main(String[] args) {
        UnitTestRunner unitTestRunner = new UnitTestRunner();
        unitTestRunner.runAllTests(AnnotationDemo.class);
    }
}
