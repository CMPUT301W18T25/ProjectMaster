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



public class RequesterPostTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_post_task);


        //settle submit button
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterEditListActivity.class);
                startActivity(info2);

            }
        });


        //settle cancel button
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterMainActivity.class);
                startActivity(info2);

            }
        });
    }
}