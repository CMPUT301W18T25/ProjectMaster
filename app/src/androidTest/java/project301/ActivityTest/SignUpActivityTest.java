package project301.ActivityTest;


import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import project301.R;
import project301.allUserActivity.SignUpActivity;
import com.robotium.solo.Solo;

/**
 * Test for sign up and sign up an user for further testing.
 * @classname : SignUpActivityTest
 * @Date :   18/03/2018
 * @author : Wang Dong
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class SignUpActivityTest extends ActivityInstrumentationTestCase2 {

    private Solo solo;

    public SignUpActivityTest() {
        super(SignUpActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testSignUp1() {
        //SignUpActivity activity = (SignUpActivity)solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email),"123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");
    }

    public void testSignUp2() {
        //SignUpActivity activity = (SignUpActivity)solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong22");

        solo.enterText((EditText) solo.getView(R.id.signup_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email),"123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");
    }

    public void testSignUp3() {
        //SignUpActivity activity = (SignUpActivity)solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong222");

        solo.enterText((EditText) solo.getView(R.id.signup_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email),"123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");
    }

    public void testInvalid1() {
        //SignUpActivity activity = (SignUpActivity)solo.getCurrentActivity();

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email),"123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"pa");

        solo.clickOnButton("Log In");
    }


}
