package com.mc.lld.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Order {

    private String orderId;
    private List<Product> products;
    private Buyer buyer;
    private Double totalOrderValue;

    public Order(List<Product> products, Buyer buyer) {
        this.products = products;
        this.buyer = buyer;
        this.totalOrderValue = calculateOrderValue();

    }

    private Double calculateOrderValue() {
        Double value = 0.0;
        for (Product product: this.products) {
            value = value + product.getPrice();
        }
        return value;
    }

}
