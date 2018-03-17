package com.example.mayingnan.project301;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * Created by User on 2018/2/25.
 */

//TODO add idealprice, photo, time set to current by built-in methods?
public class Task {
    private String taskName;
    private String taskDetails;
    private String taskRequester;
    private String taskProvider;
    private String taskStatus;
    private String taskAddress;
    private Float lowestBid;

    private String taskID;

    private ArrayList<Bid> taskBidList;

    private Photo taskPhoto;
    private Double taskIdealPrice;
    private DateTime taskDateTime;



    public Task(String s, String s1, String michael, Object o, String bidding, String s2, ArrayList<Bid> bidList, Photo emptyPhoto, Double idealprice, DateTime datetime) {
    }
    public String getId(){
        return this.taskID;
    }
    public void setId(String id){
        this.taskID = id;
    }

    public Task(){
        this.taskName=null;
        this.taskDetails=null;
        this.taskRequester=null;
        this.taskProvider=null;
        this.taskStatus=null;
        this.taskAddress=null;
        this.taskBidList= new ArrayList<Bid>();
        this.taskPhoto=null;
        this.taskIdealPrice=null;
        this.taskDateTime=null;
        this.lowestBid = (float)0;
    }

    public Task(String taskName, String taskDetails, String taskRequester, String taskProvider,
                String taskStatus, String taskAddress, ArrayList<Bid> taskBidList, Photo taskPhoto){
        this.taskName=taskName;
        this.taskDetails=taskDetails;
        this.taskRequester=taskRequester;
        this.taskProvider=taskProvider;
        this.taskStatus=taskStatus;
        this.taskAddress=taskAddress;
        this.taskBidList=taskBidList;
        this.taskPhoto=taskPhoto;
        this.taskIdealPrice=null;
        this.taskDateTime=null;
        this.lowestBid = (float)0.0;
    }

    public void requesterAcceptsBid(){
        this.taskPhoto=taskPhoto;
    }

    // Setters
    public void setTaskName(String taskName){
        this.taskName=taskName;
    }
    public void setTaskDetails(String taskDetails){
        this.taskDetails=taskDetails;
    }
    public void setTaskIdealPrice(Double taskIdealPrice){
        this.taskIdealPrice=taskIdealPrice;
    }
    public void setTaskDateTime(DateTime taskDateTime){this.taskDateTime=taskDateTime;}
    public void setTaskRequester(String taskRequester){
        this.taskRequester=taskRequester;
    }
    public void setTaskProvider(String taskProvider){
        this.taskProvider=taskProvider;
    }
    public void setTaskStatus(String taskStatus){
        this.taskStatus=taskStatus;
    }
    public void setTaskAddress(String taskAddress){
        this.taskAddress=taskAddress;
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
    public float getLowestBid() {return this.lowestBid;}
    public Photo getTaskPhoto(){
        return taskPhoto;
    }

    public boolean addBid(Bid bid){
        if (bid.getBidAmount() < this.lowestBid){
            this.lowestBid = bid.getBidAmount();
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

    public boolean cancelBid(String providerName){
        for (int i = 0; i<taskBidList.size();i++){
            if (providerName.equals(taskBidList.get(i).getProviderId())){
                taskBidList.remove(i);
                return true;
            }
        }
        return false;
    }

}
