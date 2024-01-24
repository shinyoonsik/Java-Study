package org.example.proxy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrinterTest {

    @Test
    void printerTest(){
        Aspect logging = mock(Aspect.class); // 인터페이스를 통해서 Mock을 만들 수 없다.
        Aspect userAuth = mock(UserAuth.class);
        Printable printer = new PrinterProxy("samsung", logging, userAuth);
        String message = "나는 삼송맨이다";

        String printedMessage = printer.print(message);

        String expected = "<samsung>" +message + "</samsung>";
        assertEquals(expected, printedMessage);

        verify(logging, times(1)).before();
        verify(logging, times(1)).after();
        verify(userAuth, times(1)).before();
        verify(userAuth, times(1)).after();
    }

}