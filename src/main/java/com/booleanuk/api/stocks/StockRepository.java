package com.booleanuk.api.stocks;

import com.booleanuk.api.customers.Customer;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class StockRepository {
    DataSource datasource;
    String dbUser;
    String dbURL;
    String dbPassword;
    String dbDatabase;
    Connection connection;

    public StockRepository() throws SQLException {
        this.getDatabaseCredentials();
        this.datasource = this.createDataSource();
        this.connection = this.datasource.getConnection();
    }

    private void getDatabaseCredentials() {
        try (InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            this.dbUser = prop.getProperty("db.user");
            this.dbURL = prop.getProperty("db.url");
            this.dbPassword = prop.getProperty("db.password");
            this.dbDatabase = prop.getProperty("db.database");
        } catch(Exception e) {
            System.out.println("Oops: " + e);
        }
    }

    private DataSource createDataSource() {
        // The url specifies the address of our database along with username and password credentials
        // you should replace these with your own username and password
        final String url = "jdbc:postgresql://" + this.dbURL + ":5432/" + this.dbDatabase + "?user=" + this.dbUser +"&password=" + this.dbPassword;
        final PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        return dataSource;
    }

    public List<Stock> getAll() throws SQLException {
        List<Stock> everyone = new ArrayList<>();
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Stocks");

        ResultSet results = statement.executeQuery();

        while (results.next()) {
            Stock theStock = new Stock(results.getLong("id"), results.getString("name"), results.getString("category"), results.getString("description"));
            everyone.add(theStock);
        }
        return everyone;
    }

    public Stock get(long id) throws SQLException {
        PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM Stocks WHERE id = ?");
        // Choose set**** matching the datatype of the missing element
        statement.setLong(1, id);
        ResultSet results = statement.executeQuery();
        Stock stock = null;
        if (results.next()) {
            stock = new Stock(results.getLong("id"), results.getString("name"), results.getString("category"), results.getString("description"));
        }
        return stock;
    }

    public Stock update(long id, Stock stock) throws SQLException {
        String SQL = "UPDATE Stocks " +
                "SET name = ? ," +
                "category = ? ," +
                "description = ? ," +
                "WHERE id = ? ";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        statement.setString(1, stock.getName());
        statement.setString(2, stock.getCategory());
        statement.setString(3, stock.getDescription());
        statement.setLong(4, id);
        int rowsAffected = statement.executeUpdate();
        Stock updatedStock = null;
        if (rowsAffected > 0) {
            updatedStock = this.get(id);
        }
        return updatedStock;
    }

    public Stock delete(long id) throws SQLException {
        String SQL = "DELETE FROM Stocks WHERE id = ?";
        PreparedStatement statement = this.connection.prepareStatement(SQL);
        // Get the stock we're deleting before we delete it
        Stock deletedStock = null;
        deletedStock = this.get(id);

        statement.setLong(1, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 0) {
            //Reset the stock we're deleting if we didn't delete it
            deletedStock = null;
        }
        return deletedStock;
    }

    public Stock add(Stock stock) throws SQLException {
        String SQL = "INSERT INTO Stocks(name, category, description) VALUES(?, ?, ?)";
        PreparedStatement statement = this.connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, stock.getName());
        statement.setString(2, stock.getCategory());
        statement.setString(3, stock.getDescription());
        int rowsAffected = statement.executeUpdate();
        long newId = 0;
        if (rowsAffected > 0) {
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    newId = rs.getLong(1);
                }
            } catch (Exception e) {
                System.out.println("Oops: " + e);
            }
            stock.setId(newId);
        } else {
            stock = null;
        }
        return stock;
    }





}
