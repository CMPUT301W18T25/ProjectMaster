package project301;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

import project301.allUserActivity.LogInActivity;



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

}
