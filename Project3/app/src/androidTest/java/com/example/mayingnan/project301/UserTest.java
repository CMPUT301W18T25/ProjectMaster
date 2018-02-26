package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import java.util.ArrayList;

/**
 * Created by wdong2 on 2/15/18.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {


    public UserTest() {
        super(LogInActivity.class);
    }

    public void testAddUser(){
        User user = new User();
        userListController uc = new userListController();
        uc.addUser(user);
        ArrayList<User> userList = new uc.getAllUsers();
        assertTrue(userList.contains(user));

    }
    public void testUpdateUser(){
        User user = new User();
        user.setUserName = "hi";
        userListController uc = new userListController();
        uc.addUser(user);
        user.setUserName = "tester";
        uc.updateUser(user);
        ArrayList<User> userList = new uc.getAllUsers();
        assertEquals(userList.getAUserByName("tester"),user);
    }


}

