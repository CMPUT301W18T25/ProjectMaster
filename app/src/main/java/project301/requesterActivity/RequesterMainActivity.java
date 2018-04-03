package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import project301.BidCounter;
import project301.GlobalCounter;
import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.controller.BidController;
import project301.controller.OfflineController;
import project301.providerActivity.ProviderMainActivity;

/**
 * Detail : requester main is for user to choose their actions: post task,view and edit task or edit profile
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterMainActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterMainActivity extends AppCompatActivity {


    private String userId;
    private Timer timer;
    MyTask myTask = new MyTask();
    private class MyTask extends TimerTask {
        public void run() {
            Log.i("Timer8","run");
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
                    Log.i("New Bid","New Bid");
                    Log.i("bidCount",Integer.toString(bidCounter.getCounter()));
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

    //when on create, settle three buttons: postnewtask, view and edit, and edit profile
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.requester_main);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();



        //settle postNewTask button
        Button postNewTaskButton = (Button) findViewById(R.id.post_button);
        postNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterPostTaskActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
        //settle view button
        Button viewButton = (Button) findViewById(R.id.edit_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterAllListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });


        //settle editProfile button
        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditInfoActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle bidden button
        Button biddenButton = (Button) findViewById(R.id.bidden_button);
        biddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterBiddenListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle done button
        Button doneButton = (Button) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterDoneListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle assigned button
        Button assignedButton = (Button) findViewById(R.id.assigned_button);
        assignedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterAssignedListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle logout Button
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RequesterMainActivity.this, LogInActivity.class);
                //intent.putExtra("userId",userId);
                startActivity(intent);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterMainActivity.this);
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