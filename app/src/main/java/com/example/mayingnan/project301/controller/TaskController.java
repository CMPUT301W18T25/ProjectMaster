package com.example.mayingnan.project301.controller;

import android.os.AsyncTask;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.OnAsyncTaskCompleted;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.TaskUtil;
import com.example.mayingnan.project301.User;
import com.example.mayingnan.project301.UserUtil;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.Log;

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

            Index index = new Index.Builder(a_task[0]).index("cmput301w18t25").type("task").build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                Log.i("TEST client result", result.getId());
                if(result.isSucceeded())
                {
                    a_task[0].setId(result.getId());
                    Log.i("Success","Elasticsearch ");
                    Index deep_index = new Index.Builder(a_task[0]).index("cmput301w18t25").type("task").build();
                    try {
                        result = client.execute(deep_index);
                        if (result.isSucceeded()){
                            Log.i("Success",a_task[0].getId());
                        }
                    }
                    catch (Exception e){
                    }

                }
                else
                {
                    Log.i("Error","Elasticsearch was not able to add the task");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the tasks");
            }


            return null;
        }

    }

    public static class getTaskById extends AsyncTask<String, Void, Task> {

        private String id;

        @Override
        protected Task doInBackground(String... arg_id) {
            verifySettings();

            Task task = new Task();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"_id\":\""+arg_id[0]+"\"}\n"+

                    "}\n"+"}";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    task = result.getSourceAsObject(Task.class);
                    Log.i("Success",task.getId());
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

    public static class deleteTaskById extends AsyncTask<String, Void, Void>{

        private String id;

        public deleteTaskById(String arg_id){
            this.id = arg_id;
        }

        @Override
        protected Void doInBackground(String... idToDelete) {
            verifySettings();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"_id\":\""+idToDelete[0]+"\"}\n"+

                    "}\n"+"}";

            Log.i("Query", "The query was " + query);

            Delete delete = new Delete.Builder(idToDelete[0]).index("cmput301w18t25").type("task").build();
            try {
                DocumentResult result = client.execute(delete);
                if (result.isSucceeded()) {
                    Log.i("Success", "Successful delete");
                } else {
                    Log.i("Error", "Elastic search was not able to deletes.");
                }
            } catch (Exception e) {
                Log.i("Error", "We failed to add a request to elastic search!");
                e.printStackTrace();
            }

            return null;
        }
    }

    public static class requesterUpdateTask extends AsyncTask<Task, Void, Void>{

        @Override
        protected Void doInBackground(Task... single_task) {

            verifySettings();

            String query = TaskUtil.serializer(single_task[0]);

            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("task").id(single_task[0].getId()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.i("Debug", "Successful update user profile");
                } else {
                    Log.i("Error", "We failed to update user profile to elastic search!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Error", "We failed to connect Elasticsearch server");
            }
            return null;
        }
    }

    public static class providerSetBid extends AsyncTask<Void, Void, Void>{
        //new MyTask(int foo, long bar, double arple).execute();

        Task current_task;
        Bid current_bid;

        public providerSetBid(Task current_task, Bid current_bid){
            this.current_task = current_task;
            this.current_bid = current_bid;
            this.current_task.addBid(this.current_bid);
        }

        @Override
        protected Void doInBackground(Void... nul) {

            verifySettings();

            String query = TaskUtil.serializer(this.current_task);

            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("task").id(this.current_task.getId()).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.i("Success", "Successful update the bid");
                } else {
                    Log.i("Error", "We failed to update new bid to elastic search!");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Error", "We failed to connect Elasticsearch server");
            }
            return null;
        }
    }

    public static class providerCancelBid extends AsyncTask<Void, Void, Boolean>{

        Task current_task;
        String providerName;
        Boolean rt_val;

        public providerCancelBid(Task current_task, String providerName){
            this.current_task = current_task;
            this.providerName = providerName;
            this.rt_val = this.current_task.cancelBid(this.providerName);
        }

        @Override
        protected Boolean doInBackground(Void... nul) {
            if (this.rt_val){
                verifySettings();

                String query = TaskUtil.serializer(this.current_task);

                Index index = new Index.Builder(query)
                        .index("cmput301w18t25").type("task").id(this.current_task.getId()).build();
                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        Log.i("Success", "Successful cancel provider's bid");
                    } else {
                        Log.i("Error", "We failed to cancel provider's bid to elastic search!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("Error", "We failed to connect Elasticsearch server");
                }
                return true;
            }
            else {
                return false;
            }

        }
    }

    public static class searchTaskByKeyword extends AsyncTask<String, Void, ArrayList<Task>>{
        protected ArrayList<Task> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"userName\":\""+search_parameters[0]+"\"}\n"+
                    "}\n"+"}";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("user")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundUsers
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundUsers);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return result_tasks;
        }
    }

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

    public boolean testTrue(String name){
        return true;
    } //created by wdong2 for testing

    public boolean testFalse(String name){
        return false;
    } //created by wdong2 for testing

    // no need to use it, providerSetBis is good enough
    public void providerUpdateBid(Task task,Bid bid){}

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
