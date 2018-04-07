package project301.requesterActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.novoda.merlin.MerlinsBeard;

import project301.BidCounter;
import project301.GlobalCounter;
import project301.Photo;
import project301.R;
import project301.Task;
import project301.allUserActivity.CameraActivity;
import project301.controller.BidController;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.PlaceArrayAdapterController;
import project301.controller.TaskController;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import static com.loopj.android.http.AsyncHttpClient.LOG_TAG;

/**
 * Detail :RequesterEditTaskActivity allows user to edit their task.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterEditTaskActivity
 */



@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterEditTaskActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private Context context;
    private String userId;
    private String userName;
    private EditText edit_name;
    private EditText edit_detail;
    private AutoCompleteTextView edit_destination;
    private EditText edit_idealprice;
    private Task target_task;
    private ArrayList<Task> task_list;
    private ArrayList<Task> start_list;
    private String view_index;
    private Integer last_index;
    protected MerlinsBeard merlinsBeard;
    private String temp_status;
    private LinearLayout myGallery;

    // Address autocomplete stuff
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));
    private PlaceArrayAdapterController mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private Place taskPlace;
    // Photo stuff
    public static final int GET_FROM_GALLERY = 3;
    private boolean setImage;
    private Photo task_photos;

    private Timer timer;
    MyTask myTask = new MyTask();
    private class MyTask extends TimerTask {
        public void run() {
            //Log.i("Timer7","run");
            BidController bidController = new BidController();
            //check counter change
            BidCounter bidCounter = bidController.searchBidCounterOfThisRequester(userId);
            if(bidCounter==null){
                Log.i("Bid counter search error",".");
            }
            else{
                OfflineController offlineController = new OfflineController();
                offlineController.tryToExecuteOfflineTasks(getApplication());
                if(bidCounter.getCounter()!= bidCounter.getPreviousCounter()){
                    Message msg = new Message();

                    msg.arg1 = 1;
                    handler.sendMessage(msg);

                    //update previousCounter
                    bidCounter.setPreviousCounter(bidCounter.getCounter());
                    BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
                    updateBidCounterOfThisRequester.execute(bidCounter);
                }
            }



        }
    };




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
        merlinsBeard = MerlinsBeard.from(context);

        FileSystemController FC = new FileSystemController();

        //find view by id.

        edit_name = (EditText) findViewById(R.id.c_edit_name);
        edit_detail = (EditText) findViewById(R.id.c_edit_detail);
        edit_destination = (AutoCompleteTextView) findViewById(R.id.c_edit_destination);
        edit_idealprice = (EditText) findViewById(R.id.c_edit_idealprice);
        myGallery = (LinearLayout)findViewById(R.id.mygallery2);


        // Location autocomplete stuff
        mGoogleApiClient = new GoogleApiClient.Builder(RequesterEditTaskActivity.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        edit_destination.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapterController(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        edit_destination.setAdapter(mPlaceArrayAdapter);

        //time sleep

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //get newest data from database.
        if(merlinsBeard.isConnected()) {
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
            for(Task task:start_list){
                FC.saveToFile(task,"sent",getApplication());
            }
        }

        start_list = FC.loadSentTasksFromFile(context);




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

        temp_status=target_task.getTaskStatus();
        //settle save button click

        if (target_task.getTaskPhoto() != null){
            task_photos = target_task.getTaskPhoto();
            attachTaskPhotos();
        }


        Button saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first check empty, name,destination and idealprice cannot leave empty.
                FileSystemController FC = new FileSystemController();
                if(check_status(temp_status)){
                if(check_detaillength(edit_detail.getText().toString())){
                if (check_titlelength(edit_name.getText().toString())){
                if (check_empty(edit_name.getText().toString(),edit_destination.getText().toString(),
                        edit_idealprice.getText().toString())){


                    //interface jump
                    //Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskRequestActivity.class);
                    Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterAllListActivity.class);



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
                    if (setImage == true){
                        target_task.setTaskPhoto(task_photos);
                        /*Photo new_photo = new Photo();

                        BitmapDrawable bit_map_drawable = (BitmapDrawable) post_photo.getDrawable();

                        Bitmap bitmap_photo = bit_map_drawable.getBitmap();

                        new_photo.addPhoto(getStringFromBitmap(bitmap_photo));

                        new_task.setTaskPhoto(new_photo);*/
                    }
                    //upload to database
                    if(merlinsBeard.isConnected()) {
                        OfflineController offlineController = new OfflineController();
                        offlineController.tryToExecuteOfflineTasks(getApplication());
                        TaskController.requesterUpdateTask update = new TaskController.requesterUpdateTask();
                        update.execute(target_task);

                        TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
                        search.execute(userId);
                        try {
                            task_list = search.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        for(Task task:task_list){
                            FC.saveToFile(task,"sent",getApplication());
                        }

                    }
                    //offline
                    else{
                        FileSystemController fileSystemController = new FileSystemController();
                        fileSystemController.saveToFile(target_task,"offlineEdit",getApplication());
                        Log.i("offlineEdit","test");
                    }

                    info2.putExtra("userId",userId);
                    info2.putExtra("info",view_index);

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
                }}else {
                    Toast toast = Toast.makeText(context, "Only requested status allow to edit", Toast.LENGTH_LONG);
                    toast.show();
                }




            }
        });



        //cameraButton click
        FloatingActionButton cameraButton = (FloatingActionButton) findViewById(R.id.floating_editcamera);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info2 = new Intent(RequesterEditTaskActivity.this, CameraActivity.class);
                startActivity(info2);

            }
        });

        //photoButton click
        FloatingActionButton photoButton = (FloatingActionButton) findViewById(R.id.floating_editphoto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Requester edit task activity","take photo clicked");
                // source: https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);


            }
        });

        //settle back button
        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //interface jump
                Intent info2 = new Intent(RequesterEditTaskActivity.this, RequesterViewTaskRequestActivity.class);
                info2.putExtra("userId",userId);
                info2.putExtra("info",view_index);
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
            if (task_photos == null){
                task_photos = new Photo();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Log.d("Old image size", String.valueOf(bitmap.getByteCount()));
            byte[] compressedImage = stream.toByteArray();
            Bitmap compressedbBitmap = BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.length);
            // If image is massive, compress it as much as possible
            if (stream.toByteArray().length >= 15216000){
                ByteArrayOutputStream new_stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, new_stream);
                byte[] new_imageInByte = new_stream.toByteArray();

                Bitmap new_bitmap = BitmapFactory.decodeByteArray(new_imageInByte, 0, new_imageInByte.length);
                myGallery.addView(insertPhoto(new_bitmap));


                Log.d("New image size (massive image)", String.valueOf(new_stream.toByteArray().length));

                task_photos.addPhoto(getStringFromBitmap(new_bitmap));

            }
            // Else if image is big, compress it a little
            else if (stream.toByteArray().length >= 65536){
                ByteArrayOutputStream new_stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 8, new_stream);
                byte[] new_imageInByte = new_stream.toByteArray();

                Bitmap new_bitmap = BitmapFactory.decodeByteArray(new_imageInByte, 0, new_imageInByte.length);
                myGallery.addView(insertPhoto(new_bitmap));


                Log.d("New image size", String.valueOf(new_stream.toByteArray().length));

                task_photos.addPhoto(getStringFromBitmap(new_bitmap));

            }
            // Otherwise don't compress it
            else{
                myGallery.addView(insertPhoto(bitmap));
                task_photos.addPhoto(getStringFromBitmap(bitmap));
            }
            setImage=true;
        }
    }

    private String getStringFromBitmap(Bitmap bitmapPicture) {
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encodedImage;
    }


    @Override
    protected void onStart(){
        super.onStart();
        if(timer!=null) {

            timer.cancel();
        }
        else{
            timer = new Timer(true);
            myTask = new MyTask();

            timer.schedule(myTask,0,2000);
        }
        BidController bidController = new BidController();
        //check counter change
        BidCounter bidCounter = bidController.searchBidCounterOfThisRequester(userId);
        if(bidCounter==null){
            Log.i("Bid counter search error",".");
        }
        else{
            if(bidCounter.getCounter()!= bidCounter.getPreviousCounter()){
                Log.i("New Bid","New Bid");
                Log.i("bidCount",Integer.toString(bidCounter.getCounter()));
                openRequestInfoDialog();
                //update previousCounter
                bidCounter.setPreviousCounter(bidCounter.getCounter());
                BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
                updateBidCounterOfThisRequester.execute(bidCounter);
            }



        }
        //fetch new list
        task_list = new ArrayList<>();
        FileSystemController FC = new FileSystemController();

        if(merlinsBeard.isConnected()) {



            TaskController.searchAllTasksOfThisRequester search = new TaskController.searchAllTasksOfThisRequester();
            search.execute(userId);
            try {
                task_list = search.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            for(Task task:task_list){
                FC.saveToFile(task,"sent",getApplication());
            }
        }

        task_list=FC.loadSentTasksFromFile(getApplication());



    }

    /**
     * method check empty to make sure below parameters are not empty
     * @param name
     * @param detail
     * @param destination
     * @param ideal_price
     * @return true or false
     */
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

    private View insertPhoto(Bitmap bm){

        LinearLayout layout = new LinearLayout(context);
        double iv_scale=(double)bm.getWidth()/bm.getHeight();

        layout.setLayoutParams(new ViewGroup.LayoutParams((int)(iv_scale*700), 700));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);
        Log.d("bm width", String.valueOf((int)((double)bm.getHeight()/bm.getWidth())*650));
        Log.d("scaling", String.valueOf(iv_scale));

        imageView.setLayoutParams(new ViewGroup.LayoutParams((int)(iv_scale*650), 650));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);

        layout.addView(imageView);
        return layout;
    }

    private void attachTaskPhotos(){
        for (int i=0;i<task_photos.getPhotos().size();i++){
            myGallery.addView(insertPhoto(task_photos.getBitmapImage(i)));
        }
    }

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
    private boolean check_status(String status)
    {
        if(status.equals("request" )){

            return true;
        }
        return false;
    }

    private void openRequestInfoDialog() {
        // get request info, and show it on the dialog


        AlertDialog.Builder builder = new AlertDialog.Builder(RequesterEditTaskActivity.this);
        builder.setTitle("New Bid")
                .setMessage("You got a new bid!");
        // Create & Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if(msg.arg1==1)
            {
                //Print Toast or open dialog
                openRequestInfoDialog();
                msg.arg1 = 0;

            }
            return false;
        }
    });
    @Override
    protected void onStop(){
        super.onStop();
        if(timer!=null){
            timer.cancel();
            timer = null;
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(timer!=null) {
            timer.cancel();
            timer = null;
        }
    }

}