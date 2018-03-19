package com.example.mayingnan.project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.controller.TaskController;
import com.example.mayingnan.project301.controller.UserListController;
import com.example.mayingnan.project301.requesterActivity.RequesterAdapter;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 *
 * @author :
 * @version 1.0
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderBidHistoryActivity extends AppCompatActivity {
    private Button backButton;
    private ListView bidHistoryList;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private Context context;
    private String userId;
    private User user;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_bid_history);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        this.context = getApplicationContext();
        UserListController uc = new UserListController();
        user = uc.getAUserById(userId);

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderBidHistoryActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //wdong2:
        //the two class below could be one class
        //there will be a "if" statement to decide which class to jump

        // settle click on bid history list
        // case1: jump tp finish
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Task task = taskList.get(index);
                String status = task.getTaskStatus();
                if (status == "request") {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","request");
                    startActivity(info1);
                } else if (status == "bidden") {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","bidden");
                    startActivity(info1);
                } else if (status == "assigned") {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","assigned");
                    startActivity(info1);
                } else if (status == "done") {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskFinishActivity.class);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    startActivity(info1);
                } else {
                    //print error message
                    Toast toast = Toast.makeText(context, "Task Type Wrong!", Toast.LENGTH_LONG);
                    TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                }
            }
        });
/*
        // settle click on bid history list
        // case2: jump tp bid
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                info1.putExtra("userId",userId);
                info1.putExtra("info", index);
                startActivity(info1);
            }
        });
*/



    }

    @Override
    public void onStart() {
        super.onStart();

        TaskController.searchBiddenTasksOfThisProvider search = new TaskController.searchBiddenTasksOfThisProvider(userId);
        search.execute();

        try {
            taskList = search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Task task = taskList.get(0);
        //Test
        Task task1 = new Task();
        task1.setTaskName("a");
        task1.setTaskAddress("a");
        task1.setTaskIdealPrice(1.0);
        task1.setTaskStatus("bidden");
        //taskList = new ArrayList<>();
        taskList.add(task1);
        //

        RequesterAdapter adapter = new RequesterAdapter(this, taskList);
        // Attach the adapter to a ListView
        this.bidHistoryList.setAdapter(adapter);
    }
}
