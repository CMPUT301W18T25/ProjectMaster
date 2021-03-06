package project301.allUserActivity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import project301.R;
import project301.User;
import project301.controller.BidController;
import project301.controller.UserController;
import project301.utilities.TaskUtil;

/**
 * This activity is for a new user to sign up an account.
 */


public class SignUpActivity extends AppCompatActivity {

    private final SignUpActivity activity = this;

    private EditText usernameText;
    private EditText emailText;
    private EditText mobileText;
    private EditText passwardText;
    private Button checkValidationButton;
    private UserController userListControl;
    private User newUser;
    private UserController userController;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Button checkValidationButton = (Button) findViewById(R.id.check_validation);
        usernameText = (EditText) findViewById(R.id.signup_name);
        emailText = (EditText) findViewById(R.id.signup_email);
        mobileText = (EditText) findViewById(R.id.signup_phone);
        passwardText = (EditText) findViewById(R.id.signup_password);
        this.context = getApplicationContext();

        checkValidationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (check_namelength(usernameText.getText().toString())) {
                //setResult(RESULT_OK);
                String enterName = usernameText.getText().toString();
                String enterEmail = emailText.getText().toString();
                String enterPhone = mobileText.getText().toString();
                String enterPassward = passwardText.getText().toString();
                //deal with those variables
                newUser = new User();
                newUser.setUserName(enterName);
                if (TaskUtil.validate_phone(enterPhone)){
                    newUser.setUserPhone(enterPhone);
                }else{
                    //error message
                    Toast toast = Toast.makeText(context, "Invalid Phone Number! Please Try Again!", Toast.LENGTH_LONG);
                    TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                    return;
                }
                if (TaskUtil.validate_email(enterEmail)){
                    newUser.setUserEmail(enterEmail);
                }else{
                    //error message
                    Toast toast = Toast.makeText(context, "Invalid Email Address! Please Try Again!", Toast.LENGTH_LONG);
                    TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                    return;
                }

                newUser.setUserPassword(enterPassward);
                newUser.setId(enterName);
                Log.i("Name: ", newUser.getUserName());

                UserController uc = new UserController();
                String newUserId;
                newUserId = uc.addUserAndCheck(newUser);


                if (newUserId != null) {
                    newUser.setId(newUserId);
                    Log.i("Success sign up:", enterName);
                    Intent intent = new Intent(activity, UserCharacterActivity.class);
                    //initializes a bidCounter for this new user.
                    BidController bidController = new BidController();
                    bidController.buildBidCounterOfThisRequester(newUserId);

                    //deliver userName
                    intent.putExtra("userId", newUserId);
                    Log.i("newUserId", newUserId);

                    startActivity(intent);
                } else {
                    //error message
                    Toast toast = Toast.makeText(context, "User Name Already Exist!", Toast.LENGTH_LONG);
                    TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                    v1.setTextColor(Color.RED);
                    v1.setTextSize(20);
                    v1.setGravity(Gravity.CENTER);
                    toast.show();
                }

            } else {
                //error message
                Toast toast = Toast.makeText(context, "The maximum length of username is 8", Toast.LENGTH_LONG);
                TextView v1 = (TextView) toast.getView().findViewById(android.R.id.message);
                v1.setTextColor(Color.RED);
                v1.setTextSize(20);
                v1.setGravity(Gravity.CENTER);
                toast.show();
            }
            }
        });
    }

    private boolean check_namelength(String name)
    {
        return name.length () < 9;
    }
}