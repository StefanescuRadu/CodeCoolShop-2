package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.DatabaseManager;


import com.codecool.shop.model.Order;
import com.codecool.shop.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/orders"})
public class OrdersController extends HttpServlet {


    private DatabaseManager databaseManager = new DatabaseManager();

    private static final Logger LOG = LoggerFactory.getLogger(OrdersController.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = null;
        try {
            databaseManager.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpSession session = req.getSession(false);
        if(session != null){
            email = session.getAttribute("email").toString();
        }
        List<Order> allOrders = databaseManager.getOrders();
        List<User> users = databaseManager.getUsers();
        List<Order> orders = new ArrayList<>();
        for (Order order: allOrders) {
            if( email.equals(order.getUser_email()) ){
                orders.add(order);
            };

        }

        try {

            WebContext context = new WebContext(req, resp, req.getServletContext());
            ;
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            context.setVariable("orders",orders);
            engine.process("product/orders.html", context, resp.getWriter());
            LOG.info("Entered orders page!");
        } catch (Exception e) {
            LOG.error("Could not load orders page!");
        }

    }
}