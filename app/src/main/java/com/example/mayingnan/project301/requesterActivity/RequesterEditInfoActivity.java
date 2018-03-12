package com.example.mayingnan.project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.controller.UserListController;
import com.example.mayingnan.project301.providerActivity.ProviderEditInfoActivity;
import com.example.mayingnan.project301.providerActivity.ProviderMainActivity;
import com.example.mayingnan.project301.providerActivity.ProviderMapActivity;

public class RequesterEditInfoActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private Button saveButton;
    private Button backButton;
    private UserListController userListControl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);

        //settle save button
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditInfoActivity.this, RequesterMainActivity.class);
                startActivity(info2);

            }
        });


        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditInfoActivity.this, RequesterMainActivity.class);
                startActivity(info2);

            }
        });
    }

    public void  saveButton(){

    }

    public void backButton(){

    }

}
