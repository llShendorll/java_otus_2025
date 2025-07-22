package ru.otus;

public class Main {
    public static void main(String[] args) {
        AnnotationDemo demo = new AnnotationDemo();
        UnitTestRunner<AnnotationDemo> unitTestRunner = new UnitTestRunner<>();
        unitTestRunner.runAllTests(demo);
    }
}
