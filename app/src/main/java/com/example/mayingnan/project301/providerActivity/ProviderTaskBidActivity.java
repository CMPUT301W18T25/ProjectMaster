package com.example.mayingnan.project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : ProviderTaskBidActivity
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
public class ProviderTaskBidActivity extends AppCompatActivity {

    private TextView taskName;
    private TextView taskDetail;
    private TextView taskStatus;
    private TextView taskLocation;
    private TextView taskLowestPrice;
    private TextView taskIdealPrice;
    private EditText taskMybid;
    private ListView BidListView;
    private ArrayList<Bid> listOfBids;
    private ArrayList<Task> tasklist;
    private Context context;
    private Button BackButton;
    private Button BidButton;
    private Button CancelButton;
    private String userName;
    private String userId;
    private String status;
    private String index;
    private Bid bid;
    private Task view_task;


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_bid);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        this.context = getApplicationContext();
        userId = intent.getExtras().get("userId").toString();


        /**
         * find view by id.
         */
        taskName = (TextView) findViewById(R.id.p_task_name);
        taskDetail= (TextView) findViewById(R.id.p_task_detail);
        taskLocation = (TextView) findViewById(R.id.p_task_destination);
        //taskStatus = (TextView) findViewById(R.id.p_task_status);
        taskIdealPrice = (TextView) findViewById(R.id.p_task_idealprice);
        taskLowestPrice = (TextView) findViewById(R.id.p_task_mybid);
        taskMybid = (EditText)findViewById(R.id.p_task_mybid);

        // get index of target task
        int view_index = Integer.parseInt(intent.getExtras().get("info").toString());


        //get tast status from last activity
        status = intent.getExtras().get("status").toString();
        //get data from database
        if (status.equals("request")) {
            TaskController.searchAllRequestingTasks search = new TaskController.searchAllRequestingTasks();
            search.execute();

            try {
                tasklist = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //set bid
            taskMybid.setText("0");

        }else if(status.equals("bidden")){
            TaskController.searchBiddenTasksOfThisProvider search = new TaskController.searchBiddenTasksOfThisProvider(userId);
            search.execute();

            try {
                tasklist = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //set bid
            //bid = view_task.getBit();
            //taskMybid.setText("0");

        }else if(status.equals("assigned")){
            TaskController.searchAssignTasksOfThisProvider search = new TaskController.searchAssignTasksOfThisProvider();
            search.execute(userId);

            try {
                tasklist = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }else {
            //print error message
            Toast toast = Toast.makeText(context, "Task Type Wrong!(not request, bidden, assigned)", Toast.LENGTH_LONG);
            TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
            v1.setTextColor(Color.RED);
            v1.setTextSize(20);
            v1.setGravity(Gravity.CENTER);
            toast.show();
        }

        // get target task
        view_task=tasklist.get(view_index);


        // get information from target task and set information
        String temp_name=view_task.getTaskName();
        taskName.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        taskDetail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        taskLocation.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        Log.i("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",temp_status);
        //taskStatus.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        taskIdealPrice.setText(Double.toString(temp_idealprice));

        Double temp_lowestbid=view_task.getLowestBid();
        taskLowestPrice.setText(Double.toString(temp_lowestbid));


        //settle cancel button : cancel the old bid
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);
                */

            }
        });



        //settle bid button : add the bid to list and jump back to history
        Button bidButton = (Button) findViewById(R.id.bid_button);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);
                */

            }
        });


        //settle back button : back to history, no change
        Button backButton = (Button) findViewById(R.id.Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        /*
        // get information from target task and set information
        String temp_name=view_task.getTaskName();
        taskName.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        taskDetail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        taskLocation.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        taskStatus.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        taskIdealPrice.setText(Double.toString(temp_idealprice));

        Double temp_lowestbid=view_task.getLowestBid();
        taskLowestPrice.setText(Double.toString(temp_lowestbid));
        */

    }


}
