package org.example.service;

public class PaySamsung implements PaymentMethod{
    @Override
    public String pay() {
        return "삼성페이로 지불";
    }
}
