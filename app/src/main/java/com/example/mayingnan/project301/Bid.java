package com.example.mayingnan.project301;

/**
 * Created by User on 2018/2/25.
 */

public class Bid {
    private String taskID;
    private Float bidAmount;
    private String providerName;

    public Bid(Float bidAmount, String providerName, String taskID){
        this.bidAmount = bidAmount;
        this.providerName = providerName;
        this.taskID = taskID;
    }

    public void setBidAmount(Float bidAmount){
        this.bidAmount = bidAmount;
    }
    public Float getBidAmount(){
        return this.bidAmount;
    }
    public String getProviderName(){
        return this.providerName;
    }
    public void setTaskID(String ID){
        this.taskID = ID;
    }
    public String getTaskID(){
        return this.taskID;
    }
}
