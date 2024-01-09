package org.example.service;

public class PurchaseService {
    private PaymentMethod paymentMethod;

    public PurchaseService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String pay(){
        return paymentMethod.pay();
    }
}