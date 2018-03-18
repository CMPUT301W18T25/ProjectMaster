package com.example.mayingnan.project301;

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
    public User(){
        super();
    }
    public User(String userId,String userName,String userPhone,String userAddress,String userEmail,String userPassword,String userType){
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.userPhone = userPhone;

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

}
