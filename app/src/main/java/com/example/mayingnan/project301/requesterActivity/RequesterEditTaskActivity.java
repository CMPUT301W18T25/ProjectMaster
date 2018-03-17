package com.example.mayingnan.project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;

/**
 * Created by User on 2018/2/25.
 */



public class RequesterEditTaskActivity extends AppCompatActivity {
    private String userId;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_edit_task);
        final Intent intent = getIntent();
        userName = intent.getExtras().get("userId").toString();


        //settle save button
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskActivity.class);
                info2.putExtra("userId",userName);
                startActivity(info2);

            }
        });


        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskActivity.class);
                info2.putExtra("userId",userName);
                startActivity(info2);

            }
        });
    }
}