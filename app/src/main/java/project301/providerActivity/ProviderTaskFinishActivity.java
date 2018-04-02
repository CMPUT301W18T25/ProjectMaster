package project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import project301.Bid;
import project301.R;
import project301.Task;
import project301.User;
import project301.controller.TaskController;
import project301.controller.UserController;

import java.util.ArrayList;
import java.util.Map;

/**
 * After a task finished (or assigned), this Activity class shows the detail of the task
 * @classname : ProviderTaskFinishActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderTaskFinishActivity extends AppCompatActivity {

    //UI variable
    private TextView taskName;
    private TextView taskDetail;
    private TextView taskLocation;
    private TextView taskIdealPrice;
    private TextView taskBidPrice;
    private TextView taskStatus;
    private TextView requesterName_view;
    private TextView requesterPhone_view;
    private TextView requesterEmail_view;
    private Map providerMap;
    private ArrayList tasklist;

    // task variable
    private String userName;
    private String userId;
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
        setContentView(R.layout.provider_task_finish);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        // find view by id.
        taskName = (TextView) findViewById(R.id.p_task_name);
        taskDetail= (TextView) findViewById(R.id.p_task_detail);
        taskLocation = (TextView) findViewById(R.id.p_task_location);
        taskIdealPrice = (TextView) findViewById(R.id.p_task_idealprice);
        taskBidPrice = (TextView) findViewById(R.id.p_task_bidprice);
        taskStatus = (TextView) findViewById(R.id.p_task_status);

        // find requester info view by id
        requesterName_view = (TextView) findViewById(R.id.r_name);
        requesterPhone_view = (TextView) findViewById(R.id.r_phone);
        requesterEmail_view = (TextView) findViewById(R.id.r_email);

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


        // get information from target task and set information
        String temp_name=view_task.getTaskName();
        taskName.setText(temp_name);

        String temp_detail=view_task.getTaskDetails();
        taskDetail.setText(temp_detail);

        String temp_destination=view_task.getTaskAddress();
        if(temp_destination!=null){
            taskLocation.setText(temp_destination);
        }


        String temp_status=view_task.getTaskStatus();
        taskStatus.setText(temp_status);

        Double temp_idealprice=view_task.getTaskIdealPrice();
        if(temp_idealprice!=null){
            taskIdealPrice.setText(Double.toString(temp_idealprice));
        }

        Bid temp_bid=view_task.getChoosenBid();
        Double bidPrice = temp_bid.getBidAmount();
        if(bidPrice!=null) {
            taskBidPrice.setText(Double.toString(bidPrice));
        }

        //settle back button : jump back to history
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderTaskFinishActivity.this, ProviderBidHistoryActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("content","all");
                startActivity(info2);

            }
        });
    }

}
