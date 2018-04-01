package project301.providerActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import project301.R;
import project301.Task;

import java.util.ArrayList;

/**
 * this class is an adapter for the task arrayList to show on the UI
 * @classname : ProviderAdapter
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderAdapter extends ArrayAdapter<Task> {
    public ProviderAdapter(Context context, ArrayList<Task> users) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_search, parent, false);
        }

        // Lookup view for data population
        TextView task_requester = convertView.findViewById(R.id.adapter_requester);
        TextView task_name = convertView.findViewById(R.id.adapter_name);
        TextView task_lowestBid = convertView.findViewById(R.id.adapter_lowestBid);
        TextView task_status = convertView.findViewById(R.id.adapter_status);
        TextView task_idealPrice = convertView.findViewById(R.id.adapter_idealprice);

        // Return the completed view to render on screen
        //noinspection ConstantConditions

        //get taskRequester
        String taskRequester = task.getTaskRequester().toString();

        //get taskName
        String taskName = task.getTaskName().toString();

        //get taskLowestBid
        String taskLowestBid;
        if (task.findLowestbid()==null){
            taskLowestBid = "";
        }else{
            taskLowestBid = Double.toString(task.findLowestbid());
        }

        //get task status
        String taskStatus = task.getTaskStatus().toString();

        //get task ideal price
        String taskIdealPrice = task.getTaskIdealPrice().toString();

        //set task info
        task_requester.setText(taskRequester);
        task_name.setText(taskName);
        task_lowestBid.setText(taskLowestBid);
        task_status.setText(taskStatus);
        task_idealPrice.setText(taskIdealPrice);

        //Log.i("a",task.getTaskAddress().toString());
        return convertView;
    }
}
