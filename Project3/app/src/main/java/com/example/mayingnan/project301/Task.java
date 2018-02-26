package com.example.mayingnan.project301;

import java.util.ArrayList;

/**
 * Created by User on 2018/2/25.
 */

public class Task {
    private String taskName;
    private String taskDetails;
    private String taskRequester;
    private String taskProvider;
    private String taskStatus;
    private String taskAddress;

    private ArrayList<Bid> taskBidList;
    private Photo taskPhoto;

    public Task(){
        this.taskName=null;
        this.taskDetails=null;
        this.taskRequester=null;
        this.taskProvider=null;
        this.taskStatus=null;
        this.taskAddress=null;
        this.taskBidList=null;
        this.taskPhoto=null;    }

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
    }

    public void requesterAcceptsBid(){
        this.taskPhoto=taskPhoto;
    }

    // Getters
    public String getTaskName(){
        return taskName;
    }
    public String getTaskDetails(){
        return taskDetails;
    }
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
    }public ArrayList<Bid> getTaskBidList(){
        return taskBidList;
    }public Photo getTaskPhoto(){
        return taskPhoto;
    }

    // Setters
    public void setTaskName(String taskName){
        this.taskName=taskName;
    }
    public void setTaskDetails(String taskDetails){
        this.taskDetails=taskDetails;
    }
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



}
