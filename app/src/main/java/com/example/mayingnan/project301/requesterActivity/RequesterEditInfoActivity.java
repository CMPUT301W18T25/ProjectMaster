package com.example.mayingnan.project301.requesterActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.controller.UserListController;

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
    }

    public void  saveButton(){

    }

    public void backButton(){

    }

}
