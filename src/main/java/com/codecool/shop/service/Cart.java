package com.codecool.shop.service;
import com.codecool.shop.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
public class Cart implements Serializable {
    private final UUID orderID;
    List<CartItem> cartItems = new ArrayList<>();
    private static Cart instance;
    private String email;
    private static final Logger LOG = LoggerFactory.getLogger(Cart.class);

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }
    private Cart() {
        this.orderID = UUID.randomUUID();
    }
//    public Cart(String email,List<CartItem> cartItems,UUId id){
//        this.email = email;
//        this.cartItems = cartItems;
//    }
    public UUID getOrderID() {
        return orderID;
    }


    public void addItem(Product product) {
        System.out.println("Adding " + product.getName());
        CartItem toAdd = new CartItem(product, 1);
        for (CartItem itm : cartItems) {
            if (itm.getProduct().getId() == toAdd.getProduct().getId()) {
                itm.setQuantity(itm.getQuantity() + toAdd.getQuantity());
                return;
            }
        }
        cartItems.add(toAdd);
    }


    public void removeItem(Product product) {
        LOG.info("Removing " + product.getName());
        cartItems.removeIf(cItem -> cItem.getProduct().getId() == product.getId());
    }

    public void clear(){
        cartItems.clear();
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        try{

            oos.writeObject(orderID);
            oos.writeObject(cartItems);
            oos.close();
            LOG.info("Cart written!");
        }
        catch (IOException e){
            LOG.error("Cart could not be written!");
        }
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        try{
            instance = (Cart)in.readObject();
            LOG.info("Cart read!");
        }
        catch (IOException e){
            LOG.error("Cart could not be read!");
        }

    }
    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public float getTotalPrice() {
        float cartPrice = 0;
        for (CartItem item : cartItems) {
            cartPrice += item.getSubTotalPrice();
        }
        return cartPrice;
    }
    @Override
    public String toString() {
        return "Cart{" +
                "orderID=" + orderID +
                ", cartItems=" + cartItems +
                '}';
    }
}