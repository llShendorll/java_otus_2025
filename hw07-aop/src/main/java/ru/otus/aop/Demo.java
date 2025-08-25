package ru.otus.aop;

public class Demo {
    public static void main(String[] args) {
        new Demo().action();
    }

    public void action() {
        TestLoggingInterface proxy = LoggingProxy.createProxy(new TestLogging());
        proxy.calculation(1);
        proxy.calculation(1, 2);
        proxy.calculation(3, 4, "five");
    }
}
