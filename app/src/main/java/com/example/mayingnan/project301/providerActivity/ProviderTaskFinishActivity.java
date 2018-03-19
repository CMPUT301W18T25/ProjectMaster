package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * @classname : ProviderTaskFinishActivity
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
public class ProviderTaskFinishActivity extends AppCompatActivity {
    private Map providerMap;
    private ArrayList tasklist;
    private String userName;
    private String userId;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_finish);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("useId").toString();

        //settle back button : jump back to history
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskFinishActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
    }

    public void showList(){}
    public void editInfo(){}
    public void OnClick(){}


}
