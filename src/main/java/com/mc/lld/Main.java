package com.mc.lld;

import com.mc.lld.model.*;
import com.mc.lld.service.OMService;
import com.mc.lld.service.PincodeServicibility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello");

        OMService omService = OMService.getInstance();

        System.out.println("Create Address in system");

        Address buyerAddress1 = new Address("Street1", "Mahadevpura", new Pincode(560048));
        Address address2 = new Address("Street2", "Marathalli", new Pincode(560049));

        System.out.println("Create Buyer in system");

        String buyerId = omService.createBuyer("krishna", buyerAddress1);
        System.out.println("Buyer got created. buyer-id: " + buyerId);



        Address productAddress1 = new Address("Street3", "KR-Puram", new Pincode(560050));
        String productId = omService.createProduct("Kurkure", 1, 20.0, productAddress1);
        System.out.println("Product got created. product-id: " + productId);


        System.out.println("Added source and des servicibility map");
        PincodeServicibility pincodeServicibility = PincodeServicibility.getInstance();
        pincodeServicibility.addSourceAndDetToServiecibilty(productAddress1, buyerAddress1, PaymentModeType.COD);


        System.out.println("Placing order");
        Product product = omService.getProductById(productId);
        Buyer buyer = omService.getBuyerById(buyerId);

        Map<Pincode, List<PaymentModeType>> pincodeListMap = pincodeServicibility.fetchServicebleDestByPincode(product.getAddress().getPincode());
        List<PaymentModeType> paymentModeTypeList = pincodeListMap.get(buyer.getAddress().getPincode());
        if (paymentModeTypeList.size() <= 0) {
            System.out.println("product can not be delivered at buyer pin code");
        }
        Order order = omService.placeoder(Arrays.asList(product), buyer, PaymentModeType.COD);
        System.out.println("Order got placed successful orderId = " + order.getOrderId());


        /**
         * // Design a simple e-commerce platform, where you need to have the capability of handling buyers, orders and products.
         *
         *
         * // The following methods need to be implemented:
         *
         * // Create buyer account:
         * // 1. Store buyer name and his address
         * // 2. Return created buyerId
         *
         *
         * // Create product:
         * // 1. Store product name, inventory, price and pickup address.
         * // 2. Return created productId
         *
         *
         * // Create pincode serviceability:
         * // 1. Store sourcePincode(product pincode), destinationPincode(buyer pincode) and paymentMode serviceability.
         * // It will be a map of source(pickup) and destination pincodes where each pair of pincode can be serviceable for either cod or prepaid or both.
         *
         *
         * // Create order:  String createOrder(String buyerId, String productId, int quantity, String paymentMode)
         * // 1. Check if order is valid based on available inventory and pincode serviceability,
         * // if not valid due to serviceability throw a proper exception and print "Order failed because pincode is unserviceable" and exit.
         * // if not valid due to insufficient stock throw a proper exception and print "Order failed because product stock is insufficient" and exit.
         *
         * // 2. PaymentMode can be prepaid or cod
         * // 3. Assume an order comprises of a single product
         * // 4. Return created orderId
         *
         *
         * // Note:
         *
         * // You can also use IntelliJ or Eclipse to write your code.
         *
         * // You can use util library to generate a uniqueId.
         *
         *
         *
         * // Evaluation Criteria:
         *
         * // Correctness
         * // Approach to the Solution
         * // Code Structure
         * // Race condition/Thread Safe
         */
    }
}
