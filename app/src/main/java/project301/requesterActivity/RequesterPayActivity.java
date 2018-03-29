

package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import project301.Bid;
import project301.GlobalCounter;
import project301.R;
import project301.User;
import project301.controller.BidController;
import project301.controller.TaskController;
import project301.controller.UserController;

/**
 * Detail : pay class is to change the status of task from bidding to processing
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterPayActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterPayActivity extends AppCompatActivity {

    private String userId;
    private User user;
    private TextView confirmName;
    private TextView confirmEmail;
    private TextView confirmPhone;
    private TextView confirmPrice;

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_pay);
        final Intent intent = getIntent();

        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        //get all bids

        //get index of target bid

        //get providerid of target bid

        //get user
        UserController uc = new UserController();
        user = uc.getAUserById(userId);

        // find view by id.
        confirmName = (TextView) findViewById(R.id.confirm_name);
        confirmEmail= (TextView) findViewById(R.id.confirm_email);
        confirmPhone = (TextView) findViewById(R.id.confirm_phone);
        confirmPrice = (TextView) findViewById(R.id.confirm_price);


        //set information to textview
        String temp_name=user.getUserName();
        confirmName.setText(temp_name);

        String temp_email=user.getUserEmail();
        confirmEmail.setText(temp_email);

        String temp_phone=user.getUserPhone();
        confirmPhone.setText(temp_phone);


        //Double temp_idealprice=view_task.getTaskIdealPrice();
        //view_idealprice.setText(Double.toString(temp_idealprice));







        //settle pay button
        Button payButton = (Button) findViewById(R.id.pay_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPayActivity.this, RequesterEditListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle decline button
        Button declineButton = (Button) findViewById(R.id.decline_button);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPayActivity.this, RequesterEditListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPayActivity.this, RequesterViewTaskActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
    }





    protected void onStart(){

        super.onStart();
        BidController bidController = new BidController();
        //check counter change
        int newCount = bidController.searchBidCounterOfThisRequester(userId);
        if(newCount!= GlobalCounter.count){
            GlobalCounter.count = newCount;
            Log.i("New Bid","New Bid");
        }



    }
}