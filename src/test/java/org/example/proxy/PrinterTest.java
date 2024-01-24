package org.example.proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    @Test
    void printerTest(){
        Printable printer = new PrinterProxy("samsung"); // (LSP 준수)상위 클래스의 인스턴스는 하위 클래스의 인스턴스로 대체될 수 있어야 한다.
        String message = "나는 삼송맨이다";
        String printedMessage = printer.print(message);

        String expected = "<samsung>" +message + "</samsung>";
        assertEquals(expected, printedMessage);
    }

}