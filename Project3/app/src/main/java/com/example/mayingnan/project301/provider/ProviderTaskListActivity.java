package com.example.mayingnan.project301.provider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.Task;

import java.util.ArrayList;

public class ProviderTaskListActivity extends AppCompatActivity {

    private Button searchTask;
    private Button viewOnMap;
    private Button BidHistoy;
    private ListView taskListView;
    private TextView taskLabel;
    private ArrayList<Task> taskList;
    private ArrayAdapter<Task> taskAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_provider);
    }
    public void onSearch(){}

    public void showMap(){}

    public void showHistory(){}

}
