
package com.example.mayingnan.project301.controller;


import com.example.mayingnan.project301.User;

import java.util.ArrayList;

public class UserListController {

    public ArrayList<User> userlist;

    public boolean testTrue(String name){
        return true;
    }

    public boolean testFalse(String name){
        return false;
    }

    public boolean checkUserName(String name){
        /**
         * return true for valid user name and passward; false otherwise
         */
        if (name == "wdong2"){ return true;}
        if (name == "IUN"){ return false;}
        return true;
    }

    public void addUser(User user){

    }

    public void updateUser(User user){

    }

    public ArrayList<User> getAllUsers(){
        return userlist;
    }

    public User getAUserByName(String name){
        User user = new User ();
        return user;
    }
    
}