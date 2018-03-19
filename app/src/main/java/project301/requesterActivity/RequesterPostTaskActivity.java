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

import project301.utilities.FileIOUtil;
import project301.R;
import project301.Task;
import project301.controller.TaskController;

/**
 * @classname : RequesterPostTaskActivity
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */





@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterPostTaskActivity extends AppCompatActivity {
    private Context context;

    private EditText post_name;
    private EditText post_detail;
    private EditText post_destination;
    private EditText post_ideal_price;
    private ImageView post_photo;
    private Button submitButton;
    private Button cancelButton;
    private String userId;



    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_post_task);
        context=getApplicationContext();
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();

        /**
         * find view by id.
         */
        post_name = (EditText) findViewById(R.id.c_task_name);
        post_detail = (EditText) findViewById(R.id.c_task_detail);
        post_destination = (EditText) findViewById(R.id.c_task_location);
        post_ideal_price = (EditText) findViewById(R.id.c_task_idealprice);
        post_photo = (ImageView) findViewById(R.id.c_task_photo);
        submitButton=(Button)findViewById(R.id.submit_button);
        cancelButton=(Button)findViewById(R.id.cancel_button);


        /**
         * submitButton click
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if (check_empty(post_name.getText().toString(),post_destination.getText().toString(),
                        post_ideal_price.getText().toString())){


                    //interface jump
                    Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterEditListActivity.class);

                    //set data
                    Task new_task = new Task();
                    new_task.setTaskName(post_name.getText().toString());
                    new_task.setTaskDetails(post_detail.getText().toString());
                    new_task.setTaskAddress(post_destination.getText().toString());
                    new_task.setTaskIdealPrice(Double.parseDouble(post_ideal_price.getText().toString()));
                    new_task.setTaskRequester(userId);
                    //to do:set photo


                    //upload new task data to database
                    TaskController.addTask addTaskCtl=new TaskController.addTask();
                    addTaskCtl.execute(new_task);



                    info2.putExtra("userId",userId);
                    startActivity(info2);
                    FileIOUtil fileIOUtil = new FileIOUtil();
                    fileIOUtil.saveSentTaskInFile(new_task,context);




                }else{
                    Toast toast = Toast.makeText(context,"Enter name, detail destination, ideal price, date and time",Toast.LENGTH_LONG);
                    toast.show();
                }



            }
        });




        /**
         * cancelButton click
         */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterMainActivity.class);
                info2.putExtra("userId",userId);
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
     * @return
     */


    private boolean check_empty(String name, String destination, String ideal_price)
    {
        if(name.length()==0 || destination.length()==0|| ideal_price.length()==0 ){
            return false;
        }
        return true;
    }


}




