package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.DatabaseManager;
import com.codecool.shop.dao.implementation.UserMem;
import com.codecool.shop.model.User;
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
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"/sign"})
public class SignController extends HttpServlet {


    private DatabaseManager databaseManager = new DatabaseManager();
    private final UserMem users = UserMem.getInstance();
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
        LOG.info("DoPOST sign page!");
        String user = req.getParameter("user");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
                databaseManager.setup();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        List<User> getUsers = databaseManager.getUsers();
//        List<User> getUsers = users.getAll();
        if(getUsers.size()==0){
            User newUser = new User(user,email,password);

            databaseManager.addUser(newUser);
            LOG.info("LOGIN REDIRECT!");
            resp.sendRedirect("/login");
        }
        for (User user1:getUsers) {
            System.out.println("This is" + user1.getName() + user1.getPassword());
            System.out.println(user1.getEmail().equals(email));
            if(user1.getEmail().equals(email)){
                error = "Email already in use!";
                LOG.info("SING UP REDIRECT!");
                resp.sendRedirect("/sign");
            }
        }
        User newUser = new User(user,email,password);


        databaseManager.addUser(newUser);
        resp.sendRedirect("/login");


    }
}