package project301.controller;

import project301.Bid;
import project301.OnAsyncTaskCompleted;
import project301.Task;
import project301.User;
import project301.utilities.TaskUtil;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import android.os.AsyncTask;
import android.util.Log;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Activities can communicate with task model, bid model through this class.
 * The class contains methods to renew data on the elasticsearch database
 * @classname : TaskController
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class TaskController {

    private Task current_task;
    private static JestDroidClient client;

    /**
     * A static class to delete all tasks in ES database
     */
    public static class deleteAllTasks extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();
            ArrayList<Task> tasks = new ArrayList<Task>();

            String query = "{ \"size\": 500 }" ;
            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundTasks
                            = result.getSourceAsObjectList(Task.class);
                    tasks.addAll(foundTasks);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            for (Task task:tasks){
                Delete delete = new Delete.Builder(task.getId()).index("cmput301w18t25").type("task").build();

                try {
                    client.execute(delete);

                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;

        }
    }
    /**
     * A static class to add a task to ES database
     */
    public static class addTask extends AsyncTask<Task, Void, String>{
        public OnAsyncTaskCompleted listener;
        @Override

        protected String doInBackground(Task... a_task) {
            verifySettings();
            String taskId = "taskId";

            a_task[0].setTaskStatus("request");
            Index index = new Index.Builder(a_task[0]).index("cmput301w18t25").type("task").build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                //Log.i("TEST client result", result.getId());
                if(result.isSucceeded())
                {
                    a_task[0].setId(result.getId());
                    taskId = result.getId();

                    String query = TaskUtil.serializer(a_task[0]);

                    Index index2 = new Index.Builder(query)
                            .index("cmput301w18t25").type("task").id(a_task[0].getId()).build();
                    try {
                        DocumentResult result2 = client.execute(index2);
                        if (result2.isSucceeded()) {
                            Log.i("Debug", "Successful update user profile");
                        } else {
                            Log.i("Error", "We failed to update user profile to elastic search!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.i("Error", "We failed to connect Elasticsearch server");
                    }
                    Log.i("Success","Elasticsearch ");

                }
                else
                {
                    Log.i("Error","Elasticsearch was not able to add the task");
                }
            }
            catch (Exception e) {
                Log.i("Error", "The application failed to build and send the tasks");
                e.printStackTrace();
                return taskId;
            }


            return taskId;
        }

    }
    /**
     * A static class to get a task by its id from ES database
     */
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
    /**
     * A static class to delete a task by its id in ES database
     */
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
    /**
     * A static class to update tasks in ES database
     */

    public static class requesterUpdateTask extends AsyncTask<Task, Void, Boolean>{

        @Override
        protected Boolean doInBackground(Task... single_task) {

            verifySettings();
            Boolean success = false;
            String query = TaskUtil.serializer(single_task[0]);

            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("task").id(single_task[0].getId()).build();
            try {
                Log.i("try to execute","update");
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    success = true;
                    Log.i("Debug", "Successful update user profile");
                } else {
                    Log.i("Error", "We failed to update user profile to elastic search!");
                }
            } catch (IOException e) {
                //e.printStackTrace();

                Log.i("Error", "We failed to connect Elasticsearch server?");

                return success;
            }
            return success;
        }
        @Override
        protected void onPostExecute(Boolean success){
            Log.i("Finish","execution");
        }
    }
    /**
     * A static class to set bid in ES database
     */
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


            this.current_task.setTaskStatus("bidden");
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
            //UserController getUser = new UserController();
            //User new_user = getUser.getAUserById(current_bid.getProviderId());

            //new_user.addProviderBiddenTask(current_task.getId());

            // update user

            //UserController uc= new UserController();
            //uc.updateUser(new_user);


            return null;
        }
    }
    /**
     * A static class to cancel a bid in ES database
     */
    public static class providerCancelBid extends AsyncTask<Void, Void, Boolean>{

        Task current_task;
        String providerId;
        Boolean rt_val;

        public providerCancelBid(Task current_task, String providerId){
            this.current_task = current_task;
            this.providerId = providerId;
            this.rt_val = this.current_task.cancelBid(this.providerId);
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

    //TODO test it
    /**
     * A static class to get provider bidden tasks in ES database
     */
    public static class providerGetBiddenTasks extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... providerID) {
            ArrayList<String> rtTasks = new ArrayList<>();
            User foundUser;

            verifySettings();

            String query = "{ \n"+
                    "\"size\" : 300,\n"+

                    "\"query\":{\n"+
                    "\"term\":{\"userId\":\""+providerID[0]+"\"}\n"+
                    "}\n"+"}";

            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("userst").id(providerID[0]).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    foundUser = result.getSourceAsObject(User.class);
                    rtTasks = foundUser.getProviderBiddenTask();
                    Log.i("Success", "Successful cancel provider's bid");
                } else {
                    rtTasks = null;
                    Log.i("Error", "We failed to cancel provider's bid to elastic search!");
                }
            } catch (IOException e) {
                rtTasks = null;
                e.printStackTrace();
                Log.i("Error", "We failed to connect Elasticsearch server");
            }
            return rtTasks;
        }
    }

    // TODO change provider variable to requester variable
    /**
     * A static class to get requester bidden tasks in ES database
     */
    public static class requesterGetBiddenTasks extends AsyncTask<String, Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... providerID) {
            ArrayList<String> rtTasks = new ArrayList<>();
            User foundUser;

            verifySettings();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"userId\":\""+providerID[0]+"\"}\n"+
                    "}\n"+"}";

            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("userst").id(providerID[0]).build();
            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    foundUser = result.getSourceAsObject(User.class);
                    rtTasks = foundUser.getProviderBiddenTask();
                    Log.i("Success", "Successful cancel provider's bid");
                } else {
                    rtTasks = null;
                    Log.i("Error", "We failed to cancel provider's bid to elastic search!");
                }
            } catch (IOException e) {
                rtTasks = null;
                e.printStackTrace();
                Log.i("Error", "We failed to connect Elasticsearch server");
            }
            return rtTasks;
        }
    }
    /**
     * A static class to search bidden tasks in ES database
     */
    public static class searchBiddenTasksOfThisProvider extends AsyncTask<Void, Void, ArrayList<Task>>{
        //ArrayList<Task> taskList = new ArrayList<Task> ();
        String providerId;

        public searchBiddenTasksOfThisProvider(String providerId){
            this.providerId = providerId;
        }

        protected ArrayList<Task> doInBackground(Void... nul) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskStatus\" : " + "\"bidden\"}}" +
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query );
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> rt
                            = result.getSourceAsObjectList(Task.class);
                    for(Task task:rt){

                        ArrayList<Bid> BiddenList = task.getTaskBidList();
                        for(Bid bid:BiddenList){
                            if(bid.getProviderId().equals(providerId)){
                                result_tasks.add(task);


                            }
                        }
                    }

                    Log.i("allbidden","test");


                    Log.i("Success", "Data retrieved from database: " + Integer.toString(rt.size()));
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
    /**
     * A static class to search all tasks of this provider in ES database
     */
    /*

    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAssignTasksOfThisProvider extends AsyncTask<String, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(String... providerId) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 30,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskStatus\" : " + "\"assigned\"}}," + "\n"+
                            "               { \"term\" : {\"taskProvider\" : \"" + providerId[0] + "\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundTasks
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundTasks);
                    Log.i("Success", "Data retrieved from database: ");
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return result_tasks;
        }

    }*/

    public static class searchAssignTasksOfThisProvider extends AsyncTask<Void, Void, ArrayList<Task>>{
        //ArrayList<Task> taskList = new ArrayList<Task> ();
        String providerId;

        public searchAssignTasksOfThisProvider(String providerId){
            this.providerId = providerId;
        }

        protected ArrayList<Task> doInBackground(Void... nul) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskStatus\" : " + "\"assigned\"}}" +
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query );
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> rt
                            = result.getSourceAsObjectList(Task.class);
                    for(Task task:rt){

                        ArrayList<Bid> BiddenList = task.getTaskBidList();
                        for(Bid bid:BiddenList){
                            if(bid.getProviderId().equals(providerId)){
                                result_tasks.add(task);


                            }
                        }
                    }

                    Log.i("allbidden","test");


                    Log.i("Success", "Data retrieved from database: " + Integer.toString(rt.size()));
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
    /**
     * A static class to search all tasks of this requester in ES database
     */

    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAllTasksOfThisRequester extends AsyncTask<String, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(String... requesterId) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskRequester\" : \"" + requesterId[0] + "\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundResults
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundResults);
                    Log.i("Success", "Data retrieved from database: ");
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Task faultTask = new Task();
                faultTask.setId("-1");
                result_tasks.add(faultTask);
            }
            return result_tasks;
        }


    }

    public static class searchAllBiddenTasksOfThisProvider extends AsyncTask<String, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(String... providerId) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+
                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskRequester\" : \"" + providerId[0] + "\"}}," + "\n"+
                            "               { \"term\" : {\"taskStatus\" : \"bidden\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundResults
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundResults);
                    Log.i("Success", "Data retrieved from database: ");
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Task faultTask = new Task();
                faultTask.setId("-1");
                result_tasks.add(faultTask);
            }
            return result_tasks;
        }


    }


    /**
     * A static class to search all tasks of this requester in ES database
     */

    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAllBiddenTasksOfThisRequester extends AsyncTask<String, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(String... requesterId) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskRequester\" : \"" + requesterId[0] + "\"}}" + "\n"+
                            "               { \"term\" : {\"taskStatus\" : \"bidden\"}}" + "\n"+

                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundResults
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundResults);
                    Log.i("Success", "Data retrieved from database: ");
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                Task faultTask = new Task();
                faultTask.setId("-1");
                result_tasks.add(faultTask);
            }
            return result_tasks;
        }


    }
    /**
     * A static class to search all requesting tasks in ES database
     */
    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAllRequestingTasks extends AsyncTask<Void, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(Void... nul) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"taskStatus\" : \"request\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundUsers
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundUsers);
                    Log.i("Success", "Data retrieved from database: ");
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

    /**
     * A static class to search all requesting tasks in ES database
     */
    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAllBiddenTasks extends AsyncTask<Void, Void, ArrayList<Task>>{

        protected ArrayList<Task> doInBackground(Void... nul) {
            verifySettings();

            ArrayList<Task> result_tasks = new ArrayList<Task>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"should\" : [\n"+
                            "               { \"term\" : {\"taskStatus\" : \"bidden\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundUsers
                            = result.getSourceAsObjectList(Task.class);
                    result_tasks.addAll(foundUsers);
                    Log.i("Success", "Data retrieved from database: ");
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

    /**
     * A static class to search all bid of a task
     */
    //TODO do test for this method, which should be extremely similar to bidden tasks
    public static class searchAllBid extends AsyncTask<String, Void, ArrayList<Bid>>{

        protected ArrayList<Bid> doInBackground(String...taskIds) {
            verifySettings();
            ArrayList<Bid> bidList = new ArrayList<Bid>();

            String query =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"_id\" : \"" + taskIds[0] + "\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("task")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<Task> foundTask
                            = result.getSourceAsObjectList(Task.class);
                    bidList.addAll(foundTask.get(0).getTaskBidList());

                    Log.i("Success", "Data retrieved from database: ");
                } else {
                    Log.i("Error", "The search query failed");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return bidList;
        }

    }
    /**
     * A static class to search task by key word in ES database
     */
    public static class searchTaskByKeyword extends AsyncTask<String, Void, ArrayList<Task>>  {
        protected ArrayList<Task> doInBackground(String... keywords) {
            verifySettings();

            String [] search_parameters = keywords[0].split("\\s+");
            String bodyQuery;
            ArrayList<Task> result_tasks = new ArrayList<Task>();
            /*
            String query =
                    "\n{     \n"+
                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"multi_match\" : {\n" +
                            "                   \"query\" : \""+ search_parameters[0] +"\", \n" +
                            "                   \"fields : [ \"taskName\", \"taskDetails\" ] \n " +
                            "                   }  \n" +
                            "               }\n" +
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";
            */
            Log.i("Length", Integer.toString(search_parameters.length));

            bodyQuery ="               { \"multi_match\" : {\"query\" : \"" +search_parameters[0] +"\", \"fields\" : [ \"taskName\"] }}" + "\n";

            for (int i = 1; i < search_parameters.length; i++) {
                bodyQuery = bodyQuery + "               ,{ \"multi_match\" : {\"query\" : \"" + search_parameters[i] + "\", \"fields\" : [ \"taskName\"] }}" + "\n";
            }

            //bodyQuery = bodyQuery + "               ,{ \"multi_match\" : {\"query\" : \"" + search_parameters[1] + "\", \"fields\" : [ \"taskName\", \"taskDetails\"] }}" + "\n";

            String shellQuery =
                    "\n{ \n"+
                            "\"size\" : 300,\n"+
                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"should\" : [\n"+
                            bodyQuery+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            String post_query =
                    "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + shellQuery);
            Search search = new Search.Builder(shellQuery)
                    .addIndex("cmput301w18t25")
                    .addType("task")
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
    public ArrayList<Task> searchByKeyWord(String keyWord,String providerId){
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<Task> keywordTasks = new ArrayList<>();
        TaskController.searchAllBiddenTasks searchAllBiddenTasks = new TaskController.searchAllBiddenTasks();
        searchAllBiddenTasks.execute();
        try {
            tasks.addAll(searchAllBiddenTasks.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        TaskController.searchAllRequestingTasks searchAllRequestingTasks = new TaskController.searchAllRequestingTasks();
        searchAllRequestingTasks.execute();
        try {
            tasks.addAll(searchAllRequestingTasks.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        for(Task task:tasks){
            if(task.getTaskName().toLowerCase().indexOf(keyWord.toLowerCase()) != -1 ||task.getTaskDetails().toLowerCase().indexOf(keyWord.toLowerCase())!=-1) {
                keywordTasks.add(task);

            }
        }
        return keywordTasks;

    }

    public boolean testTrue(String name){
        return true;
    } //created by wdong2 for testing

    public boolean testFalse(String name){
        return false;
    } //created by wdong2 for testing

    // no need to use it, providerSetBids is good enough

    public void providerUpdateBid(Task task,Bid bid){}

    /**
     * verify ES database setting
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://192.30.35.214:8080").discoveryEnabled(true).multiThreaded(true);



            DroidClientConfig config = builder.build();


            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

    /**
     * For development, will delete finally
     */
    public static class yuqi8delete extends AsyncTask<String, Void, Void>{

        private String id;

        public yuqi8delete(String arg_id){
            this.id = arg_id;
        }

        @Override
        protected Void doInBackground(String... idToDelete) {
            verifySettings();

            String query = "{ \n"+
                    "\"size\" : 1000" +

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

}
