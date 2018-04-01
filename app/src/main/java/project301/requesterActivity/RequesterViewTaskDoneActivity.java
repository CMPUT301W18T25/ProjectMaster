package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.novoda.merlin.MerlinsBeard;

import project301.Bid;
import project301.GlobalCounter;
import project301.R;
import project301.Task;
import project301.User;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.TaskController;
import project301.controller.UserController;

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
public class RequesterViewTaskDoneActivity extends AppCompatActivity  {
    private String userId;
    private TextView view_name;
    private TextView view_detail;
    private TextView view_destination;
    private TextView view_status;
    private TextView view_idealprice;
    private TextView view_provider_phone;
    private TextView view_provider_Name;
    private TextView view_provider_Email;
    private TextView view_deal_price;
    private Task view_task;
    private Task target_task;
    private ArrayList<Task> tasklist;
    private ArrayList<Task> alltasklist;
    private User provider;

    private String view_index;
    private int view_index_int;
    protected MerlinsBeard merlinsBeard;
    private Context context;
    private Bid bid;
    private String activity;
    private Intent intent;


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_view_task_done);
        intent = getIntent();
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
        view_provider_phone = (TextView) findViewById(R.id.c_view_phone);
        view_provider_Name = (TextView) findViewById(R.id.c_view_provider);
        view_provider_Email = (TextView) findViewById(R.id.c_view_email);
        view_deal_price = (TextView) findViewById(R.id.c_deal_price);
        view_idealprice = (TextView) findViewById(R.id.c_view_idealprice);
        view_index = intent.getExtras().get("info").toString();
        view_index_int = Integer.parseInt(intent.getExtras().get("info").toString());


        //settle deleteTask button
        Button deleteTaskButton = (Button) findViewById(R.id.delete_button);
        deleteTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String index = intent.getExtras().get("info").toString();

                Intent info2;
                if(activity.equals("allList")) {
                    info2 = new Intent(RequesterViewTaskDoneActivity.this, RequesterAllListActivity.class);
                }
                else{
                    info2 = new Intent(RequesterViewTaskDoneActivity.this, RequesterDoneListActivity.class);


                }



                // get target task
                target_task=tasklist.get(view_index_int);
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
                Intent info2;
                if(activity.equals("allList")) {
                    info2 = new Intent(RequesterViewTaskDoneActivity.this, RequesterAllListActivity.class);
                }
                else{
                    info2 = new Intent(RequesterViewTaskDoneActivity.this, RequesterDoneListActivity.class);
                }
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

    }


    @Override
    //when on start, first get newest data from database and then update the information
    protected void onStart(){
        super.onStart();
        intent = getIntent();
        activity = getIntent().getExtras().get("activity").toString();

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
        tasklist = new ArrayList<>();
        alltasklist = FC.loadSentTasksFromFile(getApplication());
        Log.i("activity:",activity);
        if(activity.equals("doneList")){
            for(Task task:alltasklist){
                if(task.getTaskStatus().equals("done")) {
                    tasklist.add(task);
                }
            }
        }
        else{
            tasklist = alltasklist;
        }

        // get target task
        final int index = Integer.parseInt(view_index);
        view_task=tasklist.get(index);
        String providerId = view_task.getTaskProvider();

        UserController userController = new UserController();
        provider = userController.getAUserById(providerId);

        String temp_name=view_task.getTaskName();
        view_name.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        view_detail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        view_destination.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        view_status.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        Double temp_dealprice=view_task.getChoosenBid().getBidAmount();


        view_idealprice.setText(Double.toString(temp_idealprice));
        view_deal_price.setText(Double.toString(temp_dealprice));

        String temp_phone  = provider.getUserPhone();
        if(!temp_phone.equals(null)){
            view_provider_phone.setText(provider.getUserPhone());
        }
        String temp_email = provider.getUserEmail();
        if(!temp_email.equals(null)){
            view_provider_Email.setText(provider.getUserEmail());
        }
        String temp_provider = provider.getUserName();
        if(!temp_provider.equals(null)){
            view_provider_Name.setText(provider.getUserName());

        }



    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterViewTaskDoneActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}