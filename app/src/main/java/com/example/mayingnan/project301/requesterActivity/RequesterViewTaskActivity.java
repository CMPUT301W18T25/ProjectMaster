package com.example.mayingnan.project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mayingnan.project301.R;

/**
 * Created by User on 2018/2/25.
 */


public class RequesterViewTaskActivity extends AppCompatActivity {
    private ListView bidList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_view_task);


        //settle edit button
        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditTaskActivity.class);
                startActivity(info2);

            }
        });

        //settle deleteTask button
        Button deleteTaskButton = (Button) findViewById(R.id.delete_button);
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditListActivity.class);
                startActivity(info2);

            }
        });

        //settle chooseBid button
        Button chooseBidButton = (Button) findViewById(R.id.choose_button);
        chooseBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterPayActivity.class);
                startActivity(info2);

            }
        });

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditListActivity.class);
                startActivity(info2);

            }
        });

        // settle click on bid list, no change to interface ,but the bid price get selected
        bidList = (ListView) findViewById(R.id.bid_list);
        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {

            }
        });
    }
}