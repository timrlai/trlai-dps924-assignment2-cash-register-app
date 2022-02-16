package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RestockActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, View.OnTouchListener, View.OnKeyListener {
    TextView productTypeText;
    EditText newQuantityEdit;
    Button okBtn, cancelBtn;
    ListView stockList;

    AlertDialog.Builder alertBuilder;

    Product mainProduct;
    ProductManager productManager;
    ProductBaseAdaptor productListAdapter;

    boolean productSelected = false;
    int selectedQuantityAmount = -1;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restock);

        productTypeText = findViewById(R.id.restock_product_type);
        newQuantityEdit = findViewById(R.id.restock_new_quantity);
        okBtn = findViewById(R.id.restock_ok_btn);
        cancelBtn = findViewById(R.id.restock_cancel_btn);
        stockList = findViewById(R.id.stock_list);

        alertBuilder = new AlertDialog.Builder(this);

        mainProduct = ((MyApp)getApplication()).mainProduct;
        productManager = ((MyApp)getApplication()).productManager;
        productListAdapter = new ProductBaseAdaptor(productManager.allProducts,this);

        productTypeText.setText(getString(R.string.restock_product, getString(R.string.product_type)));

        newQuantityEdit.setOnTouchListener(this);
        newQuantityEdit.setOnKeyListener(this);

        okBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        stockList.setAdapter(productListAdapter);
        stockList.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.restock_ok_btn) {
            if (!productSelected || mainProduct.equals(new Product()) || selectedQuantityAmount < 0) {
                emptyFieldError();
            } else {
                for (int i = 0; i < productManager.allProducts.size(); i++) {
                    if (mainProduct.getName().equals(productManager.allProducts.get(i).getName())) {
                        productManager.allProducts.get(i).increaseQuantity(selectedQuantityAmount);

                        String restockAlertTitle = getString(R.string.restock_alert_title, mainProduct.getName());
                        String restockAlertMessage = getString(R.string.restock_alert_message, selectedQuantityAmount, mainProduct.getName(), productManager.allProducts.get(i).getQuantity());
                        alertBuilder.setTitle(restockAlertTitle).setMessage(restockAlertMessage);
                        AlertDialog restockAlert = alertBuilder.create();
                        restockAlert.show();

                        productListAdapter = new ProductBaseAdaptor(productManager.allProducts,this);
                        stockList.setAdapter(productListAdapter);
                        setMainProduct(new Product());
                        productSelected = false;
                        selectedQuantityAmount = -1;
                        newQuantityEdit.setText("");
                    }
                }
            }
        } else if (id == R.id.restock_cancel_btn) {
            Intent managerActivityIntent = new Intent(this,ManagerActivity.class);
            startActivity(managerActivityIntent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.stock_list) {
            setMainProduct(new Product(productManager.allProducts.get(i).getName(), productManager.allProducts.get(i).getQuantity(), productManager.allProducts.get(i).getPrice()));
            productSelected = true;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();

        if (id == R.id.restock_new_quantity) {
            if (newQuantityEdit.getText().toString().equals(getString(R.string.new_quantity))) {
                newQuantityEdit.setText("");
            }
        }

        return view.performClick();
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        int id = view.getId();

        if (id == R.id.restock_new_quantity) {
            if (isInteger(newQuantityEdit.getText().toString())) {
                selectedQuantityAmount = Integer.parseInt(newQuantityEdit.getText().toString());
            } else {
                notAnIntegerError();
            }

            return true;
        }

        return false;
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void setMainProduct(Product product) {
        mainProduct = product;
        productTypeText.setText(getString(R.string.restock_product, mainProduct.getName()));
    }

    public void emptyFieldError() {
        Toast.makeText(this, getString(R.string.empty_field_error),Toast.LENGTH_LONG).show();
    }

    public void notAnIntegerError() {
        Toast.makeText(this, getString(R.string.not_integer_error),Toast.LENGTH_LONG).show();
    }
}