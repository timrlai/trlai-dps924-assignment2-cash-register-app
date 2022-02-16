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