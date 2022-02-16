package com.example.trlaidps924assignment2cashregisterapp;

import java.util.ArrayList;

public class HistoryManager {
    ArrayList<ProductHistory> allProductHistories = new ArrayList<>(0);

    public void addProductHistory(ProductHistory productHistory){
        allProductHistories.add(productHistory);
    }
}
