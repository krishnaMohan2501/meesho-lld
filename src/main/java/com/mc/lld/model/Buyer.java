package com.mc.lld.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Buyer {

    private String buyerId;
    private Address address;
    private String name;

    public Buyer(String name,Address address) {
        this.buyerId = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
    }
}
