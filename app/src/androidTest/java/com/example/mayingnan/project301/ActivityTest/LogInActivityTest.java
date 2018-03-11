package com.example.mayingnan.project301.ActivityTest;

/**
 * Created by wdong2 on 3/8/18.
 */

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.UserCharacterActivity;
import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.allUserActivity.SignUpActivity;
import com.example.mayingnan.project301.controller.UserListController;
import com.robotium.solo.Solo;

public class LogInActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public LogInActivityTest() {
        super(LogInActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testSignUp() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }

    public void testLogInActivityTransfer() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        UserListController ULC = new UserListController();

        String invalidUserName = "IUN";

        assertFalse(ULC.testFalse(invalidUserName));

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        String validUserName = "VUN";

        assertTrue(ULC.testTrue(validUserName));

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);
    }

    public void testLogInUserInvalidation() {
        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        UserListController ULC = new UserListController();

        /**
         * need to check controller
         */

        String invalidUserName = "IUN";

        assertFalse(ULC.checkLogInInfo(invalidUserName));

        String validUserName = "wdong2";

        assertTrue(ULC.checkLogInInfo(validUserName));
    }
}