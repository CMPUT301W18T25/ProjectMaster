package project301.allUserActivity;


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

import project301.R;
import project301.User;
import project301.controller.FileSystemController;
import project301.controller.UserController;

/**
 * This is the login activity, when user open the app, this activity
 * will ask the user to sign up an account or login.
 * @classname : LoginActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * This is the login activity.
 */

public class LogInActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwardText;
    private Context context;
    private User thisUser;
    Button loginButton;
    Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FileSystemController FC = new FileSystemController();
        FC.deleteFiles(getApplication());

        setContentView(R.layout.log_in);
        usernameText = findViewById(R.id.login_name);
        passwardText = findViewById(R.id.login_password);
        this.context = getApplicationContext();

        //settle login button
        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String enterUsername = usernameText.getText().toString();
                String enterPassward = passwardText.getText().toString();

                UserController userController = new UserController();
                /* for testing!
                 if (enterUsername == "wdong2"){
                 Intent intent = new Intent (LogInActivity.this, UserCharacterActivity.class);
                 startActivity(intent);
                 }
                 */
                if (userController.checkUserByNameAndPassword(enterUsername,enterPassward) == true){
                    Intent intent = new Intent (LogInActivity.this, UserCharacterActivity.class);
                    UserController uc = new UserController();

                    String Id = enterUsername;
                    Log.i("id",Id);

                    //deliver userName
                    intent.putExtra("userId",Id);
                    startActivity(intent);
                }else{
                    //print error message
                    Toast toast = Toast.makeText(context, "Invalid Login Information! Please Try Again!", Toast.LENGTH_LONG);
                    TextView v1 = toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();

                }

            }
        });

        //settle signup button
        signUpButton = findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}