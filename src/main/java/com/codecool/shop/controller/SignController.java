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

@WebServlet(urlPatterns = {"/sign"})
public class SignController extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(SignController.class);
    private String error = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try{

            WebContext context = new WebContext(req, resp, req.getServletContext());;
            TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
            context.setVariable("error",error);
            engine.process("sign.html", context, resp.getWriter());
            LOG.info("Entered sign page!");
        }
        catch (Exception e){
            LOG.error("Could not load sign page!");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String user = req.getParameter("user");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if(user!= "radu"){
            error = "Email already in use";
        }
        System.out.println(user);
        System.out.println(email);
        System.out.println(password);
        resp.sendRedirect("/sign");
    }
}