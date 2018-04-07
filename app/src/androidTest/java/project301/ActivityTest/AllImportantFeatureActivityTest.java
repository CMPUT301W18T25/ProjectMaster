package project301.ActivityTest;

import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

import project301.allUserActivity.LogInActivity;

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

        SignUpActivityTest.testSignUp1();

        RequesterActivityTest.testPostTaskActivity();

        ProviderActivityTest.testProviderBidActivity();

        RequesterActivityTest.testAssignActivity();

    }

}

