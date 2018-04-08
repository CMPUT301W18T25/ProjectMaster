


package project301.allUserActivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project301.R;
import project301.Task;
import project301.User;
import project301.controller.FileSystemController;
import project301.controller.TaskController;
import project301.controller.UserController;
import project301.providerActivity.ProviderMainActivity;
import project301.requesterActivity.RequesterMainActivity;

/**
 * User in this activity can choose to be a provider or requestor
 * @classname : UserCharacterActivity
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
    private String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_character);
        final Intent intent = getIntent();
        userId = intent.getExtras().get("userId").toString();


        providerButton = (Button) findViewById(R.id.provider_button);
        requesterButton = (Button) findViewById(R.id.requester_button);

        //settle provider button
        providerButton.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("ConstantConditions")
            @Override
            public void onClick(View view) {
                User user = new User();
                //noinspection ConstantConditions,ConstantConditions
                Log.i("userId:",userId);
                UserController uc = new UserController();
                user = uc.getAUserById(userId);

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

                UserController uc = new UserController();
                user = uc.getAUserById(userId);




                Intent intent = new Intent (UserCharacterActivity.this, RequesterMainActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });


        ArrayList<Task> tasklist= new ArrayList<>();
        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);
        try {
            tasklist= search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        FileSystemController FC = new FileSystemController();
        for(Task task: tasklist){
            FC.saveToFile(task,"sent",getApplication());
        }
    }

    private void gotoRequester(){

    }

    private void gotoProvider(){

    }

}