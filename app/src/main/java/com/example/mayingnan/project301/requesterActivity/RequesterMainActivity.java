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


public class RequesterMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_main);

        //settle postNewTask button
        Button postNewTaskButton = (Button) findViewById(R.id.post_button);
        postNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterPostTaskActivity.class);
                startActivity(info2);

            }
        });



        //settle view button
        Button viewButton = (Button) findViewById(R.id.edit_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditListActivity.class);
                startActivity(info2);

            }
        });

        //settle editProfile button
        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditInfoActivity.class);
                startActivity(info2);

            }
        });


    }
}