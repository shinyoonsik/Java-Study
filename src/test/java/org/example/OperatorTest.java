package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperatorTest {

    @Test
    void Operator_람다_테스트(){
        Operator minus = (x ,y) -> x - y;
        Operator plus = (x, y) -> x + y;
        Operator multiply = (x, y) -> x * y;
        Operator divide = (x, y) -> {
            if(y == 0) throw new ArithmeticException("0으로 나눌 수 없습니다.");
            return x / y;
        };

        assertEquals(0, minus.operator(1, 1));
        assertEquals(2, plus.operator(1, 1));
        assertEquals(1, multiply.operator(1, 1));
        assertEquals(1, divide.operator(1, 1));
    }

    @Test
    void 나누기_예외_테스트(){
        Operator divide = (x, y) -> {
            if(y != 0) return x / y;
            else throw new RuntimeException("0으로 나눌수 없다");
        };

        assertThrows(RuntimeException.class, () -> {
            divide.operator(1, 0);
        });
    }
}