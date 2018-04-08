package project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import project301.R;
import project301.Task;
import project301.User;
import project301.controller.TaskController;
import project301.controller.UserController;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This class shows a list of task that the status of the tasks are bidden, assigned or done
 * @classname : ProviderBidHistoryActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderBidHistoryActivity extends AppCompatActivity {
    private Button showAssigned;
    private Button showAll;
    private Button viewOnMapButton;
    private Button backButton;
    private ListView bidHistoryList;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private Context context;
    private String userId;
    private User user;
    private String taskId;
    private String content;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_bid_history);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        this.context = getApplicationContext();
        UserController uc = new UserController();
        user = uc.getAUserById(userId);
        this.content = intent.getExtras().get("content").toString();


        //settle viewOnMap button
        viewOnMapButton = findViewById(R.id.c_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderBidHistoryActivity.this, ProviderMapActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        //settle show assigned task button
        showAssigned = (Button) findViewById(R.id.show_assigned_button);
        showAssigned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderBidHistoryActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("content","assigned");
                startActivity(info2);
            }
        });

        //settle show all task button
        showAll = (Button) findViewById(R.id.show_all_tasks_button);
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderBidHistoryActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("content","all");
                startActivity(info2);
            }
        });

        //settle back button
        backButton = (Button) findViewById(R.id.c_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderBidHistoryActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);
            }
        });

        // settle click on bid history list based on the task status
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Task tempTask = taskList.get(index);
                taskId = tempTask.getId();
                String status = tempTask.getTaskStatus();

                Intent intent = setIntent(status,index);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();


        taskList = new ArrayList<>();
        ArrayList<Task> searchedTask = new ArrayList<>();
        if (this.content.equals("all")) {
            //get all bidden task of this provider (user) into the list
            TaskController.searchBiddenTasksOfThisProvider search = new TaskController.searchBiddenTasksOfThisProvider(userId);
            search.execute();
            try {
                searchedTask = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            taskList.addAll(searchedTask);
        }

        //get all assigned task of this provider (user) into the list
        TaskController.searchAssignTasksOfThisProvider search2 = new TaskController.searchAssignTasksOfThisProvider();
        search2.execute(userId);
        try {
            searchedTask = search2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        taskList.addAll(searchedTask);


        //get all finished task of this provider (user) into the list
        if (this.content.equals("all")) {
            //get all bidden task of this provider (user) into a list
            TaskController.searchDoneTasksOfThisProvider search = new TaskController.searchDoneTasksOfThisProvider(userId);
            search.execute();
            try {
                searchedTask = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            taskList.addAll(searchedTask);
        }

        // Attach the adapter to a ListView
        if (this.content.equals("all")) {
            ProviderBiddenAdapter adapter = new ProviderBiddenAdapter(this, taskList);
            adapter.setId(userId);
            this.bidHistoryList.setAdapter(adapter);
        }else{
            ProviderAssignedAdapter adapter = new ProviderAssignedAdapter(this, taskList);
            adapter.setId(userId);
            this.bidHistoryList.setAdapter(adapter);
        }
    }

    //set intent
    public Intent setIntent(String status,int index){
        Intent intent;
        if (status.equals("request")|status.equals("bidden")) {
            intent = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
        }else{
            intent = new Intent(ProviderBidHistoryActivity.this, ProviderTaskFinishActivity.class);
        }
        intent.putExtra("taskId",taskId);
        intent.putExtra("userId", userId);
        intent.putExtra("info", index);
        intent.putExtra("status",status);
        return intent;
    }
}
