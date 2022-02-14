package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnValueChangeListener {

    TextView productTypeText, totalAmountText, selectedQuantityText;
    Button managerBtn, buyBtn;
    NumberPicker quantityPicker;
    ListView productList;

    Product myProduct;
    ProductManager productManager;
    ProductBaseAdaptor productListAdapter;

    ArrayList<Product> allProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productTypeText = findViewById(R.id.product_type);
        totalAmountText = findViewById(R.id.total_amount);
        selectedQuantityText = findViewById(R.id.selected_quantity);
        managerBtn = findViewById(R.id.manager_btn);
        buyBtn = findViewById(R.id.buy_btn);
        quantityPicker = findViewById(R.id.quantity_picker);
        productList = findViewById(R.id.product_list);

        myProduct = ((MyApp)getApplication()).mainProduct;
        productManager = ((MyApp)getApplication()).productManager;
        allProducts = productManager.allProducts;
        productListAdapter = new ProductBaseAdaptor(allProducts,this);

        managerBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);

        quantityPicker.setMinValue(0);
        quantityPicker.setMaxValue(1000);
        quantityPicker.setOnValueChangedListener(this);

        productList.setAdapter(productListAdapter);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.manager_btn) {

        }  else if (id == R.id.buy_btn) {

        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        int id = numberPicker.getId();

        if (id == R.id.quantity_picker) {

        }
    }
}