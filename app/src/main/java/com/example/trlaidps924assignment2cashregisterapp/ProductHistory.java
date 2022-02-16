package com.example.trlaidps924assignment2cashregisterapp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

public class ProductHistory extends Product {
    BigDecimal totalPrice;
    String purchaseDate;

    public ProductHistory(String name, int quantity, double totalPrice, String purchaseDate) {
        super(name, quantity, totalPrice / quantity);
        this.setTotalPrice(totalPrice);
        this.setPurchaseDate(purchaseDate);
    }

    public ProductHistory() {
        this.setTotalPrice(0);
        this.setPurchaseDate("No date");
    }

    public double getTotalPrice() {
        return totalPrice.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setTotalPrice(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        this.totalPrice = bd;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
