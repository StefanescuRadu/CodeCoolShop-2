package com.codecool.shop.service;

import com.codecool.shop.controller.SuccesController;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CartItem implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(CartItem.class);
    Product product;
    int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getSubTotalPrice(){
        DecimalFormat df = new DecimalFormat("#.00");
        return Float.parseFloat(df.format(product.getDefaultPrice().multiply(new BigDecimal(quantity))));
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try{

            oos.writeInt(product.getId());
            oos.writeInt(quantity);
            LOG.info("Cart item written");
        }
        catch (IOException e){
            LOG.error("Cart item failed to be written");
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        try{

            product = ProductDaoMem.getInstance().find((int)in.readObject());
            quantity = (int)in.readObject();
            LOG.info("Item cart read!");
        }
        catch (IOException e){
            LOG.error("Item cart could not be read!");
        }
    }
    @Override
    public String toString() {
        return quantity + " * " + product.getName();
    }
}