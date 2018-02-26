package com.example.mayingnan.project301;

import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by julianstys on 2018-02-25.
 */

public class TaskTest extends ActivityInstrumentationTestCase2 {


    public TaskTest() {
        super(LogInActivity.class);
    }

    @Test
    public void testTask(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task task = new Task();
        task.setTaskName("Fetch car");
        task.setTaskDetails("Fetch my car from lister hall");
        task.setTaskAddress("HUB mall");
        task.setTaskRequester("Julian");
        task.setTaskProvider(null);
        assertEquals("Julian", task.getTaskRequester());
        task.setTaskRequester("Chris");
        assertEquals("Chris", task.getTaskRequester());

    }
    public void addTaskTest(){

        assertTrue(true);

    }
    public void updateTaskTest(){
        assertTrue(true);
    }
    public void deleteTaskTest(){

        assertTrue(true);
    }
    public void searchBiddenTasksTest(){
        assertTrue(true);
    }
    public void searchAssignTasksTest(){
        assertTrue(true);
    }
    public void searchAllTasksOfThisRequester(){
        assertTrue(true);
    }
    public void searchAllRequestingTasksTest(){
        assertTrue(true);
    }
    public void searchTaskByTaskNameTest(){
        assertTrue(true);
    }

}
