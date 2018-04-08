package project301.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import project301.BidCounter;

/**
 * Detail : Bid controller is used to implement bidding notification feature.
 * Each user has a bid counter which is used to detect new bid.
 * @classname : BidController
 * @Date :   18/03/2018
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */
public class BidController {
    private static JestDroidClient client;
    /**
     * When the user signed up, this function will be called to initialize a bid counter
     * @param requesterId requesterId
     * @return boolean value means success or not
     */
    public boolean buildBidCounterOfThisRequester(String requesterId){
        BidCounter bidCounter = new BidCounter(requesterId,0);
        BidController.buildBidCounterOfThisRequester buildBidCounterOfThisRequester = new BidController.buildBidCounterOfThisRequester();
        buildBidCounterOfThisRequester.execute(bidCounter);
        Boolean success = false;
        try {
            success = buildBidCounterOfThisRequester.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return success;

    }

    /**
     * Search the bidCounter by requesterId
     * @param requesterId requester id
     * @return bidcounter
     */
    public BidCounter searchBidCounterOfThisRequester(String requesterId){
        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute(requesterId);
        @SuppressWarnings("UnusedAssignment") BidCounter bidCounter = new BidCounter();
        try {
            bidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            //noinspection ConstantConditions
            throw null;

        }
        if(bidCounter==null){
            return null;
        }
        else{
            return bidCounter;
        }
    }

    /**
     * Increase BidCounter of this requester
     * @param requesterId requesterId
     * @return success or fail
     */
    public Boolean increaseBidCounterOfThisRequester(String requesterId){
        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute(requesterId);
        BidCounter bidCounter = null;
        try {
            bidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if(bidCounter == null){
            return false;
        }
        //increase count by 1
        int newCount = bidCounter.getCounter()+1;
        bidCounter.setCounter(newCount);

        BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
        updateBidCounterOfThisRequester.execute(bidCounter);
        return true;

    }

    /**
     * Update bidcounter of this requester from ES database
     */
    public static class updateBidCounterOfThisRequester extends AsyncTask<BidCounter, Void, Void> {

        @Override
        protected Void doInBackground(BidCounter... bidCounters) {
            verifySettings();
            Gson gson = new Gson();
            String query = gson.toJson(bidCounters[0]);
            Index index2 = new Index.Builder(query)
                    .index("cmput301w18t25").type("bidcount").id(bidCounters[0].getESid()).build();
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
            return null;
        }
    }

    /**
     * Search bid counter from ES database
     */
    public static class searchBidCounterOfThisRequester extends AsyncTask<String, Void, BidCounter>{

        @Override
        protected BidCounter doInBackground(String... requesterId) {
            verifySettings();
            String query =
                    "\n{ \n"+
                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"requesterId\" : \"" + requesterId[0] + "\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";


            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("bidcount")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {

                    return result.getSourceAsObject(BidCounter.class);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                e.printStackTrace();

                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return null;
        }
    }

    /**
     * Create BidCounter in ES database
     */
    public static class buildBidCounterOfThisRequester extends AsyncTask<BidCounter, Void, Boolean>{

        protected Boolean doInBackground(BidCounter... bidCounters) {
            verifySettings();
            Boolean success = false;
            Index index = new Index.Builder(bidCounters[0]).index("cmput301w18t25").type("bidcount").build();

            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                //Log.i("TEST client result", result.getId());
                if(result.isSucceeded())
                {
                    bidCounters[0].setESid(result.getId());
                    success = true;
                    Gson gson = new Gson();
                    String query = gson.toJson(bidCounters[0]);

                    Index index2 = new Index.Builder(query)
                            .index("cmput301w18t25").type("bidcount").id(bidCounters[0].getESid()).build();
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
            }


            return success;
        }

    }
    /**
     * A static class used to delete all user from the ES database
     */


    public static class deleteAllBidCounters extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();
            ArrayList<BidCounter> bidCounters = new ArrayList<> ();

            String query = "{ \"size\": 100 }" ;
            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("bidcount")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    @SuppressWarnings("deprecation") List<BidCounter> founds
                            = result.getSourceAsObjectList(BidCounter.class);
                    bidCounters.addAll(founds);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            for (BidCounter u: bidCounters){
                Delete delete = new Delete.Builder(u.getESid()).index("cmput301w18t25").type("bidcount").build();

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
     * Setting ES database
     */
    private static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://192.30.35.214:8080").discoveryEnabled(true).multiThreaded(true);



            DroidClientConfig config = builder.build();


            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
