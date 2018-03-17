


package com.example.mayingnan.project301.allUserActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.controller.UserListController;
import com.example.mayingnan.project301.providerActivity.ProviderMainActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterMainActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class UserCharacterActivity extends AppCompatActivity {

    private Button providerButton;
    private Button requesterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_character);
        final Intent intent = getIntent();


        providerButton = (Button) findViewById(R.id.provider_button);
        requesterButton = (Button) findViewById(R.id.requester_button);

        //settle provider button
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User();
                String userId = intent.getExtras().get("userId").toString();
                Log.i("userId:",userId);
                //UserListController uc = new UserListController();
                //user = uc.getAUserById(userId);

                //set user type
                //user.setUserType("provider");
                //uc.updateUser(user);

                Intent intent = new Intent (UserCharacterActivity.this, ProviderMainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);


            }
        });

        //settle requester button
        requesterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user = new User();
                String userId = intent.getExtras().get("userId").toString();
                Log.i("userId",userId);

                UserListController uc = new UserListController();
                user = uc.getAUserById(userId);
                Log.i("usernamedd",user.getUserName());

                //set user type
                user.setUserType("requester");
                uc.updateUser(user);


                Intent intent = new Intent (UserCharacterActivity.this, RequesterMainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });




    }

    private void gotoRequester(){

    }

    private void gotoProvider(){

    }

}