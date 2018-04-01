package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.novoda.merlin.MerlinsBeard;
import project301.GlobalCounter;
import project301.R;
import project301.Task;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Detail : Requesdter edit list is to show a list of posted task, which support click to check details and edit task.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterAllListActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterAssignedListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private ListView assignedTaskList;
    private String userName;
    private String userId;
    private static final String FILENAME = "ProjectMaster.sav";
    private ArrayList<Task> tasklist;
    protected MerlinsBeard merlinsBeard;
    protected Context context;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeLayout;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_assigned_list);
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
                Intent info2 = new Intent(RequesterAssignedListActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle viewOnMap button
        Button viewOnMapButton = (Button) findViewById(R.id.c_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterAssignedListActivity.this, RequesterMapActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        // settle click on post task list
        assignedTaskList = (ListView) findViewById(R.id.post_list);
        assignedTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(RequesterAssignedListActivity.this, RequesterViewTaskAssignedActivity.class);
                info1.putExtra("info", index);
                info1.putExtra("userId",userId);
                info1.putExtra("activity","assignedList");
                startActivity(info1);
            }
        });


        //设置在listview上下拉刷新的监听
        ListView mListView = (ListView) findViewById(R.id.post_list);
        final SwipeRefreshLayout mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_ly);

        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
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
        renewTheList();

        //Log.i("Sign", Integer.toString(tasklist.size()));

    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog


        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterAssignedListActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void renewTheList(){

        BidController bidController = new BidController();
        //check counter change
        int newCount = bidController.searchBidCounterOfThisRequester(userId);
        Log.i("bidCount",Integer.toString(newCount));

        if(newCount!= GlobalCounter.count && newCount>0){
            GlobalCounter.count = newCount;
            Log.i("New Bid","New Bid");
            openRequestInfoDialog();
        }
        FileSystemController FC = new FileSystemController();
        if(merlinsBeard.isConnected()){
            OfflineController offlineController = new OfflineController();
            offlineController.tryToExecuteOfflineTasks(getApplication());
            //try again, will change in the future
            offlineController.tryToExecuteOfflineTasks(getApplication());

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
        ArrayList<Task> assignedTaskList = new ArrayList<>();
        for(Task task: tasklist){
            if(task.getTaskStatus().equals("assigned")){

                assignedTaskList.add(task);

            }
        }

        RequesterAdapter adapter = new RequesterAdapter(this, assignedTaskList);
        adapter.notifyDataSetChanged();
        // Attach the adapter to a ListView
        this.assignedTaskList.setAdapter(adapter);
    }

}
