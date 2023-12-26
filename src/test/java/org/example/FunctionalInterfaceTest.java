package org.example;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FunctionalInterfaceTest {


    @Test
    void Consumer_테스트(){
        Consumer<String> consumer = (x) -> System.out.println("나는 " + x + " Consumer다 ");
        consumer.accept("킹왕짱");
        System.out.println("test");
    }

    @Test
    void Supplier_테스트(){
        Supplier supplier = () -> "나는 supplier다";
        assertEquals("나는 supplier다", supplier.get()) ;
    }

    @Test
    void Function_테스트(){
        Function<Integer, String> function = (x) -> "나는 Function이다 " + x.getClass();
        System.out.println(function.apply(10));

        Function<String, List<String>> result = (str) -> {
            return List.of(str);
        };
        System.out.println(result.apply("bye"));
        assertEquals("hello", result.apply("hello").get(0));
    }

    @Test
    void Predicate_테스트(){
        Predicate<Integer> predicate = (x) -> x > 10;
        System.out.println(predicate.test(10));
        assertTrue(predicate.test(20));
    }
}
