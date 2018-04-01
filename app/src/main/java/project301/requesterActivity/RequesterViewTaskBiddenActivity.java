package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.novoda.merlin.MerlinsBeard;

import project301.Bid;
import project301.GlobalCounter;
import project301.R;
import project301.Task;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Detail :RequesterViewTaskRequestActivity is to allow user to view a target task and choose if to edit this task.
 *                the bid list will accept data from provider to show all the bid for this task so that requester can choose bid.
 *                this class also support delete task, jump back to showlist and choose bid.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterViewTaskRequestActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterViewTaskBiddenActivity extends AppCompatActivity  {
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
    private ArrayList<Task> alltasklist;
    private ArrayList<Task> deletedlist;
    private String view_index;
    protected MerlinsBeard merlinsBeard;
    private Context context;
    private Bid bid;
    private String activity;
    private Intent intent;
    private ImageButton show_photo;



    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_view_task_bidden);
        intent = getIntent();
        context = getApplicationContext();
        merlinsBeard = MerlinsBeard.from(context);
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        view_index=intent.getExtras().get("info").toString();
        activity = intent.getExtras().get("activity").toString();


        //find view by id.
        view_name = (TextView) findViewById(R.id.c_view_name);
        view_detail= (TextView) findViewById(R.id.c_view_detail);
        view_destination = (TextView) findViewById(R.id.c_view_destination);
        view_status = (TextView) findViewById(R.id.c_view_status);
        view_idealprice = (TextView) findViewById(R.id.c_view_idealprice);
        view_lowestbid = (TextView) findViewById(R.id.c_lowest_bid);
        bidList = (ListView)findViewById(R.id.bid_list);
        tasklist = new ArrayList<Task>();
        show_photo = (ImageButton) findViewById(R.id.imageButton);

        //to do : map show location
        //set viewmap button
        Button mapButton = (Button) findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getExtras().get("info").toString();
                Intent info2 = new Intent(RequesterViewTaskBiddenActivity.this, RequesterMapActivity.class);
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
                Intent info2;
                //interface jump
                if(activity.equals("allList")) {
                    info2 = new Intent(RequesterViewTaskBiddenActivity.this, RequesterAllListActivity.class);
                }else{
                    info2 = new Intent(RequesterViewTaskBiddenActivity.this, RequesterBiddenListActivity.class);
                }
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

                FileSystemController FC = new FileSystemController();
                String FileName = "sent-"+target_task.getId()+".json";
                FC.deleteFileByName(FileName,getApplication());



                info2.putExtra("userId",userId);
                info2.putExtra("info",index);
                startActivity(info2);

            }
        });



        //settle showlist button
        Button showlist_Button = (Button) findViewById(R.id.showlist_button);
        showlist_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterViewTaskBiddenActivity.this, RequesterBiddenListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        // settle click on bid list, interface jump from view to pay.
        bidList = (ListView) findViewById(R.id.bid_list);
        bidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(RequesterViewTaskBiddenActivity.this, RequesterChooseBidActivity.class);
                info1.putExtra("taskId",view_task.getId());
                info1.putExtra("userId",userId);
                info1.putExtra("bidIndex",index);

                startActivity(info1);

            }
        });

        // show photo button
        show_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("requesteredittask","showing photo");
                if (view_task.getTaskPhoto() != null){
                    Log.d("RequesterViewTaskBiddenActivity","not null");

                    view_task.getTaskPhoto().showImage(RequesterViewTaskBiddenActivity.this);
                }
            }
        });
    }


    @Override
    //when on start, first get newest data from database and then update the information
    protected void onStart(){
        super.onStart();
        activity = intent.getExtras().get("activity").toString();
        Log.i("activity",activity);

        FileSystemController FC = new FileSystemController();
        //time sleep
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BidController bidController = new BidController();
        //check counter change
        int newCount = bidController.searchBidCounterOfThisRequester(userId);
        if(newCount!= GlobalCounter.count && newCount>0){
            GlobalCounter.count = newCount;
            Log.i("New Bid","New Bid");
            openRequestInfoDialog();
        }

        //pull data from database
        if(merlinsBeard.isConnected()) {
            TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
            search.execute(userId);

            alltasklist = new ArrayList<Task>();
            try {
                alltasklist = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for(Task task:alltasklist){
                FC.saveToFile(task,"sent",getApplication());
            }
        }
        alltasklist = FC.loadSentTasksFromFile(getApplication());

        if(activity.equals("biddenList")){
            for(Task task:alltasklist){
                if(task.getTaskStatus().equals("bidden")){
                    tasklist.add(task);
                }
            }
            Log.i("enter",activity);

        }
        else{
            tasklist = alltasklist;
        }

        // get target task
        final int index = Integer.parseInt(view_index);
        view_task=tasklist.get(index);
        //Log.i("State", Integer.toString(index) + " " + tasklist.get(index).getTaskName());
        Log.i("view task name",view_task.getTaskName());
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

        Double temp_lowestbid=view_task.findLowestbid();
        view_lowestbid.setText(Double.toString(temp_lowestbid));


        //pull bid data from database
        TaskController.searchAllBid searchAllBid = new TaskController.searchAllBid();
        ArrayList<String> availableBidsString = new ArrayList<>();
        ArrayList<Bid> availableBid = new ArrayList<>();
        availableBid = view_task.getAvailableBidListOfThisTask();

        //put target data into arraylist.
        for(Bid bid: availableBid){
            if(bid!=null) {
                availableBidsString.add(Double.toString(bid.getBidAmount()));
            }
        }
        ArrayList<Bid> availabl2eBid = view_task.getAvailableBidListOfThisTask();
        for(Bid bid:availabl2eBid){
            Log.i("bid amount",bid.getProviderId());
        }
        ArrayList<Bid> canceledBid = view_task.getCanceledBidList();
        for(Bid bid:canceledBid){
            Log.i("canceled bid amount",bid.getProviderId());
        }
        //set adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.bid_list_item,availableBidsString);
        bidList.setAdapter(adapter);
    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterViewTaskBiddenActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}