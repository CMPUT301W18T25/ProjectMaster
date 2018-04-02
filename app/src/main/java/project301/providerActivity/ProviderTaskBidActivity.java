package project301.providerActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import project301.Bid;
import project301.R;
import project301.Task;
import project301.User;
import project301.controller.BidController;
import project301.controller.TaskController;
import project301.controller.UserController;
import project301.requesterActivity.RequesterAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : ProviderTaskBidActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * This is the Activity class for user to bid on an exsiting task
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderTaskBidActivity extends AppCompatActivity {

    //UI variable
    private TextView taskName;
    private TextView taskDetail;
    private TextView taskStatus;
    private TextView taskLocation;
    private TextView taskLowestPrice;
    private TextView taskIdealPrice;
    private TextView requesterName_view;
    private TextView requesterPhone_view;
    private TextView requesterEmail_view;
    private EditText taskMybid;
    private ListView BidListView;
    private ArrayList<Bid> listOfBids;
    private ArrayList<Task> tasklist;
    private Context context;
    private Button BackButton;
    private Button BidButton;
    private Button CancelButton;
    private ImageButton show_photo;

    // task variable
    private String userName;
    private String userId;
    private String status;
    private String index;
    private Bid bid;
    private Task view_task;
    private String taskId;

    // task requester variable
    private User requester;
    private String requesterName;
    private String requesterPhone;
    private String requesterEmail;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.provider_task_bid);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        this.context = getApplicationContext();
        userId = intent.getExtras().get("userId").toString();

        // find requester info view by id
        requesterName_view = (TextView) findViewById(R.id.r_name);
        requesterPhone_view = (TextView) findViewById(R.id.r_phone);
        requesterEmail_view = (TextView) findViewById(R.id.r_email);

        // find view by id.
        taskName = (TextView) findViewById(R.id.p_task_name);
        taskDetail= (TextView) findViewById(R.id.p_task_detail);
        taskStatus = (TextView) findViewById(R.id.p_task_status);
        taskLocation = (TextView) findViewById(R.id.p_task_destination);
        taskIdealPrice = (TextView) findViewById(R.id.p_task_idealprice);
        taskLowestPrice = (TextView) findViewById(R.id.p_task_lowestbid);
        taskMybid = (EditText)findViewById(R.id.p_task_mybid);
        BidListView = (ListView)findViewById(R.id.provider_bid_lkist);
        show_photo = (ImageButton) findViewById(R.id.imageButton2);

        /*
        // get index of target task
        int view_index = Integer.parseInt(intent.getExtras().get("info").toString());


        //get tast status from last activity
        status = intent.getExtras().get("status").toString();

        //get data from database
        if (status.equals("request")) {
            //request and bidden. if only request, he will not see other person's bidding tasks
            TaskController.searchAllRequestingTasks search = new TaskController.searchAllRequestingTasks();
            search.execute();
            tasklist = new ArrayList<>();
            ArrayList<Task> searchedTask = new ArrayList<>();
            try {
                searchedTask = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            tasklist.addAll(searchedTask);

            TaskController.searchAllBiddenTasks search2 = new TaskController.searchAllBiddenTasks();
            search2.execute();
            try {
                searchedTask = search2.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            tasklist.addAll(searchedTask);

            //set initiallized bid
            //taskMybid.setText("0");

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
        // test id correctness
        Log.i("id", view_task.getId());
        */

        // get target task (new)
        taskId = intent.getExtras().get("taskId").toString();
        TaskController.getTaskById getIt = new TaskController.getTaskById();
        getIt.execute(taskId);
        try{
            view_task = getIt.get();
        }catch (Exception e){
            Log.d("boom", "shaka: ");
        }

        if (view_task == null){
            Log.i("Error", "not getting task ");
        }

        // get requester info
        UserController uc = new UserController();
        requesterName = view_task.getTaskRequester();
        requester = uc.getAUserByName(requesterName);
        requesterPhone = requester.getUserPhone();
        requesterEmail = requester.getUserEmail();

        // set requester info
        requesterName_view.setText(requesterName);
        requesterPhone_view.setText(requesterPhone);
        requesterEmail_view.setText(requesterEmail);

        // get and set task information
        String temp_name=view_task.getTaskName();
        taskName.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        taskDetail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        taskLocation.setText(temp_destination);

        String temp_status=view_task.getTaskStatus();
        taskStatus.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        if(temp_idealprice!=null){
            taskIdealPrice.setText(Double.toString(temp_idealprice));
        }

        Double temp_lowestbid=view_task.findLowestbid();
        if(temp_lowestbid!=null) {
            taskLowestPrice.setText(Double.toString(temp_lowestbid));
        }


        //settle cancel button : cancel the old bid
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //print error message
                Toast toast = Toast.makeText(context, "You cannot cancle your bid!", Toast.LENGTH_LONG);
                TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                v1.setTextColor(Color.RED);
                v1.setTextSize(20);
                v1.setGravity(Gravity.CENTER);
                toast.show();

            }
        });



        //settle bid button : add the bid to list and jump back to history
        Button bidButton = (Button) findViewById(R.id.bid_button);
        bidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enterBid_s = taskMybid.getText().toString();

                //test input type
                //Log.i("enterBid_s",enterBid_s);

                Double enterBid = Double.parseDouble(enterBid_s);

                //test bid type
                //System.out.println(enterBid);

                bid = new Bid(enterBid,userId);

                TaskController.providerSetBid setTaskBid = new TaskController.providerSetBid(view_task,bid);
                setTaskBid.execute();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //increase the bidcounter of this requester
                BidController bidController = new BidController();
                bidController.increaseBidCounterOfThisRequester(view_task.getTaskRequester());

                Intent info2 = new Intent(ProviderTaskBidActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("content","all");
                //info2.putExtra("userName",userId);

                startActivity(info2);

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

        // show photo button
        show_photo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("requesteredittask","showing photo");
                if (view_task.getTaskPhoto() != null){
                    Log.d("asdf","not null");

                    view_task.getTaskPhoto().showImage(ProviderTaskBidActivity.this);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        TaskController.searchAllBid searchAllBid = new TaskController.searchAllBid();
        searchAllBid.execute(view_task.getId());
        Double myBidAmount = 0.0;
        ArrayList<Bid> allBidOfThisTask = new ArrayList<>();
        //currently, only bid amount is in it. Please add username in the future
        ArrayList<String> allBidsString = new ArrayList<>();
        try {
            allBidOfThisTask = searchAllBid.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(Bid bid: allBidOfThisTask){

            if(!bid.equals(null)) {
                allBidsString.add(Double.toString(bid.getBidAmount()));
                if (bid.getProviderId().equals(userId)) {
                    if (bid.getBidAmount() != null) {
                        myBidAmount = bid.getBidAmount();
                        break;

                    }

                }
            }
        }
        taskMybid.setText(Double.toString(myBidAmount));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.bid_list_item,allBidsString);
        BidListView.setAdapter(adapter);
    }

}
