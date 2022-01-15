package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.Product;
import com.codecool.shop.model.Supplier;
import com.codecool.shop.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMem {

    private List<User> users = new ArrayList<>();
    private static UserMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private UserMem() {
    }

    public static UserMem getInstance() {
        if (instance == null) {
            instance = new UserMem();
        }
        return instance;
    }
    public void add(User user) {
        user.setId(users.size() + 1);
        users.add(user);
    }

    public List<User> getAll() {
        return users;
    }
}
