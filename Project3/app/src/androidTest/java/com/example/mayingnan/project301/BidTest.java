package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;

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
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch",10,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList()[0].getProviderName());
        assertEquals(10, biddedTask.getTaskBidList()[0].getBidAmount());

        biddedTask.getTaskBidList()[0].setBidAmount(1.23)
        assertEquals(1.23, biddedTask.getTaskBidList()[0].getBidAmount());

        bidList.add(new Bid("Fetch",15,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("me", biddedTask.getTaskBidList()[1].getProviderName());
        assertEquals(15, biddedTask.getTaskBidList()[1].getBidAmount());

        biddedTask.getTaskBidList()[1].setBidAmount(9.09)
        assertEquals(9.00, biddedTask.getTaskBidList()[1].getBidAmount());


    }
    public void providerUpdateBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch",10,"me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList()[0].setBidAmount(11.11);
        assertEquals(11.11, biddedTask.getTaskBidList()[0].getBidAmount());

        bidList.add(new Bid("Fetch",15,"me"));
        biddedTask.setTaskBidList(bidList);
        biddedTask.getTaskBidList()[1].setBidAmount(0.11);
        assertEquals(0.11, biddedTask.getTaskBidList()[1].getBidAmount());

    }
    public void providerCancelBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);
        ArrayList<int> popList;

        bidList.add(new Bid("Fetch",10,"me"));
        bidList.add(new Bid("Fetch",11,"she"));
        bidList.add(new Bid("Fetch",12,"it"));
        bidList.add(new Bid("Fetch",13,"they"));
        bidList.add(new Bid("Fetch",14,"he"));
        bidList.add(new Bid("Fetch",10,"me"));
        biddedTask.setTaskBidList(bidList);

        for (i = 0;i < bidList.size();i++ ) {
            if (bidList[i].getProviderName() == "me") {
                popList.add(i);
            }
        }
        for (i = 0;i < popList.size();i++ ) {
            bidList.remove(popList[i]);
        }

        assertEquals(4, bidList.size());

    }
    public void providerSetBidTest(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Task biddedTask = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        bidList.add(new Bid("Fetch",10,"me"));
        biddedTask.setTaskBidList(bidList);
        assertEquals("Fetch", biddedTask.getTaskBidList()[0].getTaskName());
        assertEquals("me", biddedTask.getTaskBidList()[0].getProviderName());
        assertEquals(10, biddedTask.getTaskBidList()[0].getBidAmount());

    }
}
