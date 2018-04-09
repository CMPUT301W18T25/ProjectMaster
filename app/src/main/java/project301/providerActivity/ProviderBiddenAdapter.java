package project301.providerActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import project301.Bid;
import project301.R;
import project301.Task;
import java.util.ArrayList;

/**
 * This class is an adapter for the bidden task arrayList to show on the UI
 * @classname : ProviderBiddenAdapter
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */



@SuppressWarnings({"ALL", "ConstantConditions"})
public class ProviderBiddenAdapter extends ArrayAdapter<Task> {
    private String userId;

    public ProviderBiddenAdapter(Context context, ArrayList<Task> users) {
        super(context, 0, users);
    }


    public void setId(String id){
        userId = id;
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_bidden, parent, false);
        }

        // Lookup view for data population
        TextView task_name = convertView.findViewById(R.id.adapter_name_bid);
        TextView task_requester = convertView.findViewById(R.id.adapter_requester_bid);
        TextView task_idealprice = convertView.findViewById(R.id.adapter_idealprice_bid);
        TextView task_bid = convertView.findViewById(R.id.adapter_myBid_bid);
        TextView task_status = convertView.findViewById(R.id.adapter_status_bid);

        // Return the completed view to render on screen
        //noinspection ConstantConditions
        //get taskName
        String taskName = task.getTaskName().toString();

        //get task requester
        String taskRequester;
        if (task.getTaskRequester()==null){
            taskRequester = "";
        }else{
            taskRequester = task.getTaskAddress().toString();
        }

        //get taskIdealPrice
        String taskLowestPrice;
        if (task.findLowestbid()==null){
            taskLowestPrice = "";
        }else{
            taskLowestPrice = Double.toString(task.findLowestbid());
        }

        //get taskBid
        String taskBid = null;
        ArrayList<Bid> bidList = task.getTaskBidList();
        for (int counter = 0; counter < bidList.size(); counter++) {
            Bid bid = bidList.get(counter);
            if (bid.getProviderId().equals(userId)){
                taskBid = bid.getBidAmount().toString();
                break;
            }
        }
        if (taskBid == null){
            taskBid = "Cannot find";
        }

        //get taskStatus
        String taskStatus;
        if (task.getTaskStatus()==null){
            taskStatus = "";
        }else{
            taskStatus = task.getTaskStatus();
        }

        //set task info
        task_name.setText(taskName);
        task_requester.setText(taskRequester);
        task_idealprice.setText(taskLowestPrice);
        task_bid.setText(taskBid);
        task_status.setText(taskStatus);

        //Log.i("a",task.getTaskAddress().toString());
        return convertView;
    }
}