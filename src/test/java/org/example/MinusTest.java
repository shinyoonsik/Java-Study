package org.example;

import org.junit.jupiter.api.Test;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class MinusTest {

    @Test
    void 익명_클래스_테스트(){
        Minus result = new Minus() {
            @Override
            public int minus(int x, int y) {
                return x - y;
            }
        };

        int minus = result.minus(1, 1);
        assertEquals(0, minus);
    }

    @Test
    void 매개변수가_없는_람다식_테스트1(){
        Runnable hello = () -> System.out.println("hello");
        hello.run();
    }

    @Test
    void minus_인터페이스를_람다식으로_변환_테스트(){
        Minus result = (x, y) -> x - y;
        assertEquals(0, result.minus(1, 1));
    }

    @Test
    void 함수를_파라미터로_넘기는_테스트(){
        int result = operator((x, y) -> x - y, 3, 1);
        assertEquals(2, result);
    }

    private int operator(Minus miniOperator, int x, int y){
        return miniOperator.minus(x, y);
    }

}