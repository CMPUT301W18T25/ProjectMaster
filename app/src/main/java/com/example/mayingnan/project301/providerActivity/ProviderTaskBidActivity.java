package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.R;

import java.util.ArrayList;

public class ProviderTaskBidActivity extends AppCompatActivity {

    private TextView taskDetail;
    private TextView taskTime;
    private TextView taskLocation;
    private TextView taskLowestbid;
    private EditText taskMybid;
    private ListView BidListView;
    private ArrayList<Bid> listOfBids;
    private Button BackButton;
    private Button BidButton;
    private Button CancelButton;
    private String userName;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_bid);
        final Intent intent = getIntent();
        userName = intent.getExtras().get("userName").toString();

        //settle cancel button : cancel the old bid
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userName",userName);
                startActivity(info2);

            }
        });



        //settle bid button : add the bid to list and jump back to history
        Button bidButton = (Button) findViewById(R.id.bid_button);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                startActivity(info2);

            }
        });


        //settle back button : back to history, no change
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                startActivity(info2);

            }
        });
    }

    public void getTaskDetails(){}
    public void submitMyBid(){}
    public void cancelMyBid(){}



}
