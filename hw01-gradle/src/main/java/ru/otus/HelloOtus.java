package ru.otus;

import com.google.common.base.Joiner;
import java.util.Arrays;
import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<String> greetings = Arrays.asList("Hello", "Привет");
        String joinedGreetings = Joiner.on(", ").join(greetings);

        System.out.println(joinedGreetings);
    }
}
