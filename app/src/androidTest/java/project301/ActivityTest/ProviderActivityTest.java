package project301.ActivityTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.UserCharacterActivity;
import project301.controller.BidController;
import project301.controller.TaskController;
import project301.controller.UserController;
import project301.providerActivity.ProviderBidHistoryActivity;
import project301.providerActivity.ProviderEditInfoActivity;
import project301.providerActivity.ProviderMainActivity;
import project301.providerActivity.ProviderMapActivity;
import project301.providerActivity.ProviderTaskBidActivity;
import project301.requesterActivity.RequesterAllListActivity;
import project301.requesterActivity.RequesterDoneListActivity;
import project301.requesterActivity.RequesterMainActivity;
import project301.requesterActivity.RequesterPostTaskActivity;
import project301.requesterActivity.RequesterViewTaskAssignedActivity;
import project301.requesterActivity.RequesterViewTaskDoneActivity;
import project301.requesterActivity.RequesterViewTaskRequestActivity;

import com.robotium.solo.Solo;

/**
 * Test for all provider activities. Some of test based on requester activity test and sign up activity test.
 * @classname : ProviderActivityTest
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */



public class ProviderActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public ProviderActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testProviderMainActivity() {
        logIn();

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("  bid history ");

        solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("edit profile");

        solo.assertCurrentActivity("Wrong Activity", ProviderEditInfoActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
    }

    public void testProviderEditProfileActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("edit profile");

        solo.assertCurrentActivity("Wrong Activity", ProviderEditInfoActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.edit_email));

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.clearEditText((EditText) solo.getView(R.id.edit_phone));

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("       log out     ");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.login_name));

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.login_name));

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"abc");

        solo.clickOnButton("Log In");

        solo.clickOnButton("provider");

        solo.enterText((EditText) solo.getView(R.id.search_info),"");

        solo.clickOnButton("edit profile");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"passward");

        solo.clickOnButton("Save");
    }

    public void testProviderMapActivity() {
        logIn();

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);

        solo.clickOnButton("Show List");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        //wait for update
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testProviderBidHistoryActivity() {
        logIn();

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("Bid History");

        solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

    }

    private void logIn(){
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Activity Activity = solo.getCurrentActivity();

        if (!Activity.getClass().equals(UserCharacterActivity.class) ){
            signUp();
        }
    }

    private void signUp(){
        while(!solo.getCurrentActivity().getClass().equals(LogInActivity.class)){
            solo.goBack();
        }

        solo.clickOnButton("Sign Up");

        solo.enterText((EditText) solo.getView(R.id.signup_name), "wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_phone), "1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email), "123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password), "passward");

        solo.clickOnButton("Log In");
    }

    private void postTask(){
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Post New Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterPostTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.c_task_name),"Go to Hub");

        solo.enterText((EditText) solo.getView(R.id.c_task_detail),"two people");

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"ETLC");

        solo.enterText((EditText) solo.getView(R.id.c_task_idealprice),"12.34");

        solo.clickOnButton("submit");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickInList(-1);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskRequestActivity.class);

    }

    private void bidOnTask(){

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.p_task_mybid));

        solo.enterText((EditText) solo.getView(R.id.p_task_mybid),"3.5");

        solo.clickOnButton("Bid");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //solo.clickInList(0);

        //solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

    }

    private void assignTask(){

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickLongOnScreen(3,3);

        solo.clickOnButton("view bidden task");

        solo.clickInList(-1);

        solo.clickOnButton("submit");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskAssignedActivity.class);
    }

    private void payTask(){
        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("view assigned task");

        solo.clickInList(-1);

        solo.clickOnButton("Pay task");

        solo.assertCurrentActivity("Wrong Activity", RequesterDoneListActivity.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskDoneActivity.class);

    }

    private void deleteDataBase() {
        TaskController.deleteAllTasks deleteAllTasks = new TaskController.deleteAllTasks();
        deleteAllTasks.execute("");


        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");


        BidController.deleteAllBidCounters deleteAllBidCounters = new BidController.deleteAllBidCounters();
        deleteAllBidCounters.execute("");
    }
}
