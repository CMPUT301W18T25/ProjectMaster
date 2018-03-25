package project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import project301.R;
import project301.User;
import project301.controller.UserController;

/**
 * @classname : ProviderEditInfoActivity
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * this class used to change profile of the user, such as user name.
 */



@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderEditInfoActivity extends AppCompatActivity {

    private String userId;

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
    private User user;


    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        //get userId and user
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

                //get user input
                //editName = usernameText.getText().toString();
                editEmail = emailText.getText().toString();
                editPhone = mobileText.getText().toString();
                editPassword = passwordText.getText().toString();

                Log.i("editEmail",editEmail);

                //update user info
                //user.setUserName(editName);
                user.setUserEmail(editEmail);
                user.setUserPhone(editPhone);
                user.setUserPassword(editPassword);

                //update user
                UserController.updateUser updateUser= new UserController.updateUser();
                updateUser.execute(user);

                //testing result
                Log.i("resultid:",user.getResultId());

                //change activity
                Intent info2 = new Intent(ProviderEditInfoActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);

                //wait for update
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(info2);

            }
        });


        //settle back button (no data saving)
        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //change activity
                Intent info2 = new Intent(ProviderEditInfoActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);
            }
        });
    }

    public void onStart() {
        super.onStart();

        //get current user
        UserController uc2 = new UserController();
        user = uc2.getAUserById(userId);

        // put user original info onto UI
        String temp_name=user.getUserName();
        usernameText.setText(temp_name);

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

        String temp_status=user.getUserPassword();
        passwordText.setText(temp_status);
    }

}
