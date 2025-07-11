package com.mc.lld.service;

import com.mc.lld.model.PaymentModeType;
import com.mc.lld.model.Address;
import com.mc.lld.model.Pincode;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PincodeServicibility {
    // 1. Store sourcePincode(product pincode), destinationPincode(buyer pincode) and paymentMode serviceability.

    // It will be a map of source(pickup) and destination pincodes where each pair of pincode can be serviceable for either cod or prepaid or both.

    private final Map<Pincode, Map<Pincode, List<PaymentModeType>>> sourcDetPaymentMap;
    private static volatile PincodeServicibility INSTANCE;

    private PincodeServicibility () {
        sourcDetPaymentMap = new ConcurrentHashMap<>();
    }

    public static PincodeServicibility getInstance() {
        if (INSTANCE == null) {
            synchronized (PincodeServicibility.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PincodeServicibility();
                }
            }
        }
        return INSTANCE;
    }

    @Nullable
    public Map<Pincode, List<PaymentModeType>> fetchServicebleDestByPincode(Pincode pincode) {
        if (pincode == null) {
            System.out.println("Pincode can not be null");
            throw new IllegalStateException("Pincode can not be null");
        }
        return sourcDetPaymentMap.get(pincode);
    }

    public void addSourceAndDetToServiecibilty(Address productAddress, Address buyerAddress, PaymentModeType paymentModeType) {

        if (sourcDetPaymentMap.get(productAddress.getPincode()) != null) {
            Map<Pincode, List<PaymentModeType>> destPinCodeMap = sourcDetPaymentMap.get(productAddress.getPincode());
            if (destPinCodeMap.get(buyerAddress.getPincode())!=null) {
                List<PaymentModeType> paymentModeTypes = destPinCodeMap.get(buyerAddress.getPincode());
                paymentModeTypes.add(paymentModeType);
            } else {
                List<PaymentModeType> paymentModeTypeList = new CopyOnWriteArrayList<>();
                destPinCodeMap.put(buyerAddress.getPincode(), paymentModeTypeList);
            }
        } else {
            Map<Pincode, List<PaymentModeType>> destPinCodeMap = new ConcurrentHashMap<>();
            List<PaymentModeType> paymentModeTypeList = new CopyOnWriteArrayList<>();
            paymentModeTypeList.add(paymentModeType);
            destPinCodeMap.put(buyerAddress.getPincode(), paymentModeTypeList);
            sourcDetPaymentMap.put(productAddress.getPincode(), destPinCodeMap);
            System.out.println("test");
        }
    }
}
