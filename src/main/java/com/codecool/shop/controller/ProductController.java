package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.DatabaseManager;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.service.Cart;
import com.codecool.shop.service.ProductService;
import com.codecool.shop.config.TemplateEngineUtil;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);
    private DatabaseManager databaseManager = new DatabaseManager();
    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);


        LOG.info("Entered main page!");
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDao supplierDataStore = SupplierDaoMem.getInstance();
        ProductService productService = new ProductService(productDataStore, productCategoryDataStore);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        if ((req.getParameter("categoryId") != null) && (req.getParameter("vendorId") == null)) {
            int category_id = Integer.parseInt(req.getParameter("categoryId"));
            context.setVariable("category", productService.getProductCategory(category_id));
            context.setVariable("products", productService.getProductsForCategory(category_id));

        } else if ((req.getParameter("categoryId") == null) && (req.getParameter("supplierId") != null)) {
            int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            context.setVariable("supplier", supplierDataStore.find(supplierId));
            context.setVariable("products", productService.getProductsForSupplier(supplierId));
        } else {
            context.setVariable("category", productService.getProductCategory(1));
            context.setVariable("products", productService.getProductsForCategory(1));
        }

        context.setVariable("allcategories", productCategoryDataStore.getAll());
        context.setVariable("allsuppliers", supplierDataStore.getAll());
        if (session != null) {
            context.setVariable("ses", true);
            try {
                databaseManager.setup();
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        // // Alternative setting of the template context
        // Map<String, Object> params = new HashMap<>();
        // params.put("category", productCategoryDataStore.find(1));
        // params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        // context.setVariables(params);
        engine.process("product/index.html", context, resp.getWriter());
    }

}