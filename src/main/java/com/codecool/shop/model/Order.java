package com.codecool.shop.model;

import com.codecool.shop.Util.OrderType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Order {
    private int id;
    private String order_date;
    private OrderType orderType;
    private int total_price;
    private String user_email;
    private List<String> product_list;

    public String getUser_email() {
        return user_email;
    }

    public Order(String order_date, OrderType orderType, int total_price, List<String> product_list, String user_email) {

        this.order_date = order_date;
        this.orderType = orderType;
        this.total_price = total_price;
        this.product_list = product_list;
        this.user_email = user_email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public List<String> getProduct_list() {
        return product_list;
    }

    public void setProduct_list(List<String> product_list) {
        this.product_list = product_list;
    }
}
