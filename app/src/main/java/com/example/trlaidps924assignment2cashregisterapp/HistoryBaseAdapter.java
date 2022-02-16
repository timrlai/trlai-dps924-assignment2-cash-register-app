package com.example.trlaidps924assignment2cashregisterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class HistoryBaseAdapter extends BaseAdapter {
    ArrayList<ProductHistory> productHistoryList;
    Context context;

    public HistoryBaseAdapter(ArrayList<ProductHistory> productHistoryList, Context context) {
        this.productHistoryList = productHistoryList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.productHistoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.productHistoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.history_list_item_row, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.history_list_item_name);
        TextView totalPrice = view.findViewById(R.id.history_list_item_total_price);
        TextView quantity = view.findViewById(R.id.history_list_item_quantity);

        name.setText(productHistoryList.get(i).getName());
        totalPrice.setText(String.format(Locale.CANADA, "$%.2f", productHistoryList.get(i).getTotalPrice()));
        quantity.setText(String.valueOf(productHistoryList.get(i).getQuantity()));

        return view;
    }
}
