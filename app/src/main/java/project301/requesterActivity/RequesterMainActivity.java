package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project301.R;
import project301.Task;
import project301.controller.FileSystemController;
import project301.controller.TaskController;

/**
 * Detail : requester main is for user to choose their actions: post task,view and edit task or edit profile
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterMainActivity
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterMainActivity extends AppCompatActivity {


    private String userId;

    @SuppressWarnings("ConstantConditions")
    @Override

    //when on create, settle three buttons: postnewtask, view and edit, and edit profile
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_main);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        //settle postNewTask button
        Button postNewTaskButton = (Button) findViewById(R.id.post_button);
        postNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterPostTaskActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });




        //settle view button
        Button viewButton = (Button) findViewById(R.id.edit_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditListActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle editProfile button
        Button profileButton = (Button) findViewById(R.id.profile_button);
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterMainActivity.this, RequesterEditInfoActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });
        //clean offline files and load new files

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

}