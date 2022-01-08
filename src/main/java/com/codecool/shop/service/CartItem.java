package com.codecool.shop.service;

import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.model.Product;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CartItem implements Serializable {

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
        DecimalFormat df = new DecimalFormat("$#.00");
        return Float.parseFloat(df.format(product.getDefaultPrice().multiply(new BigDecimal(quantity))));
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeObject(product.getId());
        oos.writeObject(quantity);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        product = ProductDaoMem.getInstance().find((int)in.readObject());
        quantity = (int)in.readObject();
    }
}