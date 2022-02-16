# DPS924 Mobile App Development - Android Assignment 2 - Cash Register App
## Student Name: Tim Lai
## Student Email Address: trlai@myseneca.ca
## Student Number: 056 106 123

## Models
```java
package com.example.trlaidps924assignment2cashregisterapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {
    String name;
    int quantity;
    BigDecimal price;

    public Product(String name, int quantity, double price) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }

    public Product() {
        this.setName("Select a product");
        this.setQuantity(0);
        this.setPrice(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void decreaseQuantity(int amount) {
        int reducedQuantity = this.quantity - amount;
        if (reducedQuantity >= 0) {
            this.quantity = reducedQuantity;
        }
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity = this.quantity + amount;
        }
    }

    public double getPrice() {
        return price.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public void setPrice(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        this.price = bd;
    }

    public double getTotal() {
        return BigDecimal.valueOf(getQuantity() * getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
```
```java
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
```

## Managers
```java
package com.example.trlaidps924assignment2cashregisterapp;

import java.util.ArrayList;

public class ProductManager {
    ArrayList<Product> allProducts = new ArrayList<>(0);

    public void addProduct(Product product){
        allProducts.add(product);
    }
}
```
```java
package com.example.trlaidps924assignment2cashregisterapp;

import java.util.ArrayList;

public class HistoryManager {
    ArrayList<ProductHistory> allProductHistories = new ArrayList<>(0);

    public void addProductHistory(ProductHistory productHistory){
        allProductHistories.add(productHistory);
    }
}
```

## Adapters
```java
package com.example.trlaidps924assignment2cashregisterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ProductBaseAdaptor extends BaseAdapter {
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
}
```
```java
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
```

## MyApp
```java
package com.example.trlaidps924assignment2cashregisterapp;

import android.app.Application;

public class MyApp extends Application {
    ProductManager productManager = new ProductManager();
    Product mainProduct = new Product();
    Product[] availableProducts = new Product[] {
            new Product("Pants", 10, 20.44),
            new Product("Shoes", 100, 10.44),
            new Product("Hat", 30, 5.90)
    };
    HistoryManager historyManager = new HistoryManager();

    @Override
    public void onCreate() {
        super.onCreate();

        for (Product product : this.availableProducts) {
            productManager.addProduct(product);
        }
    }
}
```

## Activities
```java
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
                            String buyAlertMessage = getString(R.string.buy_alert_message, mainProduct.getQuantity(), mainProduct.getName(), mainProduct.getPrice());
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
```
```java
package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagerActivity extends AppCompatActivity implements View.OnClickListener {
    Button historyBtn, restockBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        historyBtn = findViewById(R.id.history_btn);
        restockBtn = findViewById(R.id.restock_btn);
        cancelBtn = findViewById(R.id.manager_cancel_btn);

        historyBtn.setOnClickListener(this);
        restockBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.history_btn) {
            Intent historyActivityIntent = new Intent(this,HistoryActivity.class);
            startActivity(historyActivityIntent);
        } else if (id == R.id.restock_btn) {
            Intent restockActivityIntent = new Intent(this,RestockActivity.class);
            startActivity(restockActivityIntent);
        } else if (id == R.id.manager_cancel_btn) {
            Intent mainActivityIntent = new Intent(this,MainActivity.class);
            startActivity(mainActivityIntent);
        }
    }
}
```
```java
package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView historyList;

    HistoryManager historyManager;
    HistoryBaseAdapter historyListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyList = findViewById(R.id.history_list);

        historyManager = ((MyApp)getApplication()).historyManager;
        historyListAdapter = new HistoryBaseAdapter(historyManager.allProductHistories,this);

        historyList.setAdapter(historyListAdapter);
        historyList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int id = adapterView.getId();

        if (id == R.id.history_list) {
            Intent historyDetailActivityIntent = new Intent(this,HistoryDetailActivity.class);
            historyDetailActivityIntent.putExtra("productType", historyManager.allProductHistories.get(i).getName());
            historyDetailActivityIntent.putExtra("totalPrice", historyManager.allProductHistories.get(i).getTotalPrice());
            historyDetailActivityIntent.putExtra("purchasedDate", historyManager.allProductHistories.get(i).getPurchaseDate());
            startActivity(historyDetailActivityIntent);
        }
    }
}
```
```java
package com.example.trlaidps924assignment2cashregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

public class HistoryDetailActivity extends AppCompatActivity {
    TextView productTypeText, totalPriceText, purchasedDateText;

    String productType, purchasedDate;
    Double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        productTypeText = findViewById(R.id.history_detail_product);
        totalPriceText = findViewById(R.id.history_detail_total_price);
        purchasedDateText = findViewById(R.id.history_detail_purchased_date);

        productType = getIntent().getExtras().getString("productType");
        totalPrice = getIntent().getExtras().getDouble("totalPrice");
        purchasedDate = getIntent().getExtras().getString("purchasedDate");

        productTypeText.setText(getString(R.string.detail_product, productType));
        totalPriceText.setText(getString(R.string.detail_price, String.format(Locale.CANADA, " $%.2f", totalPrice)));
        purchasedDateText.setText(getString(R.string.detail_date, purchasedDate));
    }
}
```
```java
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
```

## Layouts
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/product_type"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/product_type"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@+id/guideline_top"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@id/manager_btn"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/manager_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/manager_btn"
        android:textSize="14sp"
        android:textColor="@color/white"
        android:background="@drawable/btn_bg"
        app:layout_constraintBottom_toTopOf="@+id/guideline_top"
        app:layout_constraintEnd_toEndOf="@id/guideline_right"
        app:layout_constraintLeft_toRightOf="@id/product_type"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <NumberPicker
        android:id="@+id/quantity_picker"
        android:layout_width="70dp"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toTopOf="@+id/guideline_middle"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@id/total_amount"
        app:layout_constraintStart_toStartOf="@id/guideline_left"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/total_amount"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/total"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@+id/guideline_middle"
        app:layout_constraintEnd_toEndOf="@id/guideline_right"
        app:layout_constraintLeft_toRightOf="@id/quantity_picker"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/guideline_top" />

    <TextView
        android:id="@+id/selected_quantity"
        android:layout_width="70dp"
        android:layout_height="wrap_content"
        android:text="@string/selected_quantity"
        android:textSize="18sp"
        android:textAlignment="center"
        app:layout_constraintBottom_toTopOf="@+id/guideline_bottom"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@id/buy_btn"
        app:layout_constraintStart_toStartOf="@id/guideline_left"
        app:layout_constraintTop_toBottomOf="@id/guideline_middle" />

    <Button
        android:id="@+id/buy_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="10dp"
        android:text="@string/buy_btn"
        android:textSize="14sp"
        android:textColor="@color/white"
        android:background="@drawable/btn_bg"
        app:layout_constraintBottom_toTopOf="@+id/guideline_bottom"
        app:layout_constraintEnd_toEndOf="@id/guideline_right"
        app:layout_constraintLeft_toRightOf="@id/selected_quantity"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@id/guideline_middle" />

    <ListView
        android:id="@+id/product_list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/guideline_bottom" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.07" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.93" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_top"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.18" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_middle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.45" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_bottom"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.65" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/product_list_item_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="@string/product_type"
        android:textColor="@color/dark_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_product_item_middle"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/guideline_product_item_right"
        app:layout_constraintStart_toStartOf="@id/guideline_product_item_left"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/product_list_item_quantity"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="@string/selected_quantity"
        android:textColor="@color/light_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_product_item_middle"
        app:layout_constraintLeft_toLeftOf="@id/guideline_product_item_right"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="@id/guideline_product_item_right"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/product_list_item_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/price"
        android:textColor="@color/light_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_product_item_bottom"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/guideline_product_item_right"
        app:layout_constraintStart_toStartOf="@id/guideline_product_item_left"
        app:layout_constraintTop_toTopOf="@id/guideline_product_item_middle" />

    <View
        android:id="@+id/product_list_item_divider"
        android:layout_width="match_parent"
        android:layout_height= "2dp"
        android:layout_marginBottom="10dp"
        android:layout_marginTop="10dp"
        android:background="@color/light_teal"
        app:layout_constraintTop_toBottomOf="@id/guideline_product_item_bottom" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_product_item_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.04" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_product_item_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.7" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_product_item_middle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_product_item_bottom"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ManagerActivity">

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <Button
            android:id="@+id/history_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:layout_marginBottom="20dp"
            android:text="@string/history_btn"
            android:textSize="14sp"
            android:textColor="@color/white"
            android:background="@drawable/btn_bg" />

        <Button
            android:id="@+id/restock_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:layout_marginBottom="20dp"
            android:text="@string/restock_btn"
            android:textSize="14sp"
            android:textColor="@color/white"
            android:background="@drawable/btn_bg" />

        <Button
            android:id="@+id/manager_cancel_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:text="@string/cancel_btn"
            android:textSize="14sp"
            android:textColor="@color/white"
            android:background="@drawable/btn_bg" />

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".HistoryActivity">

    <ListView
        android:id="@+id/history_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/history_list_item_name"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="@string/product_type"
        android:textColor="@color/dark_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_history_item_middle"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/guideline_history_item_right"
        app:layout_constraintStart_toStartOf="@id/guideline_history_item_left"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/history_list_item_total_price"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="@string/price"
        android:textColor="@color/light_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_history_item_middle"
        app:layout_constraintLeft_toLeftOf="@id/guideline_history_item_right"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="@id/guideline_history_item_right"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView
        android:id="@+id/history_list_item_quantity"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/selected_quantity"
        android:textColor="@color/light_teal"
        android:textSize="18sp"
        app:layout_constraintBottom_toTopOf="@id/guideline_history_item_bottom"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toLeftOf="@+id/guideline_history_item_right"
        app:layout_constraintStart_toStartOf="@id/guideline_history_item_left"
        app:layout_constraintTop_toTopOf="@id/guideline_history_item_middle" />

    <View
        android:id="@+id/history_list_item_divider"
        android:layout_width="match_parent"
        android:layout_height= "2dp"
        android:layout_marginBottom="10dp"
        android:layout_marginTop="10dp"
        android:background="@color/light_teal"
        app:layout_constraintTop_toBottomOf="@id/guideline_history_item_bottom" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_history_item_left"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.04" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_history_item_right"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintGuide_percent="0.7" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_history_item_middle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_history_item_bottom"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".HistoryDetailActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/history_detail_product"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:textAlignment="center"
            android:text="@string/detail_product"
            android:textColor="@color/dark_teal"
            android:textSize="18sp" />

        <TextView
            android:id="@+id/history_detail_total_price"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:textAlignment="center"
            android:text="@string/detail_price"
            android:textColor="@color/light_teal"
            android:textSize="18sp" />

        <TextView
            android:id="@+id/history_detail_purchased_date"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="10dp"
            android:textAlignment="center"
            android:text="@string/detail_date"
            android:textColor="@color/light_teal"
            android:textSize="18sp" />

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".RestockActivity">

    <TextView
        android:id="@+id/restock_product_type"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/product_type"
        android:textSize="18sp"
        app:layout_constraintBottom_toBottomOf="@id/guideline_top"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <EditText
        android:id="@+id/restock_new_quantity"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/new_quantity"
        app:layout_constraintBottom_toBottomOf="@id/guideline_middle"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@id/guideline_top" />

    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintBottom_toBottomOf="@id/guideline_bottom"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="@id/guideline_middle">

        <Button
            android:id="@+id/restock_ok_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:layout_marginEnd="20dp"
            android:text="@string/ok_btn"
            android:textSize="14sp"
            android:textColor="@color/white"
            android:background="@drawable/btn_bg" />

        <Button
            android:id="@+id/restock_cancel_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:text="@string/cancel_btn"
            android:textSize="14sp"
            android:textColor="@color/white"
            android:background="@drawable/btn_bg" />

    </LinearLayout>

    <ListView
        android:id="@+id/stock_list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/guideline_bottom" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_top"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.1" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_middle"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.2" />

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/guideline_bottom"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="horizontal"
        app:layout_constraintGuide_percent="0.4" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

## Strings
```xml
<resources>
    <string name="app_name">Assignment 2 - Cash Register App</string>
    <string name="product_type">Product Type</string>
    <string name="manager_btn">Manager</string>
    <string name="total">Total</string>
    <string name="selected_quantity">Quantity</string>
    <string name="buy_btn">Buy</string>
    <string name="price">Price</string>
    <string name="buy_alert_title">Thank you for your purchase!</string>
    <string name="buy_alert_message">You purchased %1$d %2$s for $%3$.2f.</string>
    <string name="history_btn">History</string>
    <string name="restock_btn">Restock</string>
    <string name="detail_product">Product: %1$s</string>
    <string name="detail_price">Price: %1$s</string>
    <string name="detail_date">Purchase Date: %1$s</string>
    <string name="restock_product">Restock Product: %1$s</string>
    <string name="new_quantity">Add New Quantity</string>
    <string name="ok_btn">OK</string>
    <string name="cancel_btn">Cancel</string>
    <string name="restock_alert_title">Product %1$s has been restocked!</string>
    <string name="restock_alert_message">You restocked %1$d %2$s for a total stock of %3$d.</string>
    <string name="empty_field_error">All fields are required!!!</string>
    <string name="low_quantity_error">Only %1$d %2$s in stock!</string>
    <string name="no_quantity_error">There are no %1$s in stock!</string>
    <string name="not_integer_error">Value must be an integer!!!</string>
</resources>
```
