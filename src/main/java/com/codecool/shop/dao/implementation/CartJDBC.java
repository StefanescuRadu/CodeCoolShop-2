package com.codecool.shop.dao.implementation;

import com.codecool.shop.Util.OrderType;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.Cart;
import com.codecool.shop.service.CartItem;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartJDBC {

    private DataSource dataSource;

    public CartJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(Cart cart,String email) {
        try (Connection connection = dataSource.getConnection()) {
            Array array = connection.createArrayOf("VARCHAR", cart.getCartItems().toArray(new CartItem[0]));
            System.out.println(array);
            String sql = "INSERT INTO savedCart (user_email,product_list) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, email);
            statement.setArray(2, array);

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
//            cart.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAll() {
        List<String> carts = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT * FROM savedCart ";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){

                String email = resultSet.getString("order_date");
                Array a = resultSet.getArray("product_list");
                List<String> products =  new ArrayList<>();
                String[] values = (String[]) a.getArray();
//
                System.out.println(a);
                System.out.println(email);
                for (String s:values) {
                    products.add(s);
                }
//                Order order = new Order(date,status,price,products,email);
//                orders.add(order);
            }

            return carts;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
