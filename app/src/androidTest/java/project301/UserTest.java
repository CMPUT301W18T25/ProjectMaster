package project301;

import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import project301.allUserActivity.LogInActivity;
import project301.controller.UserController;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @classname : UserTest
 * @class Detail : This is the test for user-relative methods
 *
 * @Date :   18/03/2018
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * This is the test for user-relative methods.
 */


@SuppressWarnings("ALL")
public class UserTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testAddUser() throws Exception{
        User user = new User();
        user.setUserName("Tom");
        user.setUserPassword("maf19044");

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
        user1.setUserName("Mike");
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
        UserController.getAUserByName getAUserByName = new UserController.getAUserByName();
        getAUserByName.execute("Mike");
        ArrayList<User> users = null;
        try {
            users = getAUserByName.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(!users.equals(null)){
            found = 1;
        }


        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");

        assertEquals(found,1);

    }
    @Test

    public void testcheckUserByNameAndPassword(){

        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");

        User user1 = new User();
        user1.setUserName("yueMa");
        user1.setUserPassword("123456");
        UserController.addUser addUser = new UserController.addUser();
        addUser.execute(user1);
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
        boolean findUser1 = uc.checkUserByNameAndPassword("yueMa","123456");
        assertEquals(findUser1,true);
        //boolean notfindUser1 = uc.checkUserByNameAndPassword("yueMa","yueMi2018");
        //assertEquals(notfindUser1,false);

    }
    @Test

    public void testUpdateUser() throws InterruptedException {
        /*
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
        */
        User user1 = new User();
        user1.setUserName("yue12");
        user1.setUserPassword("yue12password");
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
        user1.setUserPassword("yue12fakepassword");
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
        boolean findUser1 = uc.checkUserByNameAndPassword("yue12","yue12fakepassword");
        assertEquals(findUser1,true);
        boolean notfindUser1 = uc.checkUserByNameAndPassword("yue12","yue12password");
        assertEquals(notfindUser1,false);
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

    }
    @Test


    /*public void testCheckValidationSignUp (){
        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");


        String userId = null;
        User user1 = new User();
        user1.setUserName("yue15");

        UserController uc = new UserController();
        userId = uc.addUserAndCheck(user1);

        assertNotEquals(userId,null);

        User user2 = new User();
        user2.setUserName("yue15");
        boolean checkValidUser = uc.checkValidationSignUp("yue15");
        if(checkValidUser) {
            Log.i("fault","fault");
            UserController uc2 = new UserController();
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

        String userId = null;
        User user1 = new User();
        user1.setUserName("yueLiu");
        UserController uc = new UserController();
        userId = uc.addUserAndCheck(user1);

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userId = null;
        User user2 = new User();
        user2.setUserName("yueLiu");
        userId = uc.addUserAndCheck(user2);
        assertEquals(userId,null);
        User user3 = new User();
        user3.setUserName("yumi");
        UserController uc2 = new UserController();
        userId = uc2.addUserAndCheck(user3);
    }

}

