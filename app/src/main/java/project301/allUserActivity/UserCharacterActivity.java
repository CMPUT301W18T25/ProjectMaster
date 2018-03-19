


package project301.allUserActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import project301.R;
import project301.User;
import project301.controller.UserListController;
import project301.providerActivity.ProviderMainActivity;
import project301.requesterActivity.RequesterMainActivity;

/**
 * @classname : UserCharacterActivity
 * @class Detail : User in this activity can choose to be a provider or requestor
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * User in this activity can choose to be a provider or requestor
 */

@SuppressWarnings("ALL")
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
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                User user = new User();
                //noinspection ConstantConditions,ConstantConditions
                @SuppressWarnings("ConstantConditions") String userId = intent.getExtras().get("userId").toString();
                Log.i("userId:",userId);
                UserListController uc = new UserListController();
                user = uc.getAUserById(userId);

                //set user type
                user.setUserType("provider");
                uc.updateUser(user);

                Intent intent = new Intent (UserCharacterActivity.this, ProviderMainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);


            }
        });

        //settle requester button
        requesterButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {

                User user = new User();
                //noinspection ConstantConditions,ConstantConditions
                @SuppressWarnings("ConstantConditions") String userId = intent.getExtras().get("userId").toString();
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