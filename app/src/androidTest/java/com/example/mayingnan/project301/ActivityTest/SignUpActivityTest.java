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
import com.robotium.solo.Solo;

public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public SignUpActivityTest() {
        super(SignUpActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testCheckValidationButton() {
        SignUpActivity activity = (SignUpActivity)solo.getCurrentActivity();

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

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

    }


}