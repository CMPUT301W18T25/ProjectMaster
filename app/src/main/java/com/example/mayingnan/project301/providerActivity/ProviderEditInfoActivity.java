package com.example.mayingnan.project301.providerActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.controller.UserListController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ProviderEditInfoActivity extends AppCompatActivity {

    private String userId;

    private String userName;
    private String editName;
    private String editEmail;
    private String editPhone;
    private String editPassword;
    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwordText;
    private Button saveButton;
    private Button backButton;
    private UserListController userListControl;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);
        final Intent intent = getIntent();
        userId = intent.getExtras().get("userId").toString();

        //match edit text
        usernameText = findViewById(R.id.edit_name);
        emailText = findViewById(R.id.edit_email);
        mobileText = findViewById(R.id.edit_phone);
        passwordText = findViewById(R.id.edit_passward);


        //settle save button
        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get user input
                editName = usernameText.getText().toString();
                editEmail = emailText.getText().toString();
                editPhone = mobileText.getText().toString();
                editPassword = passwordText.getText().toString();

                //get user by userName
                UserListController.GetAllUsers getAllUsers = new UserListController.GetAllUsers();
                getAllUsers.execute(userName);

                ArrayList<User> Userlist = null;
                try{
                    Userlist = getAllUsers.get();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch (ExecutionException e){
                    e.printStackTrace();
                }
                User user = Userlist.get(0);

                //update user info
                user.setUserName(editName);
                user.setUserEmail(editEmail);
                user.setUserPhone(editPhone);
                user.setUserPassword(editPassword);

                //need to update online
                //???

                //change activity
                Intent info2 = new Intent(ProviderEditInfoActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });


        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(ProviderEditInfoActivity.this, ProviderMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);
            }
        });
    }

    public void  saveButton(){

    }

    public void backButton(){

    }

}
