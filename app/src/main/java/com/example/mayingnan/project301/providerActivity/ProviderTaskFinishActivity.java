package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;

import java.util.ArrayList;
import java.util.Map;

public class ProviderTaskFinishActivity extends AppCompatActivity {
    private Map providerMap;
    private ArrayList tasklist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_finish);

        //settle back button : jump back to history
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskFinishActivity.this, ProviderBidHistoryActivity.class);
                startActivity(info2);

            }
        });
    }

    public void showList(){}
    public void editInfo(){}
    public void OnClick(){}


}
