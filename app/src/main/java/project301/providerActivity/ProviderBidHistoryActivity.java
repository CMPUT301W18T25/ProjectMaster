package project301.providerActivity;

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
    private Button backButton;
    private ListView bidHistoryList;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;
    private Context context;
    private String userId;
    private User user;
    private String taskId;

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

        //get all bidden task of this provider (user) into a list
        /*TaskController.searchBiddenTasksOfThisProvider search = new TaskController.searchBiddenTasksOfThisProvider(userId);
        search.execute();
        try {
            taskList = search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        */

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

        // settle click on bid history list based on the task status
        bidHistoryList = (ListView) findViewById(R.id.provider_bid_history);
        bidHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Task task = taskList.get(index);
                String status = task.getTaskStatus();
                if (status.equals("request")) {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    Task tempTask = taskList.get(index);
                    taskId = tempTask.getId();
                    info1.putExtra("taskId",taskId);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","request");
                    startActivity(info1);
                } else if (status.equals("bidden")) {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    Task tempTask = taskList.get(index);
                    taskId = tempTask.getId();
                    info1.putExtra("taskId",taskId);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","bidden");
                    startActivity(info1);
                } else if (status.equals("assigned")) {
                    Intent info1 = new Intent(ProviderBidHistoryActivity.this, ProviderTaskBidActivity.class);
                    Task tempTask = taskList.get(index);
                    taskId = tempTask.getId();
                    info1.putExtra("taskId",taskId);
                    info1.putExtra("userId", userId);
                    info1.putExtra("info", index);
                    info1.putExtra("status","assigned");
                    startActivity(info1);
                } else if (status.equals("done")) {
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

    }

    @Override
    public void onStart() {
        super.onStart();

        //the following code is for testing
        /*
        Task task = taskList.get(0);
        Task task1 = new Task();
        task1.setTaskName("a");
        task1.setTaskAddress("a");
        task1.setTaskIdealPrice(1.0);
        task1.setTaskStatus("bidden");
        taskList = new ArrayList<>();
        taskList.add(task1);
        */

        //get all bidden task of this provider (user) into a list
        TaskController.searchBiddenTasksOfThisProvider search = new TaskController.searchBiddenTasksOfThisProvider(userId);
        search.execute();
        try {
            taskList = search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ProviderBiddenAdapter adapter = new ProviderBiddenAdapter(this, taskList);
        // Attach the adapter to a ListView
        adapter.setId(userId);
        this.bidHistoryList.setAdapter(adapter);
    }
}
