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

@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderMainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button editProfileButton;
    private Button viewOnMapButton;
    private Button bidHistoryButton;
    private ListView availablelist;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private String userName;

    private String userId;



    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_main);
        final Intent intent = getIntent();

        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        //need to load task and store in the list
        //use adapter to show on UI


        //settle viewOnMap button
        viewOnMapButton = (Button) findViewById(R.id.provider_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ProviderMapActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        //settle bidHistory button
        bidHistoryButton = (Button) findViewById(R.id.provider_bid_button);
        bidHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ProviderBidHistoryActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        //settle editProfile button
        editProfileButton = (Button) findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(ProviderMainActivity.this, ProviderEditInfoActivity.class);
                intent.putExtra("userId",userId);
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
                info1.putExtra("userId",userId);
                startActivity(info1);
            }
        });


        // to do search button
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //need search code
            }
        });




    }
    public void onSearch(){}

    public void showMap(){}

    public void showHistory(){}

}
