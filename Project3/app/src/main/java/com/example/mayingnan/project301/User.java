package com.example.mayingnan.project301;

import android.media.Image;

/**
 * Created by User on 2018/2/25.
 */

public class User {

    private String userName;
    private String userPhone;
    private String userAddress;
    private String userEmail;
    private String userPassword;
    private String userType;
    private Photo userPhoto;

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

    public Photo getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(Photo userPhoto) {
        this.userPhoto = userPhoto;
    }
}
