package com.booleanuk.api.book;

import org.apache.catalina.connector.Response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends DataStatus<Map<String,String>> {

    public void set(String message){
        this.status = "error";
        Map<String, String> reply = new HashMap<>();
        reply.put("message",message);
        this.data = reply;
    }
}
