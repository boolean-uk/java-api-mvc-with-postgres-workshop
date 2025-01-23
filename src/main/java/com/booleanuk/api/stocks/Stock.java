package com.booleanuk.api.stocks;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;


@ToString
@AllArgsConstructor
public class Stock {
    @Setter
    private long id;
    @Getter
    private String name;
    @Getter
    private String category;
    @Getter
    private String description;

}
