package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;

public class ProviderMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_on_map);


        //settle showlist button
        Button showListButton = (Button) findViewById(R.id.show_list);
        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderMapActivity.this, ProviderMainActivity.class);
                startActivity(info2);

            }
        });

        //settle editprofile button
        Button editInfoButton = (Button) findViewById(R.id.edit_info);
        editInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderMapActivity.this, ProviderEditInfoActivity.class);
                startActivity(info2);

            }
        });
    }
}
