package project301.ActivityTest;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;

import com.robotium.solo.Solo;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.SignUpActivity;
import project301.allUserActivity.UserCharacterActivity;
import project301.providerActivity.ProviderBidHistoryActivity;
import project301.providerActivity.ProviderMainActivity;
import project301.providerActivity.ProviderTaskBidActivity;
import project301.requesterActivity.RequesterAllListActivity;
import project301.requesterActivity.RequesterDoneListActivity;
import project301.requesterActivity.RequesterMainActivity;
import project301.requesterActivity.RequesterPostTaskActivity;
import project301.requesterActivity.RequesterViewTaskAssignedActivity;
import project301.requesterActivity.RequesterViewTaskDoneActivity;
import project301.requesterActivity.RequesterViewTaskRequestActivity;

/**
 * All important feature test class
 * @classname : MainActivityTest
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */



public class AllImportantFeatureActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public AllImportantFeatureActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testPostTaskActivity() {
        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        postTask();
    }

    public void testBidTaskActivity(){
        logIn();

        solo.clickOnButton("provider");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.enterText((EditText) solo.getView(R.id.search_info),"wdong");

        solo.clickOnButton("Search");

        bidOnTask();
    }

    public void testAssignTaskActivity(){
        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assignTask();
    }

    public void testPayTaskActivity(){
        logIn();

        solo.clickOnButton("Requester");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        payTask();
    }

    public void logIn(){
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Activity Activity = solo.getCurrentActivity();

        if ( Activity.getClass().equals(UserCharacterActivity.class) ){}else {
            signUp();
        }
    }

    public void signUp(){
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

    public void postTask(){
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

    public void bidOnTask(){

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.p_task_mybid));

        solo.enterText((EditText) solo.getView(R.id.p_task_mybid),"3.5");

        solo.clickOnButton("Bid");

        while (true) {
            if (solo.getCurrentActivity().getClass().equals(ProviderBidHistoryActivity.class)) {
                break;
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

    }

    public void assignTask(){

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

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

    public void payTask(){
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


}

