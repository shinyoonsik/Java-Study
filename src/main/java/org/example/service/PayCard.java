package org.example.service;

public class PayCard implements PaymentMethod{
    @Override
    public String pay() {
        return "카드로 지불";
    }
}
