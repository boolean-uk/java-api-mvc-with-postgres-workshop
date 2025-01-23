package com.booleanuk.api.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
public class Customer {
    @Setter
    private long id;
    @Getter
    private String name;
    @Getter
    private String address;
    @Getter
    private String email;
    @Getter
    private String phoneNumber;


    public String toString() {
        String result = "";
        result += this.id + " - ";
        result += this.name + " - ";
        result += this.address;
        return result;
    }


}
