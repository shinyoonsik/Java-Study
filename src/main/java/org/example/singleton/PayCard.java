package org.example.singleton;

public class PayCard {

    private static PayCard instance = new PayCard();

    private PayCard() {}

    public static PayCard getInstance(){
        return instance;
    }
}
