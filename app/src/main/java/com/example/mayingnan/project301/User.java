package com.example.mayingnan.project301;

/**
 * @classname : User
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
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
}
