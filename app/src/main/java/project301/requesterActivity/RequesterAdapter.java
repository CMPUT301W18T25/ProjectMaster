package project301.requesterActivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import project301.R;
import project301.Task;

import java.util.ArrayList;

/**
 * Detail :RequesterAdapter is to adapt task and do task arrangement in the arraylist.
 * @Date :   18/03/2018
 * @author : Yingnan Ma
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 * @classname : RequesterActivity
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class RequesterAdapter extends ArrayAdapter<Task> {
    public RequesterAdapter(Context context, ArrayList<Task> users) {
        super(context, 0, users);
    }

    @SuppressWarnings("ConstantConditions")
    @Override

    /**
     get the view of the list information;
     load some info into each task in the task list
     */
    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position
        Task task = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_list, parent, false);
        }

        // Lookup view for data population
        TextView task_name = convertView.findViewById(R.id.adapter_name);
        TextView task_destination = convertView.findViewById(R.id.adapter_destination);
        TextView task_idealprice = convertView.findViewById(R.id.adapter_idealprice);
        TextView task_status = convertView.findViewById(R.id.adapter_status);

        // Return the completed view to render on screen
        //noinspection ConstantConditions
        task_name.setText(task.getTaskName().toString());
        task_destination.setText(task.getTaskAddress());
        if(task.getTaskIdealPrice()!= null){
            task_idealprice.setText(Double.toString(task.getTaskIdealPrice()));
        }

        task_status.setText(task.getTaskStatus().toString());
        Log.i("a","a");
        return convertView;
    }
}
