package com.example.mayingnan.project301.requesterActivity;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.controller.TaskController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class RequesterEditListActivity extends AppCompatActivity {
    private ListView postedTaskList;
    private String userName;
    private String userId;
    private static final String FILENAME = "ProjectMaster.sav";
    private ArrayList<Task> tasklist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_edit_list);
        final Intent intent = getIntent();
        userId = intent.getExtras().get("userId").toString();


        //settle mainMenu button
        Button mainMenuButton = (Button) findViewById(R.id.main_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditListActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle viewOnMap button
        Button viewOnMapButton = (Button) findViewById(R.id.c_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditListActivity.this, RequesterMapActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        // settle click on post task list
        postedTaskList = (ListView) findViewById(R.id.post_list);
        postedTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(RequesterEditListActivity.this, RequesterViewTaskActivity.class);
                info1.putExtra("info", index);
                info1.putExtra("userId",userId);
                startActivity(info1);
            }
        });

        TaskController.searchAllTasksOfThisRequester getAll = new TaskController.searchAllTasksOfThisRequester();
        getAll.execute("user id here");
        try {
            ArrayList<Task> save_tasks = getAll.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onStart(){
        super.onStart();
        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);
        //tasklist= search.get();

        tasklist = new ArrayList<>();
        //RequesterAdapter adapter = new RequesterAdapter(this, tasklist);
        // Attach the adapter to a ListView
        //this.postedTaskList.setAdapter(adapter);

    }




}
