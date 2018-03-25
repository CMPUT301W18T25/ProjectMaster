package project301.controller;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import project301.Bid;
import project301.BidCounter;

/**
 * Created by a123456 on 2018-03-22.
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
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return success;

    }
    public int searchBidCounterOfThisRequester(String requesterId){
        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute(requesterId);
        BidCounter bidCounter = null;
        try {
            bidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(bidCounter.equals(null)){
            return -1;
        }
        else{
            return bidCounter.getCounter();
        }
    }
    public Boolean increaseBidCounterOfThisRequester(String requesterId){
        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute(requesterId);
        BidCounter bidCounter = null;
        try {
            bidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(bidCounter.equals(null)){
            return false;
        }
        //increase count by 1
        int newCount = bidCounter.getCounter()+1;
        bidCounter.setCounter(newCount);

        BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
        updateBidCounterOfThisRequester.execute(bidCounter);
        return true;

    }

    public static class updateBidCounterOfThisRequester extends AsyncTask<BidCounter, Void, Void> {

        @Override
        protected Void doInBackground(BidCounter... bidCounters) {
            verifySettings();
            Gson gson = new Gson();
            String query = gson.toJson(bidCounters[0]);
            Index index2 = new Index.Builder(query)
                    .index("cmput301w18t25").type("bidCounter").id(bidCounters[0].getESid()).build();
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

    public static class searchBidCounterOfThisRequester extends AsyncTask<String, Void, BidCounter>{

        @Override
        protected BidCounter doInBackground(String... requesterId) {
            verifySettings();
            String query =
                    "\n{ \n"+
                            "\"size\" : 2,\n"+

                            "   \"query\" : {\n"+
                            "       \"bool\" : {\n"+
                            "           \"must\" : [\n"+
                            "               { \"term\" : {\"requesterId\" : \"" + requesterId[0] + "\"}}" + "\n"+
                            "           ]\n"+
                            "       }\n"+
                            "   }\n"+
                            "}\n";

            Log.i("Query", "The query was " + query );
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("bidCounter")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<BidCounter> rt
                            = result.getSourceAsObjectList(BidCounter.class);
                    return rt.get(0);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return null;
        }
    }
    public static class buildBidCounterOfThisRequester extends AsyncTask<BidCounter, Void, Boolean>{

        protected Boolean doInBackground(BidCounter... bidCounters) {
            verifySettings();
            Boolean success = false;
            Index index = new Index.Builder(bidCounters[0]).index("cmput301w18t25").type("bidCounter").build();

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
                            .index("cmput301w18t25").type("bidCounter").id(bidCounters[0].getESid()).build();
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

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://192.30.35.214:8080").discoveryEnabled(true).multiThreaded(true);



            DroidClientConfig config = builder.build();


            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
