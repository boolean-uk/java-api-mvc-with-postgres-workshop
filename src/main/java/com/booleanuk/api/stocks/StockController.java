package com.booleanuk.api.stocks;

import com.booleanuk.api.customers.Customer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("stocks")
public class StockController {
    private StockRepository stocks;

    public StockController() throws SQLException {
        this.stocks = new StockRepository();
    }

    @GetMapping
    public List<Stock> getAll() throws SQLException {
        return this.stocks.getAll();
    }

    @GetMapping("/{id}")
    public Stock getOne(@PathVariable (name = "id") long id) throws SQLException {
        Stock stock = this.stocks.get(id);
        if (stock == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return stock;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Stock create(@RequestBody Stock stock) throws SQLException {
        Stock theStock = this.stocks.add(stock);
        if (theStock == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to create the specified Stock");
        }
        return theStock;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Stock update(@PathVariable (name = "id") long id, @RequestBody Stock stock) throws SQLException {
        Stock toBeUpdated = this.stocks.get(id);
        if (toBeUpdated == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.stocks.update(id, stock);
    }

    @DeleteMapping("/{id}")
    public Stock delete(@PathVariable (name = "id") long id) throws SQLException {
        Stock toBeDeleted = this.stocks.get(id);
        if (toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return this.stocks.delete(id);
    }
}
