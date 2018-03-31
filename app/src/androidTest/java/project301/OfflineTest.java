package project301;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;

import project301.allUserActivity.LogInActivity;
import project301.controller.FileSystemController;
import project301.controller.OfflineController;
import project301.controller.TaskController;

import android.support.test.InstrumentationRegistry;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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

/**
 * This is the test class for offline handling methods.
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
        Log.i("get context", "a");
    }
    public void testOfflineAdd(){
        //assume no internet now
        Task new_task = new Task();
        new_task.setTaskName("go to New York");
        new_task.setTaskRequester("jason");
        FileSystemController fileSystemController = new FileSystemController();
        Log.i("offlineAdd","test");
        String uniqueID = UUID.randomUUID().toString();
        new_task.setId(uniqueID);
        fileSystemController.saveToFile(new_task,"offlineAdd",context);
        //assume reconnect
        OfflineController offlineController = new OfflineController();
        offlineController.tryToExecuteOfflineTasks(context);
        TaskController.searchAllTasksOfThisRequester searchAllTasksOfThisRequester = new TaskController.searchAllTasksOfThisRequester();
        searchAllTasksOfThisRequester.execute("jason");
        ArrayList<Task> taskList = new ArrayList<>();
        try {
            taskList = searchAllTasksOfThisRequester.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        boolean found = false;
        for(Task task: taskList){
            if(task.getTaskName().equals(new_task.getTaskName())){
                found = true;
            }
        }
        assertEquals(found,true);



    }


}
