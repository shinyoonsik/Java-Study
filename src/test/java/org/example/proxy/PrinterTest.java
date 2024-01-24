package org.example.proxy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PrinterTest {

    @Mock
    private Aspect logging;

    @Mock
    private Aspect userAuth;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void printerTest(){
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