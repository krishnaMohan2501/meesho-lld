package com.mc.lld.service;

public class CODPayment implements PaymentProcessor {
    @Override
    public boolean processPayment(Double value) {
        System.out.println("Successfully processed payment for COD payment mode., value= " + value);
        return true;
    }
}
