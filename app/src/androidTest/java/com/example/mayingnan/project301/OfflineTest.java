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
 * Created by julianstys on 2018-02-25.
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

    public void testSaveSentTask(){
        String instruction = "sent";
        Task test_task = new Task();

        // init test task info, all info should be tested
        test_task.setTaskDetails("Details");
        test_task.setTaskName("Test");
        test_task.setTaskProvider("A donkey");
        test_task.setTaskRequester("A snake");
        FileSystemController fc= new FileSystemController();


        TaskController.addTask addTaskCtl = new TaskController.addTask();
        addTaskCtl.execute(test_task);

        AsyncTask.Status taskStatus;
        do {
            taskStatus = addTaskCtl.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fc.saveToFile(test_task,instruction,context);

        ArrayList<Task> taskList;
        taskList =  fc.loadSentTasksFromFile(context);
        boolean addSucess = false;
        for (Task task: taskList) {
            if(task.getTaskName().equals("Test")){

                addSucess = true;

            }
        }
        assertEquals(addSucess,true);



    }





}
