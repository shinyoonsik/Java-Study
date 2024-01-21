package org.example.strategy;

public class PayMoney implements PaymentMethod {
    @Override
    public String pay() {
        return "현금으로 지불";
    }
}
