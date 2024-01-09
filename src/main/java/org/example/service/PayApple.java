package org.example.service;

public class PayApple implements PaymentMethod {
    @Override
    public String pay() {
        return "애플페이로 지불";
    }
}
