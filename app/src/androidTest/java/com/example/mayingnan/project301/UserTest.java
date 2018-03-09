package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.controller.UserListController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by wdong2 on 2/15/18.
 */

public class UserTest extends ActivityInstrumentationTestCase2 {


    public UserTest() {
        super(LogInActivity.class);
    }

    public void testAddUser(){
        String test = "test2";
        User user = new User(test,test,test,test,test,test,test);
        UserListController.addUser addUser = new UserListController.addUser();

        addUser.execute(user);
        UserListController.GetAllUsers getAllUsers = new UserListController.GetAllUsers();
        getAllUsers.execute("");
        ArrayList<User> Userlist = null;
        try {
            Userlist = getAllUsers.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        int found = 0;
        for (User u: Userlist){

            //Log.i("username   ",u.getUserName());

            if(u.getUserName().equals(user.getUserName())){
                found = 1;
            }
        }


        assertEquals(found,1);;

    }
    public void testUpdateUser(){
        User user = new User();
        user.setUserName("hi");
        UserListController uc = new UserListController();
        UserListController.addUser addUser = new UserListController.addUser();

        addUser.execute(user);
        user.setUserName("tester");
        //uc.updateUser(user);
        assertEquals(uc.getAUserByName("tester"),user);
    }
    public void testGetAllUsers(){
        User user1 = new User();
        user1.setUserName("yue");
        User user2 = new User();
        user2.setUserName("yy");
        UserListController uc = new UserListController();

        //ArrayList<User> userList = uc.getAllUsers();

        //assertEquals(userList.size(),2);
        //assertTrue(userList.contains(user1));
        //assertTrue(userList.contains(user2));

    }
    public void testGetAUserByName(){
        User user1 = new User();
        user1.setUserName("y1");
        User user2 = new User();
        user2.setUserName("yy");
        UserListController uc = new UserListController();
        //ArrayList<User> userList = uc.getAllUsers();

        assertEquals(uc.getAUserByName("y1"),user1);

    }


}

