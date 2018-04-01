package project301;
import android.test.ActivityInstrumentationTestCase2;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import project301.allUserActivity.LogInActivity;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import project301.User;
import project301.controller.UserController;

import static org.junit.Assert.*;
/**
 * Created by wdong2 on 2/15/18.
 */

@SuppressWarnings("ALL")
public class UserTest extends ActivityInstrumentationTestCase2 {
    public UserTest() {
        super(LogInActivity.class);
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddUser() throws Exception{
        String Name = "Lilian";
        User user = new User();

        user.setUserName(Name);
        UserController.addUser addUser = new UserController.addUser();
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

        UserController.GetAllUsers getAllUsers = new UserController.GetAllUsers();
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
        //noinspection ConstantConditions
        for (User u: Userlist){

            Log.i("username   ",u.getUserName());

            if(u.getUserName().equals(user.getUserName())){
                found = 1;
            }
        }

        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");
        assertEquals(found,1);


    }
    @Test

    public void testGetAUserByName(){
        User user1 = new User();
        user1.setUserName("y3hh");
        UserController.addUser addUser = new UserController.addUser();
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
        User newUser  = new User();
        UserController uc = new UserController();
        int found = 0;
        newUser = uc.getAUserByName("y3hh");
        if(newUser!=null){
            found = 1;
        }

        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");
        assertEquals(found,1);

    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testGetAUserById(){
        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");
        User user1 = new User();
        user1.setUserName("y3hh");
        UserController.addUser addUser = new UserController.addUser();
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
        User newUser  = new User();
        UserController uc = new UserController();
        int found = 0,found2 = 0;
        newUser = uc.getAUserByName("y3hh");
        if(newUser!=null){
            found = 1;
        }


        //noinspection ConstantConditions
        Log.i("said",newUser.getUserName());
        newUser = uc.getAUserById(newUser.getId());
        if(newUser!=null){
            found2= 1;
        }
        assertEquals(found,1);
        assertEquals(found2,1);


    }
    @Test

    public void testcheckUserByNameAndPassword(){
        User user1 = new User();
        user1.setUserName("yue");
        user1.setUserPassword("yuepassword");


        UserController.addUser addUser = new UserController.addUser();
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

        UserController uc = new UserController();
        boolean findUser1 = uc.checkUserByNameAndPassword("yue","yuepassword");
        assertTrue(findUser1);

        boolean notfindUser1 = uc.checkUserByNameAndPassword("yue","y1pas2s");

        assertEquals(notfindUser1,false);
        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");
    }
    @Test

    public void testUpdateUser() throws InterruptedException {
        UserController.deleteAllUsers deleteAllUsers1 = new UserController.deleteAllUsers();
        deleteAllUsers1.execute("");
        AsyncTask.Status taskStatus1;
        do {
            taskStatus1 = deleteAllUsers1.getStatus();
        } while (taskStatus1 != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user1 = new User();
        user1.setUserName("yue12");
        user1.setUserPassword("yue12pass");
        UserController.addUser addUser = new UserController.addUser();
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
        user1.setUserPassword("yue12word");
        UserController.updateUser updateUser = new UserController.updateUser();
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
        //boolean notfindUser1 = uc.checkUserByNameAndPassword("yue12","yue12pass");
        //assertEquals(notfindUser1,false);
        UserController uc = new UserController();
        boolean findUser1 = uc.checkUserByNameAndPassword("yue12","yue12word");
        assertEquals(findUser1,true);
        boolean notfindUser1 = uc.checkUserByNameAndPassword("yue12","yue12pass");
        assertEquals(notfindUser1,false);



    }
    @Test


    /*public void testCheckValidationSignUp (){
        UserListController.deleteAllUsers deleteAllUsers = new UserListController.deleteAllUsers();
        deleteAllUsers.execute("");


        String userId = null;
        User user1 = new User();
        user1.setUserName("yue15");

        UserListController uc = new UserListController();
        userId = uc.addUserAndCheck(user1);

        assertNotEquals(userId,null);

        User user2 = new User();
        user2.setUserName("yue15");
        boolean checkValidUser = uc.checkValidationSignUp("yue15");
        if(checkValidUser) {
            Log.i("fault","fault");
            UserListController uc2 = new UserListController();
            userId = uc2.addUserAndCheck(user2);
        }
        else{
            userId = null;
        }

        assertEquals(userId,null);

    }
    */

    public void testCheckValidationSignUp (){
        // TaskController.deleteAllTasks deleteAllTasks = new TaskController.deleteAllTasks();
        //deleteAllTasks.execute("");

        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");
        AsyncTask.Status taskStatus1;
        do {
            taskStatus1 =deleteAllUsers.getStatus();
        } while (taskStatus1 != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String userId = null;
        User user1 = new User();
        user1.setUserName("yue15");
        UserController.addUser addUser= new UserController.addUser();
        addUser.execute(user1);

        AsyncTask.Status taskStatus2;
        do {
            taskStatus2 = addUser.getStatus();
        } while (taskStatus2 != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user2 = new User();
        user2.setUserName("yue15");
        UserController uc = new UserController();
        userId = uc.addUserAndCheck(user2);

        assertEquals(userId,null);

        User user3 = new User();
        user3.setUserName("yumi");
        UserController uc2 = new UserController();
        userId = uc2.addUserAndCheck(user3);
        assertNotEquals(userId,null);



    }

}

