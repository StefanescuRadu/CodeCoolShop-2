package com.codecool.shop.dao.implementation;

import javax.sql.DataSource;

public class UserJDBC {
    private DataSource dataSource;

    public UserJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
