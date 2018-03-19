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
 * @classname : ProviderAdapter
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
public class ProviderAdapter extends ArrayAdapter<Task> {
    public ProviderAdapter(Context context, ArrayList<Task> users) {
        super(context, 0, users);
    }

    @SuppressWarnings("ConstantConditions")
    @Override

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

        // Return the completed view to render on screen
        //noinspection ConstantConditions
        //get taskName
        String taskName = task.getTaskName().toString();
        //get taskAddress
        String taskAddress;
        if (task.getTaskAddress()==null){
            taskAddress = "";
        }else{
            taskAddress = task.getTaskAddress().toString();
        }
        //get taskIdealPrice
        String taskIdealPrice;
        if (task.getTaskIdealPrice()==null){
            taskIdealPrice = "";
        }else{
            taskIdealPrice = Double.toString(task.getTaskIdealPrice());
        }

        //set task info
        task_name.setText(taskName);
        task_destination.setText(taskAddress);
        task_idealprice.setText(taskIdealPrice);

        //Log.i("a",task.getTaskAddress().toString());
        return convertView;
    }
}
