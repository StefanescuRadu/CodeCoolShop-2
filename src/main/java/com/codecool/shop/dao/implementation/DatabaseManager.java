package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import org.postgresql.ds.PGSimpleDataSource;


import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager {
    private ProductCategoryDao productCategoryDao;
    private DataSource dataSource;
    private ProductDao productDao;
    private SupplierDao supplierDao;
    private UserJDBC userJDBC;


    public void setup() throws SQLException {
        DataSource dataSource = connect();
        productCategoryDao = new ProductCategoryDaoJDBC(dataSource);
        productDao = new ProductDaoJDBC(dataSource);
        supplierDao = new SupplierDaoJDBC(dataSource);
        userJDBC = new UserJDBC(dataSource);
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
            System.out.println(prop.getProperty("url"));
            System.out.println(prop.getProperty("user"));
            System.out.println(prop.getProperty("password"));
            System.out.println(prop.getProperty("database"));
            System.out.println("Trying to connect");
            dataSource.getConnection().close();
            System.out.println("Connection ok.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return dataSource;
    }


    }

