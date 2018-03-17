package com.example.mayingnan.project301;

/*
 * Created by User on 2018/2/25.
 */

public class Bid {
    private String taskID;
    private Double bidAmount;
    private String providerId;

    public Bid(Double bidAmount, String providerId, String taskID){
        this.bidAmount = bidAmount;
        this.providerId= providerId;
        this.taskID = taskID;
    }

    public void setBidAmount(Double bidAmount){
        this.bidAmount = bidAmount;
    }
    public Double getBidAmount(){
        return this.bidAmount;
    }
    public String getProviderId(){
        return this.providerId;
    }
    public void setTaskID(String ID){
        this.taskID = ID;
    }
    public String getTaskID(){
        return this.taskID;
    }
}
