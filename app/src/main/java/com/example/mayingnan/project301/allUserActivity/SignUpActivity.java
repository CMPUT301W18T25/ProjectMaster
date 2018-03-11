

package com.example.mayingnan.project301.allUserActivity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.controller.UserListController;


public class SignUpActivity extends AppCompatActivity {

    private SignUpActivity activity = this;

    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwardText;
    private Button checkValidationButton;
    private UserListController userListControl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button checkValidationButton = (Button) findViewById(R.id.check_validation);
        usernameText = (EditText) findViewById(R.id.signup_name);
        emailText = (EditText) findViewById(R.id.signup_email);
        mobileText = (EditText) findViewById(R.id.signup_phone);
        passwardText = (EditText) findViewById(R.id.signup_password);

        checkValidationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                String enterName = usernameText.getText().toString();
                String enterEmail = emailText.getText().toString();
                String enterPhone= mobileText.getText().toString();
                String enterPassward = passwardText.getText().toString();
                //deal with those variables
                Intent intent = new Intent(activity, UserCharacterActivity.class);
                startActivity(intent);
            }
        });
    }



    public void signup(){

    }

}