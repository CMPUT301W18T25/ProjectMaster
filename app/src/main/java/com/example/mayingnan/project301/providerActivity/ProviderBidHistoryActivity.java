package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;

import java.util.ArrayList;

public class ProviderBidHistoryActivity extends AppCompatActivity {
    private Button backButton;
    private ListView bidHistoryList;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_bid_history);

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderBidHistoryActivity.this, ProviderMainActivity.class);
                startActivity(info2);

            }
        });

        // settle click on bid history list
        // case1: jump tp finish
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskFinishActivity.class);
                info1.putExtra("info", index);
                startActivity(info1);
            }
        });

        // settle click on bid history list
        // case2: jump tp bid
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                info1.putExtra("info", index);
                startActivity(info1);
            }
        });




    }
}
