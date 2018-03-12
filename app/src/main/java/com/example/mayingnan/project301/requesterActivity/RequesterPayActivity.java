

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


public class RequesterPayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_pay);

        //settle pay button
        Button payButton = (Button) findViewById(R.id.pay_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPayActivity.this, RequesterEditListActivity.class);
                startActivity(info2);

            }
        });

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPayActivity.this, RequesterViewTaskActivity.class);
                startActivity(info2);

            }
        });
    }
}