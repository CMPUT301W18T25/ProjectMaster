package com.example.mayingnan.project301.ActivityTest;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;


/*
 * Created by wdong2 on 3/7/18.
 */

public class MainActivityTest extends ActivityInstrumentationTestCase2 {

    public MainActivityTest() {
        super(LogInActivity.class);
    }

}

/* the activity testing code from lonelyTwitter
 public class LonelyTwitterActivityTest extends ActivityInstrumentationTestCase2 {
 private Solo solo;
 public LonelyTwitterActivityTest() {
 super(ca.ualberta.cs.lonelytwitter.LonelyTwitterActivity.class);
 }
 public void setUp() throws Exception
 {
 solo = new Solo(getInstrumentation());
 }
 public void testStart() throws Exception {
 Activity activity = getActivity();
 }
 public  void testTweet()
 {
 solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
 solo.enterText((EditText) solo.getView(R.id.body),"Test Tweet!");
 solo.clickOnButton("Save");
 solo.clearEditText((EditText) solo.getView(R.id.body));
 assertTrue(solo.waitForText("Test Tweet!"));
 solo.clickOnButton("Clear");
 assertFalse(solo.waitForText("Test Tweet!",1,3000));
 }
 public void testClickTweetList()
 {
 LonelyTwitterActivity activity = (LonelyTwitterActivity)solo.getCurrentActivity();
 solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
 solo.clickOnButton("Clear");
 solo.enterText((EditText) solo.getView(R.id.body),"Test Tweet!");
 solo.clickOnButton("Save");
 solo.clearEditText((EditText) solo.getView(R.id.body));
 assertTrue(solo.waitForText("Test Tweet!"));
 final ListView oldTweetList = activity.egtOldTweetsList();
 Tweet tweet = (Tweet) oldTweetList.getItemAtPosition(0);
 assertEquals("Test Tweet!",tweet.getMessage());
 solo.clickInList(0);
 solo.assertCurrentActivity("Wrong Activity", EditTweetActivity.class);
 solo.goBack();
 solo.assertCurrentActivity("Wrong Activity", LonelyTwitterActivity.class);
 }
 @Override
 public void tearDown() throws Exception
 {
 solo.finishOpenedActivities();
 }
 }

 */
