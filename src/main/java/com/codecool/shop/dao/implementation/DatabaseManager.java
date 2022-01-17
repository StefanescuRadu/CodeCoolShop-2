package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
import com.codecool.shop.service.Cart;
import org.postgresql.ds.PGSimpleDataSource;


import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {
    private ProductCategoryDao productCategoryDao;
    private DataSource dataSource;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private UserJDBC userJDBC;
    private OrderJDBC orderJDBC;
    private CartJDBC cartJDBC;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        productCategoryDao = new ProductCategoryDaoJDBC(dataSource);
        productDao = new ProductDaoJDBC(dataSource);
        supplierDao = new SupplierDaoJDBC(dataSource);
        userJDBC = new UserJDBC(dataSource);
        orderJDBC = new OrderJDBC(dataSource);
        cartJDBC = new CartJDBC(dataSource);
    }

    private DataSource connect() throws SQLException {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
//        String dbName = "test";
//        String user = "test";
//        String password = "test";
//        ApplicationProperties properties = new ApplicationProperties();
//        dataSource.setDatabaseName(properties.readProperty("database"));
//        dataSource.setUser(properties.readProperty("user"));
//        dataSource.setPassword(properties.readProperty("password"));
        try (
            InputStream input = new FileInputStream("src/main/resources/connection.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
//            System.out.println(prop.getProperty("url"));
//            System.out.println(prop.getProperty("user"));
//            System.out.println(prop.getProperty("password"));
//            System.out.println(prop.getProperty("database"));
//            dataSource.setUrl(prop.getProperty("url"));
            dataSource.setDatabaseName(prop.getProperty("database"));
            dataSource.setUser(prop.getProperty("user"));
            dataSource.setPassword(prop.getProperty("password"));
            System.out.println("Trying to connect");
            dataSource.getConnection().close();
            System.out.println("Connection ok.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return dataSource;
    }
    public void addUser(User user){
        userJDBC.add(user);
    }

    public List<User> getUsers(){
        return userJDBC.getAll();
    }

    public void addOrder(Order order){orderJDBC.add(order);}


    public List<Order> getOrders(){return orderJDBC.getAll();}

    public void saveOrder(Cart cart,String email){
        cartJDBC.add(cart,email);
    }

    }


