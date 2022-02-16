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