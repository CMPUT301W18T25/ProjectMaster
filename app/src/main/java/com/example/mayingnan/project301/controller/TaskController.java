package com.example.mayingnan.project301.controller;

import android.os.AsyncTask;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.OnAsyncTaskCompleted;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.User;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mayingnan.project301.OnAsyncTaskCompleted;
import com.example.mayingnan.project301.UserUtil;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.example.mayingnan.project301.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import io.searchbox.core.Delete;
import io.searchbox.core.DeleteByQuery;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

import static com.example.mayingnan.project301.controller.UserListController.verifySettings;

/**
 * Created by Xingyuan Yang on 2018-02-25.
 */

public class TaskController {

    private Task current_task;
    private static JestDroidClient client;

    public static class addTask extends AsyncTask<Task, Void, Void> {
        public OnAsyncTaskCompleted listener;
        @Override

        protected Void doInBackground(Task... a_task) {
            verifySettings();

            for (Task task : a_task) {
                Index index = new Index.Builder(task).index("cmput301w18t25").type("task").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                    {
                        task.setId(result.getId());
                        Log.i("Success","Elasticsearch ");

                    }
                    else
                    {
                        Log.i("Error","Elasticsearch was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                }

            }
            return null;
        }

    }

    public static class getTaskById extends AsyncTask<String, Void, Task> {
        @Override
        protected Task doInBackground(String... search_parameters) {
            verifySettings();

            Task task = new Task();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"taskID\":\""+search_parameters[0]+"\"}\n"+
                    "}\n"+"}";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Task store_task
                            = result.getSourceAsObject(Task.class);
                    task = store_task;
                } else {
                    Log.i("Error", "The task search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return task;
        }
    }

    public static class deleteTask extends AsyncTask<String, Void, Void>{
        protected Void doInBackground(String... search_parameters) { return null;}
    }
    public void requesterUpdateTask(Task task){}
    public ArrayList<Task>  searchTaskByKeyword(String keyword){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }
    public void providerSetBid(Task task, Bid bid){}
    public void providerUpdateBid(Task task,Bid bid){}
    public void providerCancelBid(Task task,Bid bid){}

    public boolean testTrue(String name){
        return true;
    } //created by wdong2 for testing

    public boolean testFalse(String name){
        return false;
    } //created by wdong2 for testing

    public ArrayList<Task> searchBiddenTasksOfThisProvider(String userName){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAssignTasksOfThisProvider(String userName ){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAllTasksOfThisRequester(String userName){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAllRequestingTasks(){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }
    public ArrayList<Task> searchTaskByTaskName(String taskname){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }


}
