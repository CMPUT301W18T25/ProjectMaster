
package project301.ActivityTest;


import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.UserCharacterActivity;

import project301.requesterActivity.RequesterAllListActivity;
import project301.requesterActivity.RequesterEditInfoActivity;
import project301.requesterActivity.RequesterEditTaskActivity;
import project301.requesterActivity.RequesterMainActivity;
import project301.requesterActivity.RequesterMapActivity;
import project301.requesterActivity.RequesterPostTaskActivity;
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

    public RequesterActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testRequesterMainActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

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

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("View And Edit");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickOnButton("Main menu");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);
    }

    public void testRequesterPostAndDeleteTaskActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

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

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickInList(-1);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskRequestActivity.class);

        solo.clickOnButton("Edit  Task  Information");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_name));

        assertTrue(solo.waitForText("GO!"));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_detail));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_destination));

        solo.clearEditText((EditText) solo.getView(R.id.c_edit_idealprice));

        solo.enterText((EditText) solo.getView(R.id.c_edit_name),"GO!_test");

        solo.enterText((EditText) solo.getView(R.id.c_edit_detail),"two people_test");

        solo.enterText((EditText) solo.getView(R.id.c_edit_destination),"NorthGate_test");

        solo.enterText((EditText) solo.getView(R.id.c_edit_idealprice),"12.34567");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickInList(-1);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskRequestActivity.class);

        solo.clickOnButton("Delete Task");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);
    }

    public void testRequesterEditListActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("View And Edit");

        solo.assertCurrentActivity("Wrong Activity", RequesterAllListActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", RequesterMapActivity.class);

        solo.goBack();

        solo.clickOnButton("Main menu");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
    }

    public void testRequesterEditInfoActivity() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.clickOnButton("Edit Profile");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditInfoActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.edit_name));

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2_test");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abcd");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.goBack();

        solo.goBack();

        solo.goBack();

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.login_name));

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clearEditText((EditText) solo.getView(R.id.login_name));

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2_test");

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"abcd");

        solo.clickOnButton("Log In");

        solo.clickOnButton("Requester");

        solo.clickOnButton("Edit Profile");

        solo.clearEditText((EditText) solo.getView(R.id.edit_name));

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"passward");

        solo.clickOnButton("Save");
    }

}
