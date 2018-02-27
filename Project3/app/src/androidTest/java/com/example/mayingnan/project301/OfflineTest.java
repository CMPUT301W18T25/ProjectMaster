package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import com.example.mayingnan.project301.controller.FileSystemController;
import com.example.mayingnan.project301.controller.TaskController;

import java.util.ArrayList;

/**
 * Created by julianstys on 2018-02-25.
 */


public class OfflineTest extends ActivityInstrumentationTestCase2 {


    public OfflineTest() {
        super(LogInActivity.class);
    }
    public void testOfflineAddTask(){
        Task offlineTask = new Task();
        FileSystemController fc = new FileSystemController();
        fc.saveToFile(offlineTask);
        Task[] tasks = fc.loadFromFile();
        assertEquals(tasks[0],offlineTask);


    }

    public void testOfflineEditTask(){
        //create a task online
        FileSystemController fc = new FileSystemController();
        Task newTask = new Task();
        TaskController tc= new TaskController();
        newTask.setTaskName("goToSouthgate");
        tc.addTask(newTask);

        //if go offline, save to file
        newTask.setTaskName("goToCalgary");
        fc.saveToFile(newTask);
        //when go online, load from file and update elasticsearch database
        Task[] tasks = fc.loadFromFile();
        Task renewedTask = tasks[0];
        tc.requesterUpdateTask(renewedTask);

        assertEquals(tc.searchTaskByTaskName("goToCalgary"),newTask);

    }
}
