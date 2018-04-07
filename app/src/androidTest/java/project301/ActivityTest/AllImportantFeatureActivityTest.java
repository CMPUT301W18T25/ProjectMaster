package project301.ActivityTest;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import project301.R;
import project301.allUserActivity.LogInActivity;
import project301.allUserActivity.SignUpActivity;

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
    ProviderActivityTest ProviderActivityTest = new ProviderActivityTest();
    RequesterActivityTest RequesterActivityTest = new RequesterActivityTest();
    SignUpActivityTest SignUpActivityTest = new SignUpActivityTest();

    public AllImportantFeatureActivityTest() {
        super(LogInActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public void testAddTaskActivity() {

        signUp();

        RequesterActivityTest.testPostTaskActivity();

        ProviderActivityTest.testProviderBidActivity();

        RequesterActivityTest.testAssignActivity();

    }

    public void signUp(){
        solo.clickOnButton("Sign Up");

        solo.assertCurrentActivity("Wrong Activity", SignUpActivity.class);

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_phone),"1234567890");

        solo.enterText((EditText) solo.getView(R.id.signup_email),"123@123.com");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");
    }

    public void postTask(){

    }

    public void bidOnTask(){

    }

    public void assignTask(){

    }

    public void payTask(){

    }


}

