
package project301.ActivityTest;

/**
 * @classname : RequesterActivityTest
 * @class Detail : User in this activity can choose to be a provider or requestor
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.UserCharacterActivity;

import project301.requesterActivity.RequesterEditInfoActivity;
import project301.requesterActivity.RequesterEditListActivity;
import project301.requesterActivity.RequesterEditTaskActivity;
import project301.requesterActivity.RequesterMainActivity;
import project301.requesterActivity.RequesterMapActivity;
import project301.requesterActivity.RequesterPostTaskActivity;
import project301.requesterActivity.RequesterViewTaskActivity;
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

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", RequesterViewTaskActivity.class);

        solo.clickOnButton("Edit  Task  Information");

        solo.assertCurrentActivity("Wrong Activity", RequesterEditTaskActivity.class);

        //solo.getEditText(solo.getView(R.id.c_view_name));

        //solo.goBack();

        //solo.clickOnButton("Cancel");

        //solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);
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

        solo.assertCurrentActivity("Wrong Activity", RequesterEditListActivity.class);

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

}
