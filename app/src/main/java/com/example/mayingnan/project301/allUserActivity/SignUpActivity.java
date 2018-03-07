

package com.example.mayingnan.project301.allUserActivity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.controller.UserListController;


public class SignUpActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private Button signUpButton;
    private UserListController userListControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void signup(){

    }

}