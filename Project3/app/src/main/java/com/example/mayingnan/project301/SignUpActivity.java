

package com.example.mayingnan.project301;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;


public class SignUpActivity extends AppCompatActivity {
    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private Button signUpButton;
    private UserListControl userListControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
    }

    public void signup(){

    }

}