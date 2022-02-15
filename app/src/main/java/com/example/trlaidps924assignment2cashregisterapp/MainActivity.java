package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, NumberPicker.OnValueChangeListener {

    TextView productTypeText, totalAmountText, selectedQuantityText;
    Button managerBtn, buyBtn;
    NumberPicker quantityPicker;
    ListView productList;

    Product mainProduct;
    ProductManager productManager;
    ProductBaseAdaptor productListAdapter;

    ArrayList<Product> allProducts;
    Integer selectedQuantityAmount;
    Integer quantityInStock;

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

        mainProduct = ((MyApp)getApplication()).mainProduct;
        productManager = ((MyApp)getApplication()).productManager;
        allProducts = productManager.allProducts;
        productListAdapter = new ProductBaseAdaptor(allProducts,this);

        managerBtn.setOnClickListener(this);
        buyBtn.setOnClickListener(this);

        quantityPicker.setMinValue(0);
        quantityPicker.setMaxValue(1000);
        quantityPicker.setOnValueChangedListener(this);

        productList.setAdapter(productListAdapter);
        productList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.manager_btn) {

        } else if (id == R.id.buy_btn) {
            if (mainProduct == null || selectedQuantityAmount == null) {
                emptyFieldError();
            } else {
                for (int i = 0; i < allProducts.size(); i++) {
                    if (mainProduct.getName().equals(allProducts.get(i).getName())) {
                        allProducts.get(i).decreaseQuantity(selectedQuantityAmount);
                    }
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.product_list) {
            quantityInStock = allProducts.get(i).getQuantity();

            if (selectedQuantityAmount == 0) {
                selectedQuantityAmount = 1;
                quantityPicker.setValue(selectedQuantityAmount);
            }

            if (selectedQuantityAmount > quantityInStock) {
                quantityError();
            }

            mainProduct = new Product(allProducts.get(i).getName(), selectedQuantityAmount, allProducts.get(i).getPrice());
            productTypeText.setText(mainProduct.getName());
            totalAmountText.setText(String.format(Locale.CANADA, "$%.2f", mainProduct.getTotal()));
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        int id = numberPicker.getId();

        if (id == R.id.quantity_picker && mainProduct != null) {
            if (numberPicker.getValue() <= quantityInStock) {
                selectedQuantityAmount = numberPicker.getValue();
                mainProduct.setQuantity(selectedQuantityAmount);
                selectedQuantityText.setText(String.format(Locale.CANADA, "%d", selectedQuantityAmount));
                totalAmountText.setText(String.format(Locale.CANADA, "$%.2f", mainProduct.getTotal()));
            } else {
                quantityError();
            }
        }
    }

    public void quantityError() {
        quantityPicker.setValue(quantityInStock);
        selectedQuantityAmount = quantityInStock;
        selectedQuantityText.setText(String.format(Locale.CANADA, "%d", quantityInStock));
        Toast.makeText(this, "Only " + quantityInStock + " " + mainProduct.getName() + " in stock!",Toast.LENGTH_LONG).show();
    }

    public void emptyFieldError() {
        Toast.makeText(this, "All fields are required!!!",Toast.LENGTH_LONG).show();
    }
}