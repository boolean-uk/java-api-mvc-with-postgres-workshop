package com.booleanuk.api.stocks;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        StockRepository repo = new StockRepository();
        Stock item = new Stock(1, "testName", "testCategory", "testDescription");
        System.out.println(repo.add(item));
        System.out.println(repo.get(1));
        System.out.println(repo.getAll());
        System.out.println(repo.delete(2));
    }
}
