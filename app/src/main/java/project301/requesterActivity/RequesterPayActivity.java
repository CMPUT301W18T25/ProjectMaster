

package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import project301.GlobalCounter;
import project301.R;
import project301.controller.BidController;

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

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_pay);
        final Intent intent = getIntent();

        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

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