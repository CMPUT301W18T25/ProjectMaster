package project301;

import android.util.Log;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Task model contains all the features of a task. Basic setters and getters are included.
 * @classname : Task
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author :Julian Stys
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/* status: request, bidden, assigned, done */

//TODO add idealprice, photo, time set to current by built-in methods?
public class Task {
    private String taskName;
    private String taskDetails;
    private String taskRequester;
    private String taskProvider;
    private String taskStatus;
    private String taskAddress;
    private Double lowestBid;
    private Double tasklatitude;
    private Double tasklgtitude;
    private String taskID;
    private ArrayList<Bid> taskBidList;
    private Photo taskPhoto;
    private Double taskIdealPrice;
    private DateTime taskDateTime;
    private ArrayList<Bid> canceledBidList;
    private Bid choosenBid;



    public Task(String s, String s1, String michael, Object o, String bidding, String s2, ArrayList<Bid> bidList, Photo emptyPhoto, Double idealprice, DateTime datetime) {
    }
    public String getId(){
        return this.taskID;
    }
    public void setId(String id){
        this.taskID = id;
    }

    public Task(){
        this.taskName="";
        this.taskDetails="";
        this.taskRequester="";
        this.taskProvider="";
        this.taskStatus="";
        this.taskAddress="";
        this.taskBidList= new ArrayList<Bid>();
        this.canceledBidList= new ArrayList<Bid>();
        this.choosenBid = null;
        this.taskPhoto=null;
        this.taskIdealPrice=null;
        this.taskDateTime=null;
        this.lowestBid = 0.0;
    }

    public Task(String taskName, String taskDetails, String taskRequester, String taskProvider,
                String taskStatus, String taskAddress, ArrayList<Bid> taskBidList, Photo taskPhoto){
        this.taskName=taskName.toLowerCase();
        this.taskDetails=taskDetails.toLowerCase();
        this.taskRequester=taskRequester.toLowerCase();
        if (this.getTaskProvider() != null){
            this.taskProvider=taskProvider.toLowerCase();
        }
        this.taskStatus=taskStatus.toLowerCase();
        this.taskAddress=taskAddress.toLowerCase();
        this.taskBidList=taskBidList;
        this.taskPhoto=taskPhoto;
        this.taskIdealPrice=null;
        this.taskDateTime=null;
        this.lowestBid = 0.0;
    }

    public void requesterAcceptsBid(){
        this.taskPhoto=taskPhoto;
    }

    // Setters
    public void setTaskName(String taskName){
        this.taskName=taskName.toLowerCase();
    }
    public void setTaskDetails(String taskDetails){
        this.taskDetails=taskDetails.toLowerCase();
    }
    public void setTaskIdealPrice(Double taskIdealPrice){
        this.taskIdealPrice=taskIdealPrice;
    }
    public void setTaskDateTime(DateTime taskDateTime){this.taskDateTime=taskDateTime;}
    public void setTaskRequester(String taskRequester){
        this.taskRequester=taskRequester.toLowerCase();
    }
    public void setTaskProvider(String taskProvider){
        if(taskProvider==null){
            this.taskProvider = null;
        }
        else{
        this.taskProvider=taskProvider.toLowerCase();}
    }
    public void setTaskStatus(String taskStatus){
        this.taskStatus=taskStatus.toLowerCase();
    }
    public void setTaskAddress(String taskAddress){
        this.taskAddress=taskAddress.toLowerCase();
    }
    public void setTaskBidList(ArrayList<Bid> taskBidList){
        this.taskBidList=taskBidList;
    }
    public void setTaskPhoto(Photo taskPhoto){
        this.taskPhoto=taskPhoto;
    }

    // Getters
    public String getTaskName(){
        return this.taskName;
    }
    public String getTaskDetails(){
        return taskDetails;
    }
    public Double getTaskIdealPrice(){return taskIdealPrice;}
    public DateTime getTaskDateTime(){return taskDateTime;}
    public String getTaskRequester(){
        return taskRequester;
    }
    public String getTaskProvider(){
        return taskProvider;
    }
    public String getTaskStatus(){
        return taskStatus;
    }
    public String getTaskAddress(){
        return taskAddress;
    }
    public ArrayList<Bid> getTaskBidList(){
        return taskBidList;
    }
    public Double getLowestBid() {return this.lowestBid;}
    public Photo getTaskPhoto(){
        return taskPhoto;
    }

    public Double getTasklatitude() {
        return tasklatitude;
    }

    public void setTasklatitude(Double tasklatitude) {
        this.tasklatitude = tasklatitude;
    }

    public Double getTasklgtitude() {
        return tasklgtitude;
    }

    public void setTasklgtitude(Double tasklgtitude) {
        this.tasklgtitude = tasklgtitude;
    }
    public void setChoosenBid(Bid bid){this.choosenBid = bid;}
    public void addCanceledBid(Bid bid){
        this.canceledBidList.add(bid);
    }
    public ArrayList<Bid> getCanceledBidList(){
        return this.canceledBidList;
    }
    public Bid getChoosenBid(){
        return choosenBid;
    }
    //requester needs to see all the bids which he hasn't canceled
    public ArrayList<Bid> getAvailableBidListOfThisTask(){
        ArrayList<Bid> result = new ArrayList<>();
        if(canceledBidList==null){
            return taskBidList;
        }
        //find difference between taskbidlist and canceledBidlist
        for(Bid bid:taskBidList){
            boolean success = true;
            for(Bid canceledbid:canceledBidList){
                if(canceledbid.getProviderId().equals(bid.getProviderId()) && canceledbid.getBidAmount().equals(bid.getBidAmount())){
                    success = false;
                }
            }
            if(success){
                result.add(bid);
            }
        }
        return result;
    }
    /**
     * Add a bid into task list
     * @param bid a bid object
     * @return
     */
    public boolean addBid(Bid bid){
        if (bid.getBidAmount() < this.lowestBid){
            this.lowestBid = (Double) bid.getBidAmount();
        }
        for (int i = 0; i<taskBidList.size();i++){
            String selectedItemProvider = taskBidList.get(i).getProviderId();
            if (selectedItemProvider.equals(bid.getProviderId())){
                taskBidList.set(i, bid);
                return true;
            }
        }
        taskBidList.add(bid);
        return false;
    }

    /**
     * Cancel a bid of this provider
     * @param providerName provider name
     * @return
     */
    public boolean cancelBid(String providerName){
        for (int i = 0; i<taskBidList.size();i++){
            if (providerName.equals(taskBidList.get(i).getProviderId())){
                taskBidList.remove(i);
                return true;
            }
        }
        return false;
    }
    public void changeStatusAfterDeclineDeal(Bid bid){
        this.canceledBidList.add(bid);
        this.setChoosenBid(null);
        this.setTaskProvider(null);
        ArrayList<Bid> bidList = this.getAvailableBidListOfThisTask();
        if(bidList.size() != 0){
            this.setTaskStatus("bidden");

            for(Bid b:bidList){
                Log.i("BidProvider",b.getProviderId());
            }

        }else{
            this.setTaskStatus("request");
        }
    }

    public void changeStatusAfterDeclineBid(Bid bid){
        this.canceledBidList.add(bid);
        ArrayList<Bid> bidList = this.getAvailableBidListOfThisTask();
        if(bidList.size() != 0){
            this.setTaskStatus("bidden");

            for(Bid b:bidList){
                Log.i("BidProvider",b.getProviderId());
            }

        }else{
            this.setTaskStatus("request");
        }
    }

    public Double findLowestbid(){
        ArrayList<Bid> bidList = this.getAvailableBidListOfThisTask();
        if(bidList.size()==0){
            return null;
        }

        Double lowestBid = bidList.get(0).getBidAmount();
        for(Bid bid:bidList){

            if(bid.getBidAmount()<lowestBid){
                lowestBid = bid.getBidAmount();

            }
        }
        return lowestBid;
    }
}
