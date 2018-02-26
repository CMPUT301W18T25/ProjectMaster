package com.example.mayingnan.project301.providerActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.R;

import java.util.ArrayList;

public class MakeABidActivity extends AppCompatActivity {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_info);
    }

    public void getTaskDetails(){}
    public void submitMyBid(){}
    public void cancelMyBid(){}


}
