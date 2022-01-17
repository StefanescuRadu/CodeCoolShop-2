package com.codecool.shop.controller;

import com.codecool.shop.Util.OrderType;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.DatabaseManager;
import com.codecool.shop.model.Order;
import com.codecool.shop.service.Cart;
import com.codecool.shop.service.CartItem;
import com.google.gson.JsonObject;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentPage extends HttpServlet {
    private DatabaseManager databaseManager = new DatabaseManager();
    private final Cart shoppingCart = Cart.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(PaymentPage.class);
    private String email;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            HttpSession session = req.getSession(false);


            LOG.info("Receiving request: " + req.getPathInfo());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            context.setVariable("ses",session);
            context.setVariable("cart", shoppingCart);

            engine.process("product/payment.html", context, resp.getWriter());
        }
        catch(Exception e){
            LOG.error("Could not load payment page!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            databaseManager.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HttpSession session = req.getSession(false);
//        JsonObject paymentData = Util.getRequestData(req);
//        System.out.println(paymentData);
//        Files.write(Paths.get(shoppingCart.getOrderID() + "pson" + ".json"), paymentData.toString().getBytes());

        if(session != null){

            email = session.getAttribute("email").toString();
            System.out.println(email);
            List<String> order = new ArrayList<>();
            for (CartItem item : shoppingCart.getCartItems()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Product: ")
                        .append(item.getProduct().getName() + " ")
                        .append("Quantity: ")
                        .append(item.getQuantity()+ " ")
                        .append("Subtotal: ")
                        .append(item.getSubTotalPrice());
                System.out.println(sb);
                order.add(sb.toString());

            }

            Date date = new Date();
            String formatDate = date.toString();
            int totalPrice = (int) shoppingCart.getTotalPrice();
            Order newOrder = new Order(formatDate,OrderType.randomLetter(),totalPrice,order,email);
            databaseManager.addOrder(newOrder);
            LOG.info("THIS IS THE ORDER PROCCESSING!");
            System.out.println(newOrder + "this is it !!......");
        }

        LOG.info("Loading succes page!");
        shoppingCart.clear();
        resp.sendRedirect("/success");

    }

    private void sendMail() {
        //Session session = Session
    }

    private void writeOrder(){
        try {
            databaseManager.setup();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<String> order = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Product: ")
            .append(item.getProduct().getName() + " ")
                    .append("Quantity: ")
                            .append(item.getQuantity()+ " ")
                    .append("Subtotal: ")
                                    .append(item.getSubTotalPrice());
            System.out.println(sb);
            order.add(sb.toString());

        }

        System.out.println(order);
        Date date = new Date();
        String formatDate = date.toString();
        System.out.println(date);
        int totalPrice = (int) shoppingCart.getTotalPrice();
        System.out.println(totalPrice);
        Order newOrder = new Order(formatDate,OrderType.randomLetter(),totalPrice,order,email);
        databaseManager.addOrder(newOrder);
    }
}