package com.example.mayingnan.project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : RequesterViewTaskActivity
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterViewTaskActivity extends AppCompatActivity {
    private ListView bidList;
    private String userId;
    private TextView view_name;
    private TextView view_detail;
    private TextView view_destination;
    private TextView view_status;
    private TextView view_idealprice;
    private TextView view_lowestbid;
    private Task view_task;
    private Task target_task;
    private ArrayList<Task> tasklist;
    private ArrayList<Task> deletedlist;
    private String view_index;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_view_task);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        view_index=intent.getExtras().get("info").toString();



        /**
         * find view by id.
         */
        view_name = (TextView) findViewById(R.id.c_view_name);
        view_detail= (TextView) findViewById(R.id.c_view_detail);
        view_destination = (TextView) findViewById(R.id.c_view_destination);
        view_status = (TextView) findViewById(R.id.c_view_status);
        view_idealprice = (TextView) findViewById(R.id.c_view_idealprice);
        view_lowestbid = (TextView) findViewById(R.id.c_lowest_bid);


        //get data from database
        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);

        tasklist = new ArrayList<>();
        try {
            tasklist= search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // get index of target task
        final int view_index = Integer.parseInt(intent.getExtras().get("info").toString());

        // get target task
        view_task=tasklist.get(view_index);


        // get information from target task and set information
        String temp_name=view_task.getTaskName();
        view_name.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        view_detail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        view_destination.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        view_status.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        view_idealprice.setText(Double.toString(temp_idealprice));

        Double temp_lowestbid=view_task.getLowestBid();
        view_lowestbid.setText(Double.toString(temp_lowestbid));


        //settle edit button
        Button editButton = (Button) findViewById(R.id.edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getExtras().get("info").toString();
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditTaskActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("info",index);
                startActivity(info2);

            }
        });

        //settle deleteTask button
        Button deleteTaskButton = (Button) findViewById(R.id.delete_button);
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getExtras().get("info").toString();

                //interface jump
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditListActivity.class);

                //get data from database
                deletedlist = new ArrayList<>();
                TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
                search.execute(userId);
                try {
                    deletedlist= search.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                // get index of target task
                final int view_index = Integer.parseInt(intent.getExtras().get("info").toString());

                // get target task
                target_task=deletedlist.get(view_index);

                //delete task from database
                TaskController.deleteTaskById deleteTaskById = new TaskController.deleteTaskById(target_task.getId());
                deleteTaskById.execute(target_task.getId());



                info2.putExtra("userId",userId);
                info2.putExtra("info",index);
                startActivity(info2);

            }
        });

        //settle chooseBid button
        Button chooseBidButton = (Button) findViewById(R.id.choose_button);
        chooseBidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterPayActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle showlist button
        Button showlist_Button = (Button) findViewById(R.id.showlist_button);
        showlist_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskActivity.this, RequesterEditListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });



        // settle click on bid list, no change to interface ,but the bid price get selected
        bidList = (ListView) findViewById(R.id.bid_list);
        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {

            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();


        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);

        tasklist = new ArrayList<Task>();
        try {
            tasklist= search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.i("Sign", Integer.toString(tasklist.size()));

        // get target task
        final int index = Integer.parseInt(view_index);
        view_task=tasklist.get(index);
        //Log.i("State", Integer.toString(index) + " " + tasklist.get(index).getTaskName());

        // get information from target task and set information
        String temp_name=view_task.getTaskName();
        view_name.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        view_detail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        view_destination.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        view_status.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        view_idealprice.setText(Double.toString(temp_idealprice));

        Double temp_lowestbid=view_task.getLowestBid();
        view_lowestbid.setText(Double.toString(temp_lowestbid));





    }

}