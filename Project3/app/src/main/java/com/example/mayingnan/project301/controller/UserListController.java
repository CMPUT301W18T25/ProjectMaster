
package com.example.mayingnan.project301.controller;


import com.example.mayingnan.project301.User;

import java.util.ArrayList;

public class UserListController {

    public ArrayList<User> userlist;


    public void addUser(User user){

    }

    public void updateUser(){

    }

    public ArrayList<User> getAllUsers(){
        return userlist;
    }

    public User getAUseByName(String name){
        User user = new User ();
        return user;
    }
    
}