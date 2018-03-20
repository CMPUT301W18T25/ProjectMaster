package project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.utilities.FileIOUtil;
import project301.R;
import project301.Task;
import project301.controller.TaskController;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.NetworkStatus;
import com.novoda.merlin.registerable.bind.Bindable;
import com.novoda.merlin.registerable.connection.Connectable;
import com.novoda.merlin.registerable.disconnection.Disconnectable;
import com.novoda.merlin.MerlinsBeard;


/**
 * Detail : RequesterPostTaskActivity is to allow user to post a new task , some informatin cannot be leaft empty.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterPostTaskActivity
 */




@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterPostTaskActivity extends AppCompatActivity implements Connectable, Disconnectable, Bindable{
    private Context context;

    private EditText post_name;
    private EditText post_detail;
    private EditText post_destination;
    private EditText post_ideal_price;
    private ImageView post_photo;
    private Button submitButton;
    private Button cancelButton;
    private String userId;
    protected Merlin merlin;
    protected MerlinsBeard merlinsBeard;




    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_post_task);
        context=getApplicationContext();
        final Intent intent = getIntent();
        //noinspection ConstantConditions,ConstantConditions
        userId = intent.getExtras().get("userId").toString();
        // monitor network connectivity
        merlin = new Merlin.Builder().withConnectableCallbacks().withDisconnectableCallbacks().withBindableCallbacks().build(this);
        merlin.registerConnectable(this);
        merlin.registerDisconnectable(this);
        merlin.registerBindable(this);

        merlinsBeard = MerlinsBeard.from(context);

        //find view by id.
        post_name = (EditText) findViewById(R.id.c_task_name);
        post_detail = (EditText) findViewById(R.id.c_task_detail);
        post_destination = (EditText) findViewById(R.id.c_task_location);
        post_ideal_price = (EditText) findViewById(R.id.c_task_idealprice);
        post_photo = (ImageView) findViewById(R.id.c_task_photo);
        submitButton=(Button)findViewById(R.id.submit_button);
        cancelButton=(Button)findViewById(R.id.cancel_button);


        //submitButton click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                // check empty of needed information
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
                    if(merlinsBeard.isConnected()) {
                        //Log.i("MerlinBeard","connected");
                        OfflineController offlineController = new OfflineController();
                        offlineController.tryToExecuteOfflineTasks(context);
                        TaskController.addTask addTaskCtl = new TaskController.addTask();
                        addTaskCtl.execute(new_task);
                    }
                    //doing offline
                    else{
                        FileSystemController fileSystemController = new FileSystemController();
                        Log.i("offlineAdd","test");
                        String uniqueID = UUID.randomUUID().toString();
                        new_task.setId(uniqueID);

                        fileSystemController.saveToFile(new_task,"offlineAdd",context);
                    }

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




        //cancelButton click
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
    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
    }

    @Override
    protected void onPause() {
        super.onPause();
        merlin.unbind();
    }

    @Override
    public void onBind(NetworkStatus networkStatus) {
        if (networkStatus.isAvailable()) {
            onConnect();
        } else if (!networkStatus.isAvailable()) {
            onDisconnect();
        }
    }

    @Override
    public void onConnect() {
        // try to update offline accepted request
        OfflineController offlineController = new OfflineController();
        offlineController.tryToExecuteOfflineTasks(context);
    }

    @Override
    public void onDisconnect() {
        Log.i("Hasn't connected",".");

    }

    private boolean check_empty(String name, String destination, String ideal_price)
    {
        if(name.length()==0 || destination.length()==0|| ideal_price.length()==0 ){
            return false;
        }
        return true;
    }


}



