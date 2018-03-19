package com.example.mayingnan.project301;

import java.util.ArrayList;

/**
 * Created by  on 2018/2/25.
 */

public class User {
    private String userId;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private String userPassword;
    private String userType;
    private String resultId;
    private ArrayList<String> providerBiddenTask;
    private ArrayList<String> requesterBiddenTask;

    public User(){
        super();
    }
    public User(String userId,String userName,String userPhone,String userAddress,String userEmail,String userPassword,String userType,String resultId){
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.userPhone = userPhone;
        this.resultId = resultId;
        this.providerBiddenTask = new ArrayList<String>();
        this.requesterBiddenTask = new ArrayList<String>();

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getId(){
        return userId;
    }
    public void setId(String userId){
        this.userId = userId;
    }

    public String getResultId(){
        return resultId;
    }
    public void setResultId(String resultId){
        this.resultId = resultId;
    }

    public ArrayList<String> getProviderBiddenTask(){
        return this.providerBiddenTask;
    }
    public void addProviderBiddenTask(String taskID){
        providerBiddenTask.add(taskID);
    }
    public void removeABiddenTask(String taskID){
        providerBiddenTask.remove(taskID);
    }

    public ArrayList<String> getRequesterBiddenTask(){
        return this.requesterBiddenTask;
    }
    public void addRequesterBiddenTask(String taskID){
        requesterBiddenTask.add(taskID);
    }
    public void removeRequesterBiddenTask(String taskID){
        requesterBiddenTask.remove(taskID);
    }
}
