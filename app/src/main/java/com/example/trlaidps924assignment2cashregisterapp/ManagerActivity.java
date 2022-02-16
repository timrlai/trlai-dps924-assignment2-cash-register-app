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