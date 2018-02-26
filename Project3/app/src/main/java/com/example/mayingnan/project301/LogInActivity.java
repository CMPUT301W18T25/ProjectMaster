

package com.example.mayingnan.project301;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class LogInActivity extends AppCompatActivity {

    private ListView taskListView;
    private Button viewOnMap;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private EditText usernameText;
    private Button loginButton;
    private Button signupButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logIn(){

    }

    public void signUp(){

    }
}