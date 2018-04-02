package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project301.BidCounter;
import project301.GlobalCounter;
import project301.R;
import project301.User;
import project301.controller.BidController;
import project301.controller.UserController;

/**
 * Detail :this class used to change profile of the user, such as user name.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterEditInfoActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterEditInfoActivity extends AppCompatActivity {

    private String userName;
    //private String editName;
    private String editEmail;
    private String editPhone;
    private String editPassword;
    private TextView usernameText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwordText;
    private Button saveButton;
    private Button backButton;
    private UserController userListControl;
    private String userId;
    private User user;
    private Context context;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);
        final Intent intent = getIntent();
        this.context = getApplicationContext();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        UserController uc = new UserController();
        user = uc.getAUserById(userId);

        //match edit text
        usernameText = findViewById(R.id.edit_name);
        emailText = findViewById(R.id.edit_email);
        mobileText = findViewById(R.id.edit_phone);
        passwordText = findViewById(R.id.edit_passward);


        //settle save button
        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_namelength(usernameText.getText().toString())) {

                //get user input
                //editName = usernameText.getText().toString();
                editEmail = emailText.getText().toString();
                editPhone = mobileText.getText().toString();
                editPassword = passwordText.getText().toString();

                //update user info
                //user.setUserName(editName);
                user.setUserEmail(editEmail);
                user.setUserPhone(editPhone);
                user.setUserPassword(editPassword);

                //update user
                UserController.updateUser updateUser= new UserController.updateUser();
                updateUser.execute(user);

                //change activity
                Intent info2 = new Intent(RequesterEditInfoActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);

                //wait for update
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(info2);
                } else {
                    Toast toast = Toast.makeText(context, "The maximum length of username is 8", Toast.LENGTH_LONG);
                    toast.show();
                }

            }
        });


        //settle back button
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //change activity
                Intent info2 = new Intent(RequesterEditInfoActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
    }

    public void onStart() {
        super.onStart();

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

        //get current user
        UserController uc2 = new UserController();
        user = uc2.getAUserById(userId);
        // put user original info onto UI
        String temp_name=user.getUserName();
        usernameText.setText(temp_name);

        //check empty
        if (user.getUserEmail()==null){
            emailText.setText("");
        }else {
            String temp_detail = user.getUserEmail();
            emailText.setText(temp_detail);
        }

        if (user.getUserPhone()==null) {
            mobileText.setText("");
        }else{
            String temp_phone = user.getUserPhone();
            mobileText.setText(temp_phone);
        }

        //set password
        String temp_status=user.getUserPassword();
        passwordText.setText(temp_status);



    }
    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog


        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterEditInfoActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean check_namelength(String name)
    {
        if(name.length()>=9 ){
            return false;
        }
        return true;
    }


}
