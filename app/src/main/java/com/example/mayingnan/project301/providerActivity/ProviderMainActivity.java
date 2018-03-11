package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.requesterActivity.RequesterViewTaskActivity;

import java.util.ArrayList;

public class ProviderMainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button viewOnMapButton;
    private Button bidHistoyButton;
    private ListView availablelist;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_main);

        //settle viewOnMap button
        Button viewOnMapButton = (Button) findViewById(R.id.provider_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ProviderMapActivity.class);
                startActivity(intent);
            }
        });

        //settle bidHistory button
        Button bidHistoryButton = (Button) findViewById(R.id.provider_bid_button);
        bidHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ProviderBidHistoryActivity.class);
                startActivity(intent);
            }
        });


        // settle click on list
        availablelist = (ListView) findViewById(R.id.provider_list);
        availablelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(ProviderMainActivity.this, RequesterViewTaskActivity.class);
                info1.putExtra("info", index);
                startActivity(info1);
            }
        });


        // to do search button





    }
    public void onSearch(){}

    public void showMap(){}

    public void showHistory(){}

}
