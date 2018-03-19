package project301.ActivityTest;

/**
 * @classname : SignUpActivityTest
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
import project301.allUserActivity.SignUpActivity;
import project301.allUserActivity.UserCharacterActivity;
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

        solo.enterText((EditText) solo.getView(R.id.signup_name),"wdong2");

        solo.enterText((EditText) solo.getView(R.id.signup_password),"passward");

        solo.clickOnButton("Log In");
    }


}