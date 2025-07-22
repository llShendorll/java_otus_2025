package ru.otus;

import ru.otus.annotations.*;

public class AnnotationDemo {
    @Before
    public void beforeTest() {
        System.out.println("before");
    }

    @Test
    public void test1() {
        System.out.println("test1");
    }

    @Test
    public void test2() {
        System.out.println("test2");
    }

    @Test
    public void test3() {
        System.out.println("test3");
    }

    @After
    public void afterTest() {
        System.out.println("after");
    }
}
