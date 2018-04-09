
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
import project301.providerActivity.ProviderMainActivity;
import project301.providerActivity.ProviderTaskBidActivity;
import project301.requesterActivity.RequesterAllListActivity;
import project301.requesterActivity.RequesterAssignedListActivity;
import project301.requesterActivity.RequesterChooseBidActivity;
import project301.requesterActivity.RequesterDoneListActivity;
import project301.requesterActivity.RequesterEditInfoActivity;
import project301.requesterActivity.RequesterEditTaskActivity;
import project301.requesterActivity.RequesterMainActivity;
import project301.requesterActivity.RequesterMapActivity;
import project301.requesterActivity.RequesterPostTaskActivity;
import project301.requesterActivity.RequesterViewTaskAssignedActivity;
import project301.requesterActivity.RequesterViewTaskBiddenActivity;
import project301.requesterActivity.RequesterViewTaskDoneActivity;
import project301.requesterActivity.RequesterViewTaskRequestActivity;

import com.robotium.solo.Solo;

/**
 * Test for all requester activities. Some of test based on provider activity test and sign up activity test.
 * @classname : RequesterActivityTest
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class RequesterActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    AllImportantFeatureActivityTest AllImportantFeatureActivityTest = new AllImportantFeatureActivityTest();

    public RequesterActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testRequesterMainActivity() {
        logIn();

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Post New Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterPostTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.c_task_name),"Go to Hub");

        solo.enterText((EditText) solo.getView(R.id.c_task_detail),"two people");

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"ETLC");

        solo.enterText((EditText) solo.getView(R.id.c_task_idealprice),"12.34");

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("view all task");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickOnButton("Main menu");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);
    }

    public void testRequesterPostAndDeleteTaskActivity() {

        logIn();

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Post New Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterPostTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.c_task_name),"Go to Hub");

        solo.enterText((EditText) solo.getView(R.id.c_task_detail),"two people");

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"9107 116St NW");

        solo.enterText((EditText) solo.getView(R.id.c_task_idealprice),"12.34");

        solo.clickOnButton("submit");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickInList(-1);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskRequestActivity.class);

        solo.clickOnButton("Delete Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);
    }

    public void testRequesterMapActivity() {
        logIn();

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("view all task");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", RequesterMapActivity.class);
    }

    public void testPostTaskActivity() {
        deleteDataBase();

        logIn();

        solo.clickOnButton("Requester");

        postTask();
    }

    public void testAssignActivity(){
        deleteDataBase();

        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        postTask();

        solo.clickOnButton("Show List");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickOnButton("Main menu");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Log out");

        logIn();

        solo.clickOnButton("provider");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.enterText((EditText) solo.getView(R.id.search_info),"Hub");

        solo.clickOnButton("Search");

        bidOnTask();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickOnButton("back");

        solo.clickOnButton("       log out     ");

        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assignTask();
    }

    public void testEditTaskActivity(){
        deleteDataBase();

        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        postTask();

        solo.clickOnButton("Edit Task ");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_name));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_detail));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_destination));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_idealprice));

        solo.enterText((EditText) solo.getView(R.id.c_edit_name), "Go to ETLC");

        solo.enterText((EditText) solo.getView(R.id.c_edit_detail), "two people");

        solo.enterText((EditText) solo.getView(R.id.c_edit_destination), "Hub");

        solo.enterText((EditText) solo.getView(R.id.c_edit_idealprice), "10.21");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickInList(-1);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"9107 116St NW");

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

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

    }

    private void assignTask(){

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        while(!solo.getCurrentActivity().getClass().equals(UserCharacterActivity.class)){
            solo.goBack();
        }

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.clickOnButton("view bidden task");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskBiddenActivity.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterChooseBidActivity.class);

        solo.clickOnButton("submit");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskAssignedActivity.class);
    }

    private void deleteDataBase(){
        TaskController.deleteAllTasks deleteAllTasks = new TaskController.deleteAllTasks();
        deleteAllTasks.execute("");


        UserController.deleteAllUsers deleteAllUsers = new UserController.deleteAllUsers();
        deleteAllUsers.execute("");


        BidController.deleteAllBidCounters deleteAllBidCounters = new BidController.deleteAllBidCounters();
        deleteAllBidCounters.execute("");
    }
}
