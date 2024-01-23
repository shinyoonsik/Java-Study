package org.example.proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {

    @Test
    void printerTest(){
        Printer printer = new Printer("samsung");
        String message = "나는 삼송맨이다";
        String printedMessage = printer.print(message);

        String expected = "<samsung>" +message + "</samsung>";
        assertEquals(expected, printedMessage);
    }

}