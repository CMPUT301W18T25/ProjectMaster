package project301.requesterActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import project301.R;
import project301.Task;
import project301.controller.TaskController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : RequesterEditListActivity
 * @class Detail : Requesdter edit list is to show a list of posted task, which support click to check details and edit task.
 *
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterEditListActivity extends AppCompatActivity {
    private ListView postedTaskList;
    private String userName;
    private String userId;
    private static final String FILENAME = "ProjectMaster.sav";
    private ArrayList<Task> tasklist;

    @SuppressWarnings("ConstantConditions")
    @Override

    //when on create, settle two buttons: mainmenu, viewonmap and settle the posted task list click.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_edit_list);
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();


        //settle mainMenu button
        Button mainMenuButton = (Button) findViewById(R.id.main_button);
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditListActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        //settle viewOnMap button
        Button viewOnMapButton = (Button) findViewById(R.id.c_map_button);
        viewOnMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditListActivity.this, RequesterMapActivity.class);
                info2.putExtra("userId",userId);
                startActivity(info2);

            }
        });

        // settle click on posted task list
        postedTaskList = (ListView) findViewById(R.id.post_list);
        postedTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int index, long r_id) {
                Intent info1 = new Intent(RequesterEditListActivity.this, RequesterViewTaskActivity.class);
                info1.putExtra("info", index);
                info1.putExtra("userId",userId);
                startActivity(info1);
            }
        });


    }

    @Override
    //when on start, first sleep two seconds to wait for elasticsearch from database
    //then get data from database according to userid.
    //then settle the newest task list ro adapter.
    protected void onStart(){
        super.onStart();


        // time sleep to wait for elsticsearch finish
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //get data from database according to userid.
        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);

        tasklist = new ArrayList<Task>();
        try {
            tasklist= search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        //settle the newest list to adapter
        RequesterAdapter adapter = new RequesterAdapter(this, tasklist);
        this.postedTaskList.setAdapter(adapter);

    }




}
