package com.example.mayingnan.project301;

// test - jstys

/**
 * Created by User on 2018/2/25.
 */

public class Bid {
    private String taskName;
    private Float bidAmount;
    private String providerName;
    public Bid(String taskName,Float bidAmount, String providerName){
        this.taskName = taskName;
        this.bidAmount = bidAmount;
        this.providerName = providerName;

    }

    public void setBidAmount(Float bidAmount){
        this.bidAmount = bidAmount;
    }
    public String getTaskName(){
        return this.taskName;
    }
    public Float getBidAmount(){
        return this.bidAmount;
    }
    public String getProviderName(){
        return this.providerName;
    }
}
