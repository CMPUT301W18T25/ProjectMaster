package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.novoda.merlin.MerlinsBeard;
import com.novoda.merlin.NetworkStatus;

import project301.R;
import project301.Task;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Detail :RequesterViewTaskActivity is to allow user to view a target task and choose if to edit this task.
 *                the bid list will accept data from provider to show all the bid for this task so that requester can choose bid.
 *                this class also support delete task, jump back to showlist and choose bid.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterViewTaskActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterViewTaskActivity extends AppCompatActivity  {
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
    protected MerlinsBeard merlinsBeard;
    private Context context;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_view_task);
        final Intent intent = getIntent();
        context = getApplicationContext();
        merlinsBeard = MerlinsBeard.from(context);
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        view_index=intent.getExtras().get("info").toString();



        //find view by id.
        view_name = (TextView) findViewById(R.id.c_view_name);
        view_detail= (TextView) findViewById(R.id.c_view_detail);
        view_destination = (TextView) findViewById(R.id.c_view_destination);
        view_status = (TextView) findViewById(R.id.c_view_status);
        view_idealprice = (TextView) findViewById(R.id.c_view_idealprice);
        view_lowestbid = (TextView) findViewById(R.id.c_lowest_bid);

        tasklist = new ArrayList<Task>();

        //get data from database
        if(merlinsBeard.isConnected()){
            TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
            search.execute(userId);
            try {
                tasklist= search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            FileSystemController FC = new FileSystemController();
            tasklist = FC.loadSentTasksFromFile(context);
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
    //when on start, first get newest data from database and then update the information
    protected void onStart(){
        super.onStart();

        //time sleep
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //pull data from database
        if(merlinsBeard.isConnected()) {
            TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
            search.execute(userId);

            tasklist = new ArrayList<Task>();
            try {
                tasklist = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            FileSystemController FC = new FileSystemController();
            tasklist = FC.loadSentTasksFromFile(context);
        }


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