package com.example.mayingnan.project301.ActivityTest;

/*
 * Created by wdong2 on 3/8/18.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.example.mayingnan.project301.R;
import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.allUserActivity.SignUpActivity;
import com.example.mayingnan.project301.allUserActivity.UserCharacterActivity;
import com.example.mayingnan.project301.controller.UserListController;
import com.robotium.solo.Solo;

public class LogInActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public LogInActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSignUp() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.goBack();

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clickOnButton("Sign Up");

        //solo.goBack();

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");

        //solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);
    }

    public void testLogInActivityTransfer() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.enterText((EditText) solo.getView(R.id.login_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.login_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }

}
