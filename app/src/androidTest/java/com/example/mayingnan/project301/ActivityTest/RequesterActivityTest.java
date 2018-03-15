
package com.example.mayingnan.project301.ActivityTest;

/**
 * Created by wdong2 on 3/8/18.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.allUserActivity.SignUpActivity;
import com.example.mayingnan.project301.allUserActivity.UserCharacterActivity;
import com.example.mayingnan.project301.providerActivity.ProviderBidHistoryActivity;
import com.example.mayingnan.project301.providerActivity.ProviderMainActivity;
import com.example.mayingnan.project301.providerActivity.ProviderMapActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterEditInfoActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterEditListActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterMainActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterMapActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterPostTaskActivity;
import com.robotium.solo.Solo;

public class RequesterActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public RequesterActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testRequesterMainActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
/**
 solo.clickOnButton("Log In");
 solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
 solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");
 */

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Post New Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterPostTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.c_task_name),"GO!");

        solo.enterText((EditText) solo.getView(R.id.c_task_detail),"two people");

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"NorthGate");

        solo.enterText((EditText) solo.getView(R.id.c_task_idealprice),"12.34");

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("View And Edit");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditListActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
    }

    public void testRequesterPostTaskActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
/**
 solo.clickOnButton("Log In");
 solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
 solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");
 */

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Post New Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterPostTaskActivity.class);

        solo.enterText((EditText) solo.getView(R.id.c_task_name),"GO!");

        solo.enterText((EditText) solo.getView(R.id.c_task_detail),"two people");

        solo.enterText((EditText) solo.getView(R.id.c_task_location),"NorthGate");

        solo.enterText((EditText) solo.getView(R.id.c_task_idealprice),"12.34");

        solo.clickOnButton("submit");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditListActivity.class);

        solo.goBack();

        solo.clickOnButton("Cancel");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
    }

    public void testRequesterEditListActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
/**
 solo.clickOnButton("Log In");
 solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
 solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");
 */

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("View And Edit");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditListActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", RequesterMapActivity.class);

        solo.goBack();

        solo.clickOnButton("Main menu");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
    }

    public void testRequesterEditInfoActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
/**
 solo.clickOnButton("Log In");
 solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
 solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");
 */

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
    }


    /**
 public void testProviderMainActivity() {
 RequesterMainActivity activity = (RequesterMainActivity)solo.getCurrentActivity();
 solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
 //solo.goBack();
 solo.clickOnButton("search");
 solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
 //solo.goBack();
 //solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);
 solo.clickOnButton("View On Map");
 solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);
 solo.goBack();
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 solo.clickOnButton("Bid History");
 solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);
 solo.goBack();
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 }
 public void testProviderMapActivity() {
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 //solo.goBack();
 //solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);
 solo.clickOnButton("View On Map");
 solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);
 solo.goBack();
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 }
 public void testProviderBidHistoryActivity() {
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 solo.clickOnButton("Bid History");
 solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);
 solo.clickOnButton("Back");
 solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
 }
*/
}