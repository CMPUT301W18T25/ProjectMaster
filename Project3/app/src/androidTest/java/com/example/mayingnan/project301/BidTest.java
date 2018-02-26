package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by julianstys on 2018-02-25.
 */


public class BidTest extends ActivityInstrumentationTestCase2 {


    public BidTest() {
        super(LogInActivity.class);
    }
    public void setBidAmountTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch", (float) 10,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList ().get (0).getProviderName());
        assertEquals(10, biddedTask.getTaskBidList ().get (0).getBidAmount());

        biddedTask.getTaskBidList ().get (0).setBidAmount((float) 1.23);
        assertEquals(1.23, biddedTask.getTaskBidList ().get (0).getBidAmount());

        bidList.add(new Bid("Fetch", (float) 15,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList ().get (1).getProviderName());
        assertEquals(15, biddedTask.getTaskBidList ().get (1).getBidAmount());

        biddedTask.getTaskBidList ().get (1).setBidAmount((float) 9.09);
        assertEquals(9.00, biddedTask.getTaskBidList ().get (1).getBidAmount());


    }
    public void providerUpdateBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch", (float) 10,"me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList ().get (0).setBidAmount((float) 11.11);
        assertEquals(11.11, biddedTask.getTaskBidList ().get (0).getBidAmount());

        bidList.add(new Bid("Fetch", (float) 15,"me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList ().get (1).setBidAmount((float) 0.11);
        assertEquals(0.11, biddedTask.getTaskBidList ().get (1).getBidAmount());

    }
    public void providerCancelBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);
        ArrayList<Integer> popList = null;

        bidList.add(new Bid("Fetch", (float) 10,"me"));
        bidList.add(new Bid("Fetch", (float) 11,"she"));
        bidList.add(new Bid("Fetch", (float) 12,"it"));
        bidList.add(new Bid("Fetch", (float) 13,"they"));
        bidList.add(new Bid("Fetch", (float) 14,"he"));
        bidList.add(new Bid("Fetch", (float) 10,"me"));
        biddedTask.setTaskBidList(bidList);

        int i;
        for (i = 0; i < bidList.size(); i++ ) {
            if (bidList.get (i).getProviderName() == "me") {
                popList.add(i);
            }
        }
        for (i = 0;i < popList.size();i++ ) {
            bidList.remove(popList.get (i));
        }

        assertEquals(4, bidList.size());

    }
    public void providerSetBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch", (float) 10,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("Fetch", biddedTask.getTaskBidList ().get (0).getTaskName());
        assertEquals("me", biddedTask.getTaskBidList ().get (0).getProviderName());
        assertEquals(10, biddedTask.getTaskBidList ().get (0).getBidAmount());

    }
}
