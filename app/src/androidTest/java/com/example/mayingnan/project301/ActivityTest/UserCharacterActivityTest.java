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
import com.example.mayingnan.project301.providerActivity.ProviderMainActivity;
import com.example.mayingnan.project301.requesterActivity.RequesterMainActivity;
import com.robotium.solo.Solo;

public class UserCharacterActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public UserCharacterActivityTest()  {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testStart() throws Exception {
        Activity activity = getActivity();
    }

    public void testProviderButton() {

        solo.assertCurrentActivity("Wrong Activity", LogInActivity.class);

        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
/*
 solo.clickOnButton("Log In");
 solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);
 solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");
 */

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("provider");

        solo.assertCurrentActivity("Wrong Activity", ProviderMainActivity.class);

        solo.enterText((EditText) solo.getView(R.id.search_info),"some test");

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);

        solo.clickOnButton("Requester");

        solo.assertCurrentActivity("Wrong Activity", RequesterMainActivity.class);

        solo.goBack();

        solo.assertCurrentActivity("Wrong Activity", UserCharacterActivity.class);
    }

}
