package com.codecool.shop.controller;

import com.codecool.shop.Util.OrderType;
import com.codecool.shop.config.TemplateEngineUtil;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(urlPatterns = {"/payment"})
public class PaymentPage extends HttpServlet {
    private final Cart shoppingCart = Cart.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(PaymentPage.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            LOG.info("Receiving request: " + req.getPathInfo());
            WebContext context = new WebContext(req, resp, req.getServletContext());
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());

            context.setVariable("cart", shoppingCart);

            engine.process("product/payment.html", context, resp.getWriter());
        }
        catch(Exception e){
            LOG.error("Could not load payment page!");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        JsonObject paymentData = Util.getRequestData(req);
        System.out.println(paymentData);
        Files.write(Paths.get(shoppingCart.getOrderID() + "pson" + ".json"), paymentData.toString().getBytes());

        if(session != null){
            String email = session.getAttribute("email").toString();
            System.out.println(email);
        }
        writeOrder();
        LOG.info("Loading succes page!");
        shoppingCart.clear();
        resp.sendRedirect("/success");

    }

    private void sendMail() {
        //Session session = Session
    }

    private void writeOrder(){

        List<List> order = new ArrayList<>();
        for (CartItem item : shoppingCart.getCartItems()) {
            List<Object> items = new ArrayList<>();
            items.add(item.getProduct().getName());
            items.add(item.getQuantity());
            items.add(item.getProduct().getPrice());
            items.add(item.getSubTotalPrice());
            System.out.println(items);
            order.add(items);
        }

        System.out.println(order);
        Date date = new Date();
        System.out.println(date);
        float totalPrice = shoppingCart.getTotalPrice();
        System.out.println(totalPrice);
        OrderType.randomLetter();
    }
}