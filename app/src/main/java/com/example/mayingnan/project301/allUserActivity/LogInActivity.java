

package com.example.mayingnan.project301.allUserActivity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;

import java.util.ArrayList;


public class LogInActivity extends AppCompatActivity {

    //private LogInActivity activity = this;

    private ListView taskListView;
    private Button viewOnMap;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private EditText usernameText;
    private Button loginButton;
    private Button signUpButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);







        Button signUpButton = (Button) findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setResult(RESULT_OK);
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void logIn(){

    }

    public void signUp(){

    }
}