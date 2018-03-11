package com.example.mayingnan.project301;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.controller.UserListController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by wdong2 on 2/15/18.
 */

public class UserTest{

    @Test
    public void testAddUser() throws Exception{
        String test = "test8";
        User user = new User(test,test,test,test,test,test,test);
        UserListController.addUser addUser = new UserListController.addUser();
        addUser.execute(user);
        // Hang around till is done
        AsyncTask.Status taskStatus;
        do {
            taskStatus = addUser.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

            Log.i("username   ",u.getUserName());

            if(u.getUserName().equals(user.getUserName())){
                found = 1;
            }
        }

        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");
        assertEquals(found,1);

    }
    @Test

    public void testGetAUserByName(){
        User user1 = new User();
        user1.setUserName("y3hh");
        UserListController.addUser addUser = new UserListController.addUser();
        addUser.execute(user1);

        // Hang around till is done
        AsyncTask.Status taskStatus;
        do {
            taskStatus = addUser.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserListController.getAUserByName getAUserByName = new UserListController.getAUserByName();
        getAUserByName.execute("y3hh");


        ArrayList<User> Userlist = null;
        try {
            Userlist = getAUserByName.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        int found = 0;
        for (User u: Userlist){

            Log.i("username   ",u.getUserName());

            if(u.getUserName().equals(user1.getUserName())){
                found = 1;
            }
        }
        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");
        assertEquals(found,1);

    }
    @Test

    public void testcheckUserByNameAndPassword(){
        User user1 = new User();
        user1.setUserName("y12");
        user1.setUserPassword("y12pass");


        UserListController.addUser addUser = new UserListController.addUser();
        addUser.execute(user1);

        // Hang around till is done
        AsyncTask.Status taskStatus;
        do {
            taskStatus = addUser.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserListController uc = new UserListController();
        boolean findUser1 = uc.checkUserByNameAndPassword("y12","y12pass");
        assertEquals(findUser1,true);

        boolean notfindUser1 = uc.checkUserByNameAndPassword("y12","y1pas2s");

        assertEquals(notfindUser1,false);
        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");
    }
    @Test

    public void testUpdateUser(){
        User user1 = new User();
        user1.setUserName("yue12");
        user1.setUserPassword("yue12pass");
        UserListController.addUser addUser = new UserListController.addUser();
        addUser.execute(user1);
        // Hang around till is done
        AsyncTask.Status taskStatus;
        do {
            taskStatus = addUser.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user1.setUserPassword("yue12password");
        UserListController.updateUser updateUser= new UserListController.updateUser();
        updateUser.execute(user1);

        AsyncTask.Status taskStatus2;
        do {
            taskStatus2 = updateUser.getStatus();
        } while (taskStatus2 != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserListController uc = new UserListController();
        boolean notfindUser1 = uc.checkUserByNameAndPassword("yue12","yue12pass");
        assertEquals(notfindUser1,false);

        boolean findUser1 = uc.checkUserByNameAndPassword("yue12","yue12password");
        assertEquals(findUser1,true);
        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");


    }
    @Test


    public void testCheckValidationSignUp (){
        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");

        User user1 = new User();
        user1.setUserName("yue15");

        UserListController uc = new UserListController();
        boolean addSucess = uc.addUserAndCheck(user1);
        assertEquals(addSucess,true);

        User user2 = new User();
        user2.setUserName("yue15");
        boolean addFailed = uc.addUserAndCheck(user2);
        assertEquals(addFailed,false);


    }

}

