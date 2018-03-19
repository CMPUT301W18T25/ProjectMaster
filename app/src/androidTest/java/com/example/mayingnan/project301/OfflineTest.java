package com.example.mayingnan.project301;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.controller.FileSystemController;
import com.example.mayingnan.project301.controller.TaskController;
import com.robotium.solo.Solo;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @classname : OfflineTest
 * @class Detail :
 *
 * @Date :   18/03/2018
 * @author :
 * @author :
 * @author :
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


public class OfflineTest extends ActivityInstrumentationTestCase2{
    public OfflineTest() {
        super(LogInActivity.class);
    }
    private Context context;
    @Override
    public void setUp() {
        // In case you need the context in your test
        context = InstrumentationRegistry.getContext();
        Log.i("get context","a");
    }


}
