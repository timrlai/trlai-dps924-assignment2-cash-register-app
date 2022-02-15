package com.example.trlaidps924assignment2cashregisterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ProductBaseAdaptor extends BaseAdapter implements View.OnClickListener {
    ArrayList<Product> productList;
    Context context;

    public ProductBaseAdaptor(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.productList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.product_list_item_row, viewGroup, false);
        }

        TextView name = view.findViewById(R.id.product_list_item_name);
        TextView quantity = view.findViewById(R.id.product_list_item_quantity);
        TextView price = view.findViewById(R.id.product_list_item_price);

        name.setText(productList.get(i).getName());
        quantity.setText(String.valueOf(productList.get(i).getQuantity()));
        price.setText(String.format(Locale.CANADA, "$%.2f", productList.get(i).getPrice()));

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}
