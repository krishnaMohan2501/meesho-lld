package com.mc.lld.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Address {

    String street;
    String area;
    Pincode pincode;


}
