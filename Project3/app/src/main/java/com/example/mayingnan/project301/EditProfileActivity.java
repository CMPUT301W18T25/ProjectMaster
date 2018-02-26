package com.example.mayingnan.project301;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private Button saveButton;
    private Button backButton;
    private UserListControl userListControl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);
    }

    public void  saveButton(){

    }

    public void backButton(){

    }

}
