package project301;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import project301.allUserActivity.LogInActivity;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

/**
 * @classname : OfflineTest
 * @class Detail : This is the test class for offline handling methods
 *
 * @Date :   18/03/2018
 * @author : Yue Ma
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
