package com.example.mayingnan.project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;

/**
 * @classname : RequesterMainActivity
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterMainActivity extends AppCompatActivity {


    private String userId;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_main);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        //settle postNewTask button
        Button postNewTaskButton = (Button) findViewById(R.id.post_button);
        postNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterPostTaskActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });




        //settle view button
        Button viewButton = (Button) findViewById(R.id.edit_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle editProfile button
        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditInfoActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });


    }
}