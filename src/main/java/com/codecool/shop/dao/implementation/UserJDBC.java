package com.codecool.shop.dao.implementation;

import com.codecool.shop.model.User;

import javax.sql.DataSource;
import java.sql.*;

public class UserJDBC {
    private DataSource dataSource;

    public UserJDBC(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void add(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getInt(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
