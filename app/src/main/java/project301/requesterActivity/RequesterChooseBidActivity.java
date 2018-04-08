

package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import project301.Bid;
import project301.BidCounter;
import project301.GlobalCounter;
import project301.R;
import project301.Task;
import project301.User;
import project301.controller.BidController;
import project301.controller.OfflineController;
import project301.controller.TaskController;
import project301.controller.UserController;

/**
 * Detail : Requester choose bid class is to change the status of task from bidden to assigned
 * @Date :   01/04/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterChooseBidActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterChooseBidActivity extends AppCompatActivity {

    private String userId;
    private int bidIndex;
    private String taskId;
    private User provider;
    private Task task;
    private TextView confirmName;
    private TextView confirmEmail;
    private TextView confirmPhone;
    private TextView confirmPrice;
    private TextView view_idealprice;

    private ArrayList<Bid> bidList;
    private Bid thisBid;
    private Timer timer;
    MyTask myTask = new MyTask();
    private class MyTask extends TimerTask {
        public void run() {
            //Log.i("Timer4","run");
            BidController bidController = new BidController();
            //check counter change
            BidCounter bidCounter = bidController.searchBidCounterOfThisRequester(userId);
            if(bidCounter==null){
                Log.i("Bid counter search error",".");
            }
            else{
                OfflineController offlineController = new OfflineController();
                offlineController.tryToExecuteOfflineTasks(getApplication());
                if(bidCounter.getCounter()!= bidCounter.getPreviousCounter()){
                    Message msg = new Message();
                    msg.arg1 = 1;
                    handler.sendMessage(msg);

                    //update previousCounter
                    bidCounter.setPreviousCounter(bidCounter.getCounter());
                    BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
                    updateBidCounterOfThisRequester.execute(bidCounter);
                }
            }



        }
    };


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.requester_pay);
        final Intent intent = getIntent();

        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        bidIndex = Integer.parseInt(intent.getExtras().get("bidIndex").toString());
        taskId = intent.getExtras().get("taskId").toString();

        //get Task object
        TaskController.getTaskById getTaskById = new TaskController.getTaskById();
        getTaskById.execute(taskId);
        try {
            task = getTaskById.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        bidList = task.getAvailableBidListOfThisTask();
        thisBid = bidList.get(bidIndex);
        Log.i("Bid name",thisBid.getProviderId());

        //get user

        // find view by id.
        confirmName = (TextView) findViewById(R.id.confirm_name);
        confirmEmail= (TextView) findViewById(R.id.confirm_email);
        confirmPhone = (TextView) findViewById(R.id.confirm_phone);
        confirmPrice = (TextView) findViewById(R.id.confirm_price);



        UserController uc = new UserController();
        provider = uc.getAUserById(thisBid.getProviderId());

        //set information to textview
        String temp_name=provider.getUserName();
        confirmName.setText(temp_name);

        String temp_email=provider.getUserEmail();
        confirmEmail.setText(temp_email);

        String temp_phone=provider.getUserPhone();
        confirmPhone.setText(temp_phone);


        Double temp_dealPrice=thisBid.getBidAmount();
        confirmPrice.setText(Double.toString(temp_dealPrice));


        //settle pay button
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterChooseBidActivity.this, RequesterAssignedListActivity.class);
                info2.putExtra("userId",userId);
                task.setTaskStatus("assigned");
                task.setChoosenBid(thisBid);
                task.setTaskProvider(thisBid.getProviderId());
                TaskController.requesterUpdateTask update2 = new TaskController.requesterUpdateTask();
                update2.execute(task);
                startActivity(info2);

            }
        });

        //settle decline button
        Button declineButton = (Button) findViewById(R.id.decline_button);
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //store the bid into canceledBidList
                task.addCanceledBid(thisBid);
                task.changeStatusAfterDeclineBid(thisBid);


                TaskController.requesterUpdateTask update2 = new TaskController.requesterUpdateTask();
                update2.execute(task);



                Intent info2 = new Intent(RequesterChooseBidActivity.this, RequesterAllListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
    }

    protected void onStart(){

        super.onStart();
        if(timer!=null) {

            timer.cancel();
        }
        else{
            timer = new Timer(true);
            myTask = new MyTask();

            timer.schedule(myTask,0,2000);
        }
        BidController bidController = new BidController();
        //check counter change
        BidCounter bidCounter = bidController.searchBidCounterOfThisRequester(userId);
        if(bidCounter==null){
            Log.i("Bid counter search error",".");
        }
        else{
            if(bidCounter.getCounter()!= bidCounter.getPreviousCounter()){
                Log.i("New Bid","New Bid");
                Log.i("bidCount",Integer.toString(bidCounter.getCounter()));
                openRequestInfoDialog();
                //update previousCounter
                bidCounter.setPreviousCounter(bidCounter.getCounter());
                BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
                updateBidCounterOfThisRequester.execute(bidCounter);
            }



        }
    }
    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterChooseBidActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.arg1==1)
            {
                //Print Toast or open dialog
                openRequestInfoDialog();
                msg.arg1 = 0;

            }
            return false;
        }
    });
    @Override
    protected void onStop(){
        super.onStop();
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(timer!=null) {
            timer.cancel();
            timer = null;
        }
    }
}