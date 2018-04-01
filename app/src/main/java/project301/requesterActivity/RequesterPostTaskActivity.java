package project301.requesterActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import project301.GlobalCounter;
import project301.Photo;
import project301.allUserActivity.CameraActivity;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.PlaceArrayAdapterController;
import project301.utilities.FileIOUtil;
import project301.R;
import project301.Task;
import project301.controller.TaskController;


import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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
public class RequesterPostTaskActivity extends AppCompatActivity implements Connectable, Disconnectable, Bindable,
    GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private Context context;

    private EditText post_name;
    private EditText post_detail;
    private AutoCompleteTextView post_destination;
    private EditText post_ideal_price;
    private ImageView post_photo;
    private Button submitButton;
    private Button cancelButton;
    private String userId;
    protected Merlin merlin;
    protected MerlinsBeard merlinsBeard;

    // Address autocomplete stuff
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private PlaceArrayAdapterController mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private Place taskPlace;
    // Photo stuff
    public static final int GET_FROM_GALLERY = 3;

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

        post_destination = (AutoCompleteTextView) findViewById(R.id.c_task_location);
        post_destination.setThreshold(3);

        post_ideal_price = (EditText) findViewById(R.id.c_task_idealprice);
        post_photo = (ImageView) findViewById(R.id.c_task_photo);
        submitButton=(Button)findViewById(R.id.submit_button);
        cancelButton=(Button)findViewById(R.id.cancel_button);



        // Location autocomplete stuff
        mGoogleApiClient = new GoogleApiClient.Builder(RequesterPostTaskActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        post_destination.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapterController(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        post_destination.setAdapter(mPlaceArrayAdapter);


        //submitButton click
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                // check empty and length of needed information
                if(check_detaillength(post_detail.getText().toString())){
                if (check_titlelength(post_name.getText().toString())){
                if (check_empty(post_name.getText().toString(),post_destination.getText().toString(),
                        post_ideal_price.getText().toString())){


                    //interface jump
                    Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterAllListActivity.class);

                    //set data
                    Task new_task = new Task();
                    new_task.setTaskName(post_name.getText().toString());
                    new_task.setTaskDetails(post_detail.getText().toString());
                    new_task.setTaskAddress(post_destination.getText().toString());
                    new_task.setTaskIdealPrice(Double.parseDouble(post_ideal_price.getText().toString()));
                    new_task.setTaskRequester(userId);
                    if (taskPlace != null){
                        Log.d(LOG_TAG,"Task lat long: "+taskPlace.getLatLng());
                        new_task.setTasklatitude(taskPlace.getLatLng().latitude);
                        new_task.setTasklgtitude(taskPlace.getLatLng().longitude);
                    }
                    else{
                        Log.d(LOG_TAG,"No task lat and long");
                        new_task.setTasklatitude(null);
                        new_task.setTasklgtitude(null);
                    }

                    //to do:set photo
                    if (post_photo != null){
                        Photo new_photo = new Photo();
                        new_photo.addPhoto(String.valueOf(post_photo.getDrawingCache()));
                        new_task.setTaskPhoto(new_photo);
                    }

                    //upload new task data to database
                    if(merlinsBeard.isConnected()) {
                        //Log.i("MerlinBeard","connected");
                        OfflineController offlineController = new OfflineController();
                        offlineController.tryToExecuteOfflineTasks(getApplication());
                        TaskController.addTask addTaskCtl = new TaskController.addTask();
                        addTaskCtl.execute(new_task);
                        FileIOUtil fileIOUtil = new FileIOUtil();
                        fileIOUtil.saveSentTaskInFile(new_task,context);
                    }
                    //doing offline
                    else{
                        FileSystemController fileSystemController = new FileSystemController();
                        Log.i("offlineAdd","test");
                        String uniqueID = UUID.randomUUID().toString();
                        new_task.setId(uniqueID);

                        fileSystemController.saveToFile(new_task,"offlineAdd",getApplication());
                    }

                    info2.putExtra("userId",userId);
                    startActivity(info2);

                }else{
                    Toast toast = Toast.makeText(context,"Enter name, detail destination, ideal price, date and time",Toast.LENGTH_LONG);
                    toast.show();
                }}else {
                    Toast toast = Toast.makeText(context,"The maximum length of name is 30 characters",Toast.LENGTH_LONG);
                    toast.show();

                }}else {
                    Toast toast = Toast.makeText(context, "The maximum length of detail is 300 characters", Toast.LENGTH_LONG);
                    toast.show();
                }



            }
        });


        //cameraButton click
        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.floating_addcamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterPostTaskActivity.this, CameraActivity.class);
                startActivity(info2);

            }
        });

        //photoButton click
        FloatingActionButton photoButton = (FloatingActionButton) findViewById(R.id.floating_addphoto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Requester post task activity","take photo clicked");
                // source: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("RequesterPostTask", "onActivityResult");
        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            post_photo.setImageBitmap(bitmap);
        }
    }

    protected void onStart(){

        super.onStart();
        BidController bidController = new BidController();
        //check counter change
        int newCount = bidController.searchBidCounterOfThisRequester(userId);
        if(newCount!= GlobalCounter.count && newCount>0){
            GlobalCounter.count = newCount;
            Log.i("New Bid","New Bid");
            openRequestInfoDialog();
        }
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
        offlineController.tryToExecuteOfflineTasks(getApplication());
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

    private boolean check_titlelength(String name)
    {
        if(name.length()>=31 ){
            return false;
        }
        return true;
    }

    private boolean check_detaillength(String detail)
    {
        if(detail.length()>=301 ){
            return false;
        }
        return true;
    }

    // Autocomplete address
    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapterController.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            else{
                Log.d(LOG_TAG,"ResultCallback success");
            }
            // Selecting the first object buffer.
            final Place place = places.get(0);
            taskPlace = place;
            CharSequence attributions = places.getAttributions();


        }
    };

    @Override
    public void onConnected(Bundle bundle) {

        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.d(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterPostTaskActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}