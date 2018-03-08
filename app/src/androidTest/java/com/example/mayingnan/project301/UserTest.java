package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.controller.UserListController;

import java.util.ArrayList;

/**
 * Created by wdong2 on 2/15/18.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {


    public UserTest() {
        super(LogInActivity.class);
    }

    public void testAddUser(){
        String test = "test";
        User user = new User(test,test,test,test,test,test,test);
        UserListController uc = new UserListController();
        UserListController.addUser addUser = new UserListController.addUser();

        addUser.execute(user);
        //ArrayList<User> userList = uc.getAllUsers();
        //assertTrue(userList.contains(user));

    }
    public void testUpdateUser(){
        User user = new User();
        user.setUserName("hi");
        UserListController uc = new UserListController();
        UserListController.addUser addUser = new UserListController.addUser();

        addUser.execute(user);
        user.setUserName("tester");
        uc.updateUser(user);
        assertEquals(uc.getAUserByName("tester"),user);
    }
    public void testGetAllUsers(){
        User user1 = new User();
        user1.setUserName("yue");
        User user2 = new User();
        user2.setUserName("yy");
        UserListController uc = new UserListController();

        ArrayList<User> userList = uc.getAllUsers();

        assertEquals(userList.size(),2);
        assertTrue(userList.contains(user1));
        assertTrue(userList.contains(user2));

    }
    public void testGetAUserByName(){
        User user1 = new User();
        user1.setUserName("y1");
        User user2 = new User();
        user2.setUserName("yy");
        UserListController uc = new UserListController();
        ArrayList<User> userList = uc.getAllUsers();

        assertEquals(uc.getAUserByName("y1"),user1);

    }


}

