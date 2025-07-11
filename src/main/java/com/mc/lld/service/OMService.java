package com.mc.lld.service;

import com.mc.lld.model.PaymentModeType;
import com.mc.lld.PrepaidPayment;
import com.mc.lld.model.Address;
import com.mc.lld.model.Buyer;
import com.mc.lld.model.Order;
import com.mc.lld.model.Product;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OMService {


    private Map<String, Buyer> buyerMap;
    private Map<String, Product> productMap;


    private static volatile OMService INSTANCE;

    private OMService () {
        this.buyerMap = new ConcurrentHashMap<>();
        this.productMap = new ConcurrentHashMap<>();
    }

    public static OMService getInstance() {
        if (INSTANCE == null) {
            synchronized (OMService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OMService();
                }
            }
        }
        return INSTANCE;
    }

    public String createBuyer(String name, Address address) {
        if (name == null || name.length() <=0) {
            throw new IllegalArgumentException("Invalid request for buyer creation");
        }
        Buyer buyer = new Buyer(name, address);
        buyerMap.put(buyer.getBuyerId(), buyer);
        return buyer.getBuyerId();
    }

    public String createProduct(String name, Integer count, Double price , Address productAddress) {
        if (name == null || name.length() <=0 || count <= 0 || price <= 0.0|| productAddress == null) {
            throw new IllegalArgumentException("Invalid request for product creation");
        }
        Product product = new Product(name,count,price,  productAddress);
        productMap.put(product.getProductId(), product);
        return product.getProductId();
    }

    public Product getProductById(String productId) {
        if (productId == null || productId.length() == 0) {
            throw new IllegalArgumentException("Invalid productId ");
        }
        return productMap.get(productId);
    }

    public Buyer getBuyerById(String buyerId) {
        if (buyerId == null || buyerId.length() == 0) {
            throw new IllegalArgumentException("Invalid buyerId.");
        }
        return buyerMap.get(buyerId);
    }


    public synchronized Order placeoder(List<Product> products, Buyer buyer, PaymentModeType paymentModeType) {
        Order order = null;
        if (sufficentStockPresent(products)) {
            PaymentProcessor paymentProcessor = PaymentModeType.COD.equals(paymentModeType) ? new CODPayment() : new PrepaidPayment();
            order = new Order(products, buyer);
            decreaseProductCount(products);
            paymentProcessor.processPayment(order.getTotalOrderValue());
        }
       return order;
    }

    private boolean sufficentStockPresent(List<Product> products) {
        for (Product product: products) {
            if (product.getCount() <= 0 ) {
                return false;
            }
        }
        return true;
    }

    private void decreaseProductCount(List<Product> products) {
        for (Product product: products) {
            if (product.getCount() > 0 ) {
                product.setCount(product.getCount() -1);
            }
        }
    }


}
