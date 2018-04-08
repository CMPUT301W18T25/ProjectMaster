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
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import project301.BidCounter;
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
public class RequesterPostTaskActivity extends AppCompatActivity implements
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
    private Timer timer;
    public LinearLayout myGallery;
    private Photo photos;

    MyTask myTask = new MyTask();
    private class MyTask extends TimerTask {
        public void run() {
            //Your code...
            //Log.i("Timer9","run");
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
    }


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

        merlinsBeard = MerlinsBeard.from(context);

        //find view by id.
        post_name = (EditText) findViewById(R.id.c_task_name);
        post_detail = (EditText) findViewById(R.id.c_task_detail);

        post_destination = (AutoCompleteTextView) findViewById(R.id.c_task_location);
        post_destination.setThreshold(3);

        post_ideal_price = (EditText) findViewById(R.id.c_task_idealprice);
        //post_photo = (ImageView) findViewById(R.id.c_task_photo);
        setImage=false;
        submitButton=(Button)findViewById(R.id.submit_button);
        cancelButton=(Button)findViewById(R.id.cancel_button);

        myGallery = (LinearLayout)findViewById(R.id.mygallery);
        photos=new Photo();

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
                if (check_empty(post_name.getText().toString())){


                    //interface jump
                    Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterAllListActivity.class);

                    //set data
                    Task new_task = new Task();
                    new_task.setTaskStatus("request");
                    new_task.setTaskName(post_name.getText().toString());
                    if(!check_empty(post_detail.getText().toString())){
                        new_task.setTaskDetails(" ");
                    }
                    else {
                        new_task.setTaskDetails(post_detail.getText().toString());
                    }
                    if(!check_empty(post_destination.getText().toString())){
                        new_task.setTaskAddress(" ");
                    }
                    else {
                        new_task.setTaskAddress(post_destination.getText().toString());
                    }
                    if(!check_empty(post_ideal_price.getText().toString())){
                        new_task.setTaskIdealPrice(null);
                    }
                    else {
                        new_task.setTaskIdealPrice(Double.parseDouble(post_ideal_price.getText().toString()));
                    }

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
                    if (setImage == true){
                        new_task.setTaskPhoto(photos);

                    }

                    //upload new task data to database
                    if(merlinsBeard.isConnected()) {

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

                //toast to make attention to remind
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

                startActivityForResult(info2, 5);


                Log.d("POINT","back form camera");
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

    private void cameraActivity(){

    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("RequesterPostTaskActivity","onPause");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.e("RequesterPostTaskActivity","onRestart");

    }


    /**
     * method to attach photo
     */
    private void attachCameraPhoto(){
        Log.d("RequesterPostTaskActivity","Sleeping");

        File file = new File("/storage/emulated/0/DCIM/Camera", "pic.jpg");
        int counter=1;
        while (file.exists()) {
            //file.delete();
            Log.d("RequesterPostTaskActivity","/storage/emulated/0/DCIM/Camera/pic" + String.format("%02d", counter-1) + ".jpg");
            file = new File("/storage/emulated/0/DCIM/Camera", "pic" + String.format("%02d", counter) + ".jpg");
            counter++;
        }
        String filePath;
        if (counter == 2){
            filePath = "/storage/emulated/0/DCIM/Camera/pic.jpg";
        }
        else{
            filePath = "/storage/emulated/0/DCIM/Camera/pic" + String.format("%02d", counter-2) + ".jpg";

        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Log.d("Photo file",filePath);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] compressedImage = stream.toByteArray();
        Bitmap compressedbBitmap = BitmapFactory.decodeByteArray(compressedImage, 0, compressedImage.length);
        myGallery.addView(insertPhoto(compressedbBitmap));
        photos.addPhoto(getStringFromBitmap(compressedbBitmap));
        setImage=true;
    }

    /**
     * the method on activityresult is to support the photo gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("RequesterPostTask", "onActivityResult");
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
                Toast toast = Toast.makeText(context,"Compressed image size (massive)",Toast.LENGTH_LONG);
                toast.show();
                photos.addPhoto(getStringFromBitmap(new_bitmap));

            }
            // Else if image is big, compress it a little
            else if (stream.toByteArray().length >= 65536){
                ByteArrayOutputStream new_stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 8, new_stream);
                byte[] new_imageInByte = new_stream.toByteArray();

                Bitmap new_bitmap = BitmapFactory.decodeByteArray(new_imageInByte, 0, new_imageInByte.length);
                myGallery.addView(insertPhoto(new_bitmap));


                Log.d("New image size", String.valueOf(new_stream.toByteArray().length));
                Toast toast = Toast.makeText(context,"Compressed image size",Toast.LENGTH_LONG);
                toast.show();
                photos.addPhoto(getStringFromBitmap(new_bitmap));

            }
            // Otherwise don't compress it
            else{
                myGallery.addView(insertPhoto(bitmap));
                photos.addPhoto(getStringFromBitmap(bitmap));
            }
            setImage=true;
        }
        else if(requestCode==5) {
            Log.d("RequesterPostActivity","RETURNED FROM CAMERA");
            attachCameraPhoto();
        }
    }


    // source: modified from https://stackoverflow.com/questions/17489390/image-gallery-with-a-horizontal-scrollview

    /**
     * method to insert photo
     * @param bm
     * @return
     */
    public View insertPhoto(Bitmap bm){

        LinearLayout layout = new LinearLayout(getApplicationContext());
        double iv_scale=(double)bm.getWidth()/bm.getHeight();

        layout.setLayoutParams(new LayoutParams((int)(iv_scale*600), 600));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(getApplicationContext());
        Log.d("bm width", String.valueOf((int)((double)bm.getHeight()/bm.getWidth())*550));
        Log.d("scaling", String.valueOf(iv_scale));

        imageView.setLayoutParams(new LayoutParams((int)(iv_scale*550), 550));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);

        layout.addView(imageView);

        return layout;
    }

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
    }

    /**
     * method to check empty
     * @param name
     * @return
     */
    private boolean check_empty(String name)
    {
        if(name.length()==0){
            return false;
        }
        return true;
    }

    /**
     * method to check title length
     * @param name
     * @return
     */

    private boolean check_titlelength(String name)
    {
        if(name.length()>=31 ){
            return false;
        }
        return true;
    }

    /**
     * method to check detail length
     * @param detail
     * @return
     */
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

    // Converts bitmap to string 64 format
    // source: https://stackoverflow.com/questions/30818538/converting-json-object-with-bitmaps?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
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