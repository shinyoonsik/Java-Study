package org.example.singleton;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayCardTest {

    @Test
    public void singletonTest(){
        PayCard payCard1 = PayCard.getInstance();
        PayCard payCard2 = PayCard.getInstance();

        assertEquals(payCard1, payCard2);

    }

}