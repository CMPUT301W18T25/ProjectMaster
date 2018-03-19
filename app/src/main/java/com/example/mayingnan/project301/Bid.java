package com.example.mayingnan.project301;

/**
 * @classname : Bid
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
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
    public Bid(Double bidAmount, String providerId){
        this.bidAmount = bidAmount;
        this.providerId= providerId;
        this.taskID = null;
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
    public void setProviderId(String providerId){this.providerId = providerId;}
}
