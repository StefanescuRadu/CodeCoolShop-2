package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.service.Cart;
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

@WebServlet(urlPatterns = {"/success"})
public class SuccesController extends HttpServlet {
    private final Cart shoppingCart = Cart.getInstance();
    private static final Logger LOG = LoggerFactory.getLogger(SuccesController.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

            WebContext context = new WebContext(req, resp, req.getServletContext());
            context.setVariable("orderId", shoppingCart.getOrderID());
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            engine.process("product/success.html", context, resp.getWriter());
            LOG.info("Entered succes page!");
        }
        catch (Exception e){
            LOG.error("Could not load succes page!");
        }

    }
}