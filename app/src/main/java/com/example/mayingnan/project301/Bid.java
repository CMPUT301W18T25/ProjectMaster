package com.example.mayingnan.project301;

/**
 * Created by User on 2018/2/25.
 */

public class Bid {
    private String taskID;
    private Float bidAmount;
    private String providerName;
    public Bid(String taskName,Float bidAmount, String providerName){
        this.taskID = taskName;
        this.bidAmount = bidAmount;
        this.providerName = providerName;

    }

    public void setBidAmount(Float bidAmount){
        this.bidAmount = bidAmount;
    }
    public String getTaskName(){
        return this.taskID;
    }
    public Float getBidAmount(){
        return this.bidAmount;
    }
    public String getProviderName(){
        return this.providerName;
    }
}
