package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import project301.R;
import project301.Task;
import project301.controller.TaskController;
import project301.utilities.FileIOUtil;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * @classname : RequesterEditTaskActivity
 * @class Detail :RequesterEditTaskActivity allows user to edit their task.
 *
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */



@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterEditTaskActivity extends AppCompatActivity {
    private Context context;
    private String userId;
    private String userName;
    private EditText edit_name;
    private EditText edit_detail;
    private EditText edit_destination;
    private EditText edit_idealprice;
    private ImageView edit_photo;
    private Task target_task;
    private ArrayList<Task> task_list;
    private ArrayList<Task> start_list;
    private String view_index;
    private Integer last_index;



    @SuppressWarnings("ConstantConditions")
    @Override
    //when oncreate, first get newest data from database.
    //then get index to settle original information.
    //then settle save button and back. Click save, information get saved, Click back, information not saved.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_edit_task);
        context=getApplicationContext();
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();


        //find view by id.

        edit_name = (EditText) findViewById(R.id.c_edit_name);
        edit_detail = (EditText) findViewById(R.id.c_edit_detail);
        edit_destination = (EditText) findViewById(R.id.c_edit_destination);
        edit_idealprice = (EditText) findViewById(R.id.c_edit_idealprice);
        edit_photo = (ImageView) findViewById(R.id.c_edit_photo);


        //time sleep

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        //get newest data from database.

        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
        search.execute(userId);

        start_list = new ArrayList<Task>();
        try {
            start_list= search.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // get index of target task

        view_index = intent.getExtras().get("info").toString();
        final int index = Integer.parseInt(view_index);


        // get target task
        target_task=start_list.get(index);


        // get information from target task and set information
        String temp_name=target_task.getTaskName();
        edit_name.setText(temp_name);

        String temp_detail=target_task.getTaskDetails();
        edit_detail.setText(temp_detail);

        String temp_destination=target_task.getTaskAddress();
        edit_destination.setText(temp_destination);

        Double temp_idealprice=target_task.getTaskIdealPrice();
        edit_idealprice.setText(Double.toString(temp_idealprice));


        //settle save button click

        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check empty, name,destination and idealprice cannot leave empty.
                if (check_empty(edit_name.getText().toString(),edit_destination.getText().toString(),
                        edit_idealprice.getText().toString())){


                    //interface jump
                    Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskActivity.class);


                    //get data from database
                    task_list = new ArrayList<>();
                    TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
                    search.execute(userId);
                    try {
                        task_list= search.get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    // get index of target task

                    last_index = task_list.size()-1;
                    view_index=Integer.toString(last_index);

                    // get target task
                    target_task=task_list.get(index);

                    //set data
                    target_task.setTaskName(edit_name.getText().toString());
                    target_task.setTaskDetails(edit_detail.getText().toString());
                    target_task.setTaskAddress(edit_destination.getText().toString());
                    target_task.setTaskIdealPrice(Double.parseDouble(edit_idealprice.getText().toString()));
                    target_task.setTaskRequester(userId);
                    //to do:set photo

                    //upload to database
                    TaskController.requesterUpdateTask update = new TaskController.requesterUpdateTask();
                    update.execute(target_task);

                    info2.putExtra("userId",userId);
                    info2.putExtra("info",view_index);

                    startActivity(info2);
                    FileIOUtil fileIOUtil = new FileIOUtil();
                    fileIOUtil.saveSentTaskInFile(target_task,context);




                }else{
                    Toast toast = Toast.makeText(context,"Enter name, detail destination, ideal price, date and time",Toast.LENGTH_LONG);
                    toast.show();
                }




            }
        });




        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //interface jump
                Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("info",view_index);
                startActivity(info2);

            }
        });
    }




    /**
     * method check empty to make sure below parameters are not empty
     * @param name
     * @param detail
     * @param destination
     * @param ideal_price
     * @return true or false
     */


    private boolean check_empty(String name, String destination, String ideal_price)
    {
        if(name.length()==0 || destination.length()==0|| ideal_price.length()==0 ){
            return false;
        }
        return true;
    }



}