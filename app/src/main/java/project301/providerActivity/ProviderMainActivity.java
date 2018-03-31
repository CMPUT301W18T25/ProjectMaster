package project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project301.R;
import project301.Task;
import project301.controller.TaskController;

/**
 * user could change profile, see bid history, as well as look into a requesting task.
 * This is the main page of provider; it shows a list of requesting task;
 * @classname : ProviderMainActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


public class ProviderMainActivity extends AppCompatActivity {

    private Button searchButton;
    private Button editProfileButton;
    private Button viewOnMapButton;
    private Button bidHistoryButton;
    private EditText searchEditText;
    private String searchText;
    private ListView availablelist;
    private ArrayList<Task> taskList;
    private Context context;

    private String userId;
    private String taskId;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_main);

        //get final intent
        final Intent intent = getIntent();

        //get context
        this.context = getApplicationContext();

        //get userId
        userId = intent.getExtras().get("userId").toString();

        //settle viewOnMap button
        viewOnMapButton = findViewById(R.id.provider_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderMainActivity.this, ProviderMapActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

        //settle bidHistory button
        bidHistoryButton = findViewById(R.id.provider_bid_button);
        bidHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderMainActivity.this, ProviderBidHistoryActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("content","all");
                startActivity(intent);
            }
        });

        //settle editProfile button
        editProfileButton = findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderMainActivity.this, ProviderEditInfoActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        // settle click on list
        availablelist = findViewById(R.id.provider_list);
        availablelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(ProviderMainActivity.this, ProviderTaskBidActivity.class);
                Task tempTask = taskList.get(index);
                taskId = tempTask.getId();
                info1.putExtra("taskId",taskId);
                info1.putExtra("info", index);
                //provide the task status for the next activity
                info1.putExtra("status","request");
                info1.putExtra("userId",userId);

                startActivity(info1);
            }
        });

        // to do search button
        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText = findViewById(R.id.search_info);
                searchText = searchEditText.getText().toString();
                searchText = searchText.trim();
                if (searchText.isEmpty()){
                    //print error message
                    Toast toast = Toast.makeText(context, "Search Key Cannot be Empty!", Toast.LENGTH_LONG);
                    TextView v1 = toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                }else {
                    ArrayList<Task> result = new ArrayList<>(); // search result
                    //need search code

                    TaskController.searchTaskByKeyword searchTask = new TaskController.searchTaskByKeyword();
                    searchTask.execute(searchText);

                    try {
                        result = searchTask.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    setTaskList(result);
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        TaskController.searchAllRequestingTasks search = new TaskController.searchAllRequestingTasks();
        search.execute();
        taskList = new ArrayList<>();
        ArrayList<Task> searchedTask = new ArrayList<>();
        try {
            searchedTask = search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        taskList.addAll(searchedTask);

        TaskController.searchAllBiddenTasks search2 = new TaskController.searchAllBiddenTasks();
        search2.execute();
        try {
            searchedTask = search2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        taskList.addAll(searchedTask);

        // Attach the adapter to a ListView
        setTaskList(taskList);
        ProviderAdapter adapter = new ProviderAdapter(this, taskList);
        this.availablelist.setAdapter(adapter);
    }

    public void setTaskList(ArrayList<Task> list){
        taskList = list;
        ProviderAdapter adapter = new ProviderAdapter(this, taskList);
        this.availablelist.setAdapter(adapter);
    }
}
