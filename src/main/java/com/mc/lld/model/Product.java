package com.mc.lld.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Product {

    private String productId;
    private String name;
    private Integer count;
    private Double price;
    private Address address;

    public Product(String name, Integer count, Double price , Address address) {
        this.productId = generateProductId();
        this.name = name;
        this.count = count;
        this.price = price;
       this.address = address;
    }

    public String generateProductId() {
        return "P-" + String.format(UUID.randomUUID().toString(), "06%d");
    }
}
