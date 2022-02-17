package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, NumberPicker.OnValueChangeListener {

    TextView productTypeText, totalAmountText, selectedQuantityText;
    Button managerBtn, buyBtn;
    NumberPicker quantityPicker;
    ListView productList;

    AlertDialog.Builder alertBuilder;

    Product mainProduct;
    ProductManager productManager;
    ProductBaseAdaptor productListAdapter;

    HistoryManager historyManager;

    int selectedQuantityAmount = -1;
    int quantityInStock = -1;

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

        alertBuilder = new AlertDialog.Builder(this);

        mainProduct = ((MyApp)getApplication()).mainProduct;
        productManager = ((MyApp)getApplication()).productManager;
        productListAdapter = new ProductBaseAdaptor(productManager.allProducts,this);

        historyManager = ((MyApp)getApplication()).historyManager;

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
            Intent managerActivityIntent = new Intent(this,ManagerActivity.class);
            startActivity(managerActivityIntent);
        } else if (id == R.id.buy_btn) {
            if (mainProduct == null || mainProduct.equals(new Product()) || selectedQuantityAmount == -1) {
                emptyFieldError();
            } else {
                if (quantityInStock >= selectedQuantityAmount) {
                    for (int i = 0; i < productManager.allProducts.size(); i++) {
                        if (mainProduct.getName().equals(productManager.allProducts.get(i).getName())) {
                            String buyAlertMessage = getString(R.string.buy_alert_message, mainProduct.getQuantity(), mainProduct.getName(), mainProduct.getTotal());
                            alertBuilder.setTitle(R.string.buy_alert_title).setMessage(buyAlertMessage);
                            AlertDialog buyAlert = alertBuilder.create();
                            buyAlert.show();

                            String purchaseDate = String.valueOf(new Date());
                            ProductHistory productHistory = new ProductHistory(mainProduct.getName(), mainProduct.getQuantity(), mainProduct.getTotal(), purchaseDate);
                            historyManager.addProductHistory(productHistory);

                            productManager.allProducts.get(i).decreaseQuantity(selectedQuantityAmount);
                            quantityInStock = productManager.allProducts.get(i).getQuantity();
                            productListAdapter = new ProductBaseAdaptor(productManager.allProducts,this);
                            productList.setAdapter(productListAdapter);
                            setMainProduct(new Product());
                            setSelectedQuantity(0);
                        }
                    }
                } else {
                    quantityError();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.product_list) {
            quantityInStock = productManager.allProducts.get(i).getQuantity();

            if (selectedQuantityAmount < 1) {
                setSelectedQuantity(1);
                quantityPicker.setValue(selectedQuantityAmount);
            }

            if (selectedQuantityAmount > quantityInStock) {
                quantityError();
            }

            setMainProduct(new Product(productManager.allProducts.get(i).getName(), selectedQuantityAmount, productManager.allProducts.get(i).getPrice()));
        }
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        int id = numberPicker.getId();

        if (id == R.id.quantity_picker) {
            setSelectedQuantity(numberPicker.getValue());

            if (mainProduct != null && !mainProduct.getName().equals("Select a product") && selectedQuantityAmount != -1) {
                if (selectedQuantityAmount <= quantityInStock) {
                    mainProduct.setQuantity(selectedQuantityAmount);
                    totalAmountText.setText(String.format(Locale.CANADA, "$%.2f", mainProduct.getTotal()));
                } else {
                    quantityError();
                }
            } else {
                emptyFieldError();
            }
        }
    }

    public void setSelectedQuantity(int quantity) {
        selectedQuantityAmount = quantity;
        selectedQuantityText.setText(String.format(Locale.CANADA, "%d", selectedQuantityAmount));
    }

    public void setMainProduct(Product product) {
        mainProduct = product;
        productTypeText.setText(mainProduct.getName());
        totalAmountText.setText(String.format(Locale.CANADA, "$%.2f", mainProduct.getTotal()));
    }

    public void quantityError() {
        if (quantityInStock > 0) {
            setSelectedQuantity(quantityInStock);
            quantityPicker.setValue(selectedQuantityAmount);
            Toast.makeText(this, getString(R.string.low_quantity_error, quantityInStock, mainProduct.getName()),Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, getString(R.string.no_quantity_error, mainProduct.getName()),Toast.LENGTH_LONG).show();
        }
    }

    public void emptyFieldError() {
        Toast.makeText(this, getString(R.string.empty_field_error),Toast.LENGTH_LONG).show();
    }
}