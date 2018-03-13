package com.example.mayingnan.project301.requesterActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mayingnan.project301.R;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;



/**
 * Created by User on 2018/2/25.
 */




public class RequesterPostTaskActivity extends AppCompatActivity {
    private Context context;

    private EditText post_name;
    private EditText post_detail;
    private EditText post_destination;
    private EditText post_ideal_price;
    private ImageView post_photo;
    private DatePicker post_date;
    private TimePicker post_time;

    private Button submitButton;
    private Button cancelButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requester_post_task);
        context=getApplicationContext();

        /**
         * find view by id.
         */
        post_name = (EditText) findViewById(R.id.c_task_name);
        post_detail = (EditText) findViewById(R.id.c_task_detail);
        post_destination = (EditText) findViewById(R.id.c_task_location);
        post_ideal_price = (EditText) findViewById(R.id.c_task_idealprice);
        post_photo = (ImageView) findViewById(R.id.c_task_photo);
        post_date=(DatePicker)findViewById(R.id.post_datePicker);
        post_time=(TimePicker)findViewById(R.id.post_timePicker);
        submitButton=(Button)findViewById(R.id.submit_button);
        cancelButton=(Button)findViewById(R.id.cancel_button);




        /**
         * submitButton click
         */
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_empty(post_name.getText().toString(),post_detail.getText().toString(),post_destination.getText().toString(),
                        post_ideal_price.getText().toString(),getDateTimeFromPickers(post_date,post_time).toString())){

                    //interface jump
                    Intent info2 = new Intent(RequesterPostTaskActivity.this, RequesterEditListActivity.class);
                    startActivity(info2);

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
     * @param datetime
     * @return
     */
    private boolean check_empty(String name, String detail, String destination, String ideal_price, String datetime){
        if(name.length()==0 || detail.length()==0 || destination.length()==0|| ideal_price.length()==0|| datetime.length()==0 ){
            return false;
        }
        return true;
    }


    /**
     * method to get the Date and Time from a DatePicker and TimePicker and return a JodaTime DateTime from them, in current timezone.
     * @param dp
     * @param tp
     * @return
     */
    private DateTime getDateTimeFromPickers( DatePicker dp, TimePicker tp) {


        String year    = Integer.toString(dp.getYear()) ;
        String month   = StringUtils.leftPad( Integer.toString(dp.getMonth() + 1), 2, "0" );
        String day     = StringUtils.leftPad( Integer.toString(dp.getDayOfMonth()), 2, "0" );
        String hour    = StringUtils.leftPad( Integer.toString(tp.getCurrentHour()), 2, "0" );
        String minutes = StringUtils.leftPad( Integer.toString(tp.getCurrentMinute()), 2, "0" );

        String dateTime = year + "/" + month + "/" + day + " " + hour + ":" + minutes;

        return DateTime.parse(dateTime);

    }

}




