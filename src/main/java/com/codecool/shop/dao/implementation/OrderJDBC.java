package com.codecool.shop.dao.implementation;

import com.codecool.shop.Util.OrderType;
import com.codecool.shop.model.Order;

import javax.sql.DataSource;
import java.sql.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OrderJDBC {
    private DataSource dataSource;

    public OrderJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(Order order) {
        try (Connection connection = dataSource.getConnection()) {
            Array array = connection.createArrayOf("VARCHAR", order.getProduct_list().toArray(new String[0]));
            System.out.println(array);
            String sql = "INSERT INTO orders (order_date, order_status, total_price,product_list,user_email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, order.getOrder_date().toString());
            statement.setString(2, order.getOrderType().toString());
            statement.setInt(3, order.getTotal_price());
            statement.setArray(4, array);
            statement.setString(5,order.getUser_email());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            order.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM orders ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                String date = resultSet.getString("order_date");
                OrderType status = OrderType.valueOf(resultSet.getString("order_status"));
                int price = resultSet.getInt("total_price");
                String email = resultSet.getString("user_email");
                Array a = resultSet.getArray("product_list");
                List<String> products =  new ArrayList<>();
                String[] values = (String[]) a.getArray();
//
                System.out.println(a);
                System.out.println(email);
                for (String s:values) {
                    products.add(s);
                }
                Order order = new Order(date,status,price,products,email);
                orders.add(order);
            }

            return orders;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}