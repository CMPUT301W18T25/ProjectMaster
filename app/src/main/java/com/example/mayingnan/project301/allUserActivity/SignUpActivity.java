package com.example.mayingnan.project301.allUserActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.controller.UserListController;


public class SignUpActivity extends AppCompatActivity {

    private SignUpActivity activity = this;

    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwardText;
    private Button checkValidationButton;
    private UserListController userListControl;
    private User newUser;
    private UserListController userListController;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button checkValidationButton = (Button) findViewById(R.id.check_validation);
        usernameText = (EditText) findViewById(R.id.signup_name);
        emailText = (EditText) findViewById(R.id.signup_email);
        mobileText = (EditText) findViewById(R.id.signup_phone);
        passwardText = (EditText) findViewById(R.id.signup_password);
        this.context = getApplicationContext();

        checkValidationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                String enterName = usernameText.getText().toString();
                String enterEmail = emailText.getText().toString();
                String enterPhone= mobileText.getText().toString();
                String enterPassward = passwardText.getText().toString();
                //deal with those variables
                newUser = new User();
                newUser.setUserName(enterName);
                newUser.setUserEmail(enterEmail);
                newUser.setUserPhone(enterPhone);
                newUser.setUserPassword(enterPassward);
                Log.i("Name: ",newUser.getUserName());

                UserListController uc = new UserListController();
                boolean addSucess = uc.addUserAndCheck(newUser);

                if  (addSucess){
                    Log.i("Success sign up:",enterName);
                    Intent intent = new Intent(activity, UserCharacterActivity.class);

                    //deliver userName
                    intent.putExtra("userName",enterName);

                    startActivity(intent);
                }else{
                    Toast toast = Toast.makeText(context, "Invalid Sign Up information! Please Try Again!", Toast.LENGTH_LONG);
                    TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                }
            }
        });
    }
}