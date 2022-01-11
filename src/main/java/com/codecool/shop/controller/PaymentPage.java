package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.Cart;
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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        JsonObject paymentData = Util.getRequestData(req);
        Files.write(Paths.get(shoppingCart.getOrderID() + "pson" + ".json"), paymentData.toString().getBytes());

        LOG.info("Loading succes page!");
        shoppingCart.clear();
        resp.sendRedirect("/success");

    }

    private void sendMail() {
        //Session session = Session
    }
}