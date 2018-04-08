package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.novoda.merlin.MerlinsBeard;

import project301.BidCounter;
import project301.R;
import project301.Task;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.TaskController;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

/**
 * Detail : Requesdter all list is to show a list of posted task, which support click to get information.
 * @Date :   01/04/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterAllListActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterAllListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView listView;
    private String userName;
    private String userId;
    private static final String FILENAME = "ProjectMaster.sav";
    private ArrayList<Task> tasklist = new ArrayList<>();
    protected MerlinsBeard merlinsBeard;
    protected Context context;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;
    ArrayList<Task> assignedTaskList = new ArrayList<>();
    RequesterAdapter adapter;
    private Timer timer;
    MyTask myTask = new MyTask();

    /**
     * monitor the new status update
     */
    private class MyTask extends TimerTask{

        public void run(){
            BidController bidController = new BidController();
            //check counter change
            BidCounter bidCounter = bidController.searchBidCounterOfThisRequester(userId);
            if(bidCounter==null){
                Log.i("Bid counter search error",".");
            }
            else{
                OfflineController offlineController = new OfflineController();
                boolean executeOffline = offlineController.tryToExecuteOfflineTasks(getApplication());
                if(executeOffline){
                    Message msg = new Message();
                    msg.arg1 = 2;
                    handler.sendMessage(msg);

                }

                if(bidCounter.getCounter()!= bidCounter.getPreviousCounter()){
                    Message msg1 = new Message();
                    msg1.arg1 = 1;
                    handler.sendMessage(msg1);

                    //update previousCounter
                    bidCounter.setPreviousCounter(bidCounter.getCounter());
                    BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
                    updateBidCounterOfThisRequester.execute(bidCounter);
                }
            }

        }
    }


    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RequesterAdapter(this, assignedTaskList);
        setContentView(R.layout.requester_all_list);

        final Intent intent = getIntent();
        context=getApplicationContext();

        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        merlinsBeard = MerlinsBeard.from(context);



        //settle mainMenu button
        Button mainMenuButton = (Button) findViewById(R.id.main_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterAllListActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle viewOnMap button
        Button viewOnMapButton = (Button) findViewById(R.id.c_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterAllListActivity.this, RequesterMapActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        // settle click on post task list
        listView = (ListView) findViewById(R.id.post_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                String status = tasklist.get(index).getTaskStatus();
                Intent info1;
                if(status.equals("request")){
                    info1 = new Intent(RequesterAllListActivity.this, RequesterViewTaskRequestActivity.class);
                }
                else if(status.equals("bidden")){
                    info1 = new Intent(RequesterAllListActivity.this, RequesterViewTaskBiddenActivity.class);
                }
                else if(status.equals("assigned")){
                    info1 = new Intent(RequesterAllListActivity.this, RequesterViewTaskAssignedActivity.class);
                }
                else{
                    info1 = new Intent(RequesterAllListActivity.this, RequesterViewTaskDoneActivity.class);
                }
                info1.putExtra("info", index);
                info1.putExtra("userId",userId);
                info1.putExtra("activity","allList");
                startActivity(info1);
            }
        });


        //set listview pull down to renew
        ListView mListView = (ListView) findViewById(R.id.post_list);
        final SwipeRefreshLayout mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_ly);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //set 2 seconds to implement
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        renewTheList();
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }
    /**
     * This method is called when swipe refresh is pulled down
     */
    @Override
    public void onRefresh() {
        renewTheList();
    }

    @Override
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
        renewTheList();


    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterAllListActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");

        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void renewTheList(){

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
        FileSystemController FC = new FileSystemController();
        if(merlinsBeard.isConnected()){

            //try again, will change in the future

            try {
                Thread.sleep(1000);
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
            FC.deleteAllFiles(getApplication(),"sent");
            for(Task task:tasklist){
                FC.saveToFile(task,"sent",getApplication());
            }
        }
        // FC.deleteAllFiles(getApplication(),"sent");
        tasklist = FC.loadSentTasksFromFile(getApplication());
        assignedTaskList.clear();
        for(Task task: tasklist){


            assignedTaskList.add(task);


        }

        adapter.notifyDataSetChanged();
        // Attach the adapter to a ListView
        this.listView.setAdapter(adapter);
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
            else if(msg.arg1 == 2){
                renewTheList();
                msg.arg1=0;
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