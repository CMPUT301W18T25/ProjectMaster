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
        User user = new User();
        UserListController uc = new UserListController();
        uc.addUser(user);
        ArrayList<User> userList = uc.getAllUsers();
        assertTrue(userList.contains(user));

    }
    public void testUpdateUser(){
        User user = new User();
        user.setUserName("hi");
        UserListController uc = new UserListController();
        uc.addUser(user);
        user.setUserName("tester");
        uc.updateUser(user);
        assertEquals(uc.getAUserByName("tester"),user);
    }
    public void getAllUsersTest(){
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
    public void getAUserByNameTest(){
        User user1 = new User();
        user1.setUserName("y1");
        User user2 = new User();
        user2.setUserName("yy");
        UserListController uc = new UserListController();
        ArrayList<User> userList = uc.getAllUsers();

        assertEquals(uc.getAUserByName("y1"),user1);

    }


}

