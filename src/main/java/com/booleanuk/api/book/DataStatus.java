package com.booleanuk.api.book;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DataStatus<T> {
    protected String status;
    protected T data;

    public void set(T data){
        this.status = "sucess";
        this.data = data;
    }
}
