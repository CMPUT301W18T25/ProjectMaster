package project301;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import project301.allUserActivity.LogInActivity;
import project301.controller.BidController;


/**
 * @classname : BidTest
 * @class Detail : This is the test class for bid methods, all backend methods about
 *                 add a new bid, update an existing bid, cancel a bid, assign a bid
 *
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @version 1.3
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * This is the test class for bid methods.
 */


public class BidTest extends ActivityInstrumentationTestCase2 {


    public BidTest() {
        super(LogInActivity.class);
    }

    public void testSetBidAmount(){
        ArrayList<Bid> bidList = new ArrayList<Bid> ();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid((double) 10,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList ().get (0).getProviderId());
        assertEquals(10, biddedTask.getTaskBidList ().get (0).getBidAmount());

        biddedTask.getTaskBidList ().get (0).setBidAmount((double) 1.23);
        assertEquals(1.23, biddedTask.getTaskBidList ().get (0).getBidAmount());

        bidList.add(new Bid((double) 15,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList ().get (1).getProviderId());
        assertEquals(15, biddedTask.getTaskBidList ().get (1).getBidAmount());

        biddedTask.getTaskBidList ().get (1).setBidAmount((double) 9.09);
        assertEquals(9.00, biddedTask.getTaskBidList ().get (1).getBidAmount());


    }
    public void testProviderUpdateBid(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid((double) 10,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList ().get (0).setBidAmount((double) 11.11);
        assertEquals(11.11, biddedTask.getTaskBidList ().get (0).getBidAmount());

        bidList.add(new Bid((double) 15,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList ().get (1).setBidAmount((double) 0.11);
        assertEquals(0.11, biddedTask.getTaskBidList ().get (1).getBidAmount());

    }
    public void testProviderCancelBid(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();

        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);


        bidList.add(new Bid((double) 10,"Fetch", "me"));
        bidList.add(new Bid((double) 11,"Fetch", "she"));
        bidList.add(new Bid((double) 12,"Fetch", "it"));
        bidList.add(new Bid((double) 13,"Fetch", "they"));
        bidList.add(new Bid((double) 14,"Fetch", "he"));
        bidList.add(new Bid((double) 10,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);

        int i;
        ArrayList<Integer> popList = null;

        for (i = 0; i < bidList.size(); i++ ) {
            if (bidList.get(i).getProviderId() == "me") {
                popList.add(i);
            }
        }
        for (i = 0;i < popList.size();i++ ) {
            bidList.remove(popList.get (i));
        }

        assertEquals(4, bidList.size());

    }
    public void testProviderSetBid(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid((double) 10,"Fetch", "me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("Fetch", biddedTask.getTaskBidList ().get (0).getTaskID());
        assertEquals("me", biddedTask.getTaskBidList ().get (0).getProviderId());
        assertEquals(10, biddedTask.getTaskBidList ().get (0).getBidAmount());

    }
    public void testSetBidCounter(){
        BidCounter bidCounter = new BidCounter("lily3",0);

        BidController.buildBidCounterOfThisRequester buildBidCounterOfThisRequester = new BidController.buildBidCounterOfThisRequester();
        buildBidCounterOfThisRequester.execute(bidCounter);

        AsyncTask.Status taskStatus;
        do {
            taskStatus =buildBidCounterOfThisRequester.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute("lily3");
        BidCounter searchedBidCounter = new BidCounter();

        try {
            searchedBidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(searchedBidCounter.getRequesterId(),bidCounter.getRequesterId());

    }
    public void testUpdateBidCounter(){
        BidCounter bidCounter = new BidCounter("shell",0);
        BidController.buildBidCounterOfThisRequester buildBidCounterOfThisRequester = new BidController.buildBidCounterOfThisRequester();
        buildBidCounterOfThisRequester.execute(bidCounter);

        AsyncTask.Status taskStatus;
        do {
            taskStatus =buildBidCounterOfThisRequester.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        bidCounter.setCounter(2);
        BidController.updateBidCounterOfThisRequester updateBidCounterOfThisRequester = new BidController.updateBidCounterOfThisRequester();
        updateBidCounterOfThisRequester.execute(bidCounter);

        do {
            taskStatus =updateBidCounterOfThisRequester.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BidController.searchBidCounterOfThisRequester searchBidCounterOfThisRequester = new BidController.searchBidCounterOfThisRequester();
        searchBidCounterOfThisRequester.execute("shell");
        BidCounter searchedBidCounter = new BidCounter();

        try {
            searchedBidCounter = searchBidCounterOfThisRequester.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        assertEquals(searchedBidCounter.getRequesterId(),bidCounter.getRequesterId());
        assertEquals(searchedBidCounter.getCounter(),2);


    }


}
