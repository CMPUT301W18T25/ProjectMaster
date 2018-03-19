package project301.ActivityTest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.SignUpActivity;
import project301.allUserActivity.UserCharacterActivity;
import project301.providerActivity.ProviderBidHistoryActivity;
import project301.providerActivity.ProviderEditInfoActivity;
import project301.providerActivity.ProviderMainActivity;
import project301.providerActivity.ProviderMapActivity;
import project301.providerActivity.ProviderTaskBidActivity;

import com.robotium.solo.Solo;

/**
 * Test for all provider activities. Some of test based on requester activity test and sign up activity test.
 * @classname : ProviderActivityTest
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * Test for all provider activities. Some of test based on requester activity test and sign up activity test.
 */

public class ProviderActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public ProviderActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testProviderMainActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        //solo.goBack();

        solo.enterText((EditText) solo.getView(R.id.search_info),"some test");

        solo.clickOnButton("Search");

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("Bid History");

        solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("edit profile");

        solo.assertCurrentActivity("Wrong Activity", ProviderEditInfoActivity.class);

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

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

        solo.clearEditText((EditText) solo.getView(R.id.edit_name));

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong22");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"wdong2@ualberta.ca");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"1234567890");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"abc");

        solo.clickOnButton("Save");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.search_info),"");

        solo.goBack();

        solo.goBack();

        solo.enterText((EditText) solo.getView(R.id.search_info),"");

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

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong22");

        solo.clearEditText((EditText) solo.getView(R.id.login_password));

        solo.enterText((EditText) solo.getView(R.id.login_password),"abc");

        solo.clickOnButton("Log In");

        solo.clickOnButton("provider");

        solo.enterText((EditText) solo.getView(R.id.search_info),"");

        solo.clickOnButton("edit profile");

        solo.clearEditText((EditText) solo.getView(R.id.edit_name));

        solo.enterText((EditText) solo.getView(R.id.edit_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.edit_email),"");

        solo.enterText((EditText) solo.getView(R.id.edit_phone),"");

        solo.clearEditText((EditText) solo.getView(R.id.edit_passward));

        solo.enterText((EditText) solo.getView(R.id.edit_passward),"passward");

        solo.clickOnButton("Save");
    }

    public void testProviderMapActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("View On Map");

        solo.assertCurrentActivity("Wrong Activity", ProviderMapActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);
    }

    public void testProviderBidHistoryActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickOnButton("Bid History");

        solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

    }

    public void testProviderBidActivity() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

        solo.clickOnButton("Cancel");

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);

        solo.enterText((EditText) solo.getView(R.id.p_task_mybid),"0");

        solo.clickOnButton("Back");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.goBack();

        solo.clickOnButton("Bid");

        solo.assertCurrentActivity("Wrong Activity", ProviderBidHistoryActivity.class);

        solo.clickInList(0);

        solo.assertCurrentActivity("Wrong Activity", ProviderTaskBidActivity.class);
    }
}
