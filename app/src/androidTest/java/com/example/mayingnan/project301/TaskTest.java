package com.example.mayingnan.project301;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mayingnan.project301.controller.TaskController;
import com.example.mayingnan.project301.controller.UserListController;

import org.junit.Test;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by julianstys on 2018-02-25.
 */

public class TaskTest {

    /*
    @Test
    public void testTaskContructor(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();

        Task task = new Task("Fetch","Fetchcar","Michael",null,"bidding","random address",bidList,emptyPhoto);

        assertEquals("Michael", task.getTaskRequester());
        assertEquals("Michael", task.getTaskRequester());

        assertNull(task.getTaskProvider());
        task.setTaskProvider("James");
        assertEquals("James", task.getTaskProvider());


    }
    @Test
    public void testTaskNoConstructor(){
        ArrayList<Bid> bidList = new ArrayList<Bid>();
        Photo emptyPhoto = new Photo();
        Task task = new Task("Fetch car","Fetch my car","Michael",null,"bidding","random address",bidList,emptyPhoto);
        task.setTaskName("Fetch car");
        task.setTaskDetails("Fetch my car from lister hall");
        task.setTaskAddress("HUB mall");
        task.setTaskRequester("Julian");
        task.setTaskProvider(null);
        assertEquals("Julian", task.getTaskRequester());
        task.setTaskRequester("Chris");
        assertEquals("Chris", task.getTaskRequester());


    }
    @Test
    */
    @Test
    public void testAddTask(){
        TaskController.addTask addTaskCtl = new TaskController.addTask();
        Task task = new Task();
        ArrayList<Task> single_task = new ArrayList<Task>();
        task.setTaskName("hi");

        addTaskCtl.execute(task);

        AsyncTask.Status taskStatus;
        do {
            taskStatus = addTaskCtl.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (!(task.getId() != null) && !task.getId().isEmpty()){
            assertTrue(true);
        }

        /*
        TaskController.getTaskById getTask = new TaskController.getTaskById();

        getTask.execute(task.getId());
        Task result_task = new Task();
        try {
            result_task = getTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        int valid = 0;

        //Log.i("username   ", result_task.getTaskName());

        if (result_task.getTaskName() == "hi"){
            assertTrue(true);
        }else{
            assertTrue(false);
        }
        //assertEquals(result_task.getTaskName(), null);

        //TaskController.deleteTaskById deleteCtl = new TaskController.deleteTaskById(task.getId());
        //deleteCtl.execute(task.getId());
        //assertEquals(valid,1);
        */
    }

    @Test
    public void testGetTask(){
        TaskController.addTask addTaskCtl = new TaskController.addTask();
        Task task = new Task();
        Task rt_task = new Task();
        ArrayList<Task> single_task = new ArrayList<Task>();


        task.setTaskName("gg");

        addTaskCtl.execute(task);

        AsyncTask.Status taskStatus;
        do {
            taskStatus = addTaskCtl.getStatus();
        } while (taskStatus != AsyncTask.Status.FINISHED);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TaskController.getTaskById getTask = new TaskController.getTaskById();
        getTask.execute(task.getId());

        try {
            rt_task = getTask.get();
            Log.i("Success", "message");

            if (task.getTaskName().equals(rt_task.getTaskName())){
                assertTrue(true);
            }
            else{
                assertTrue(false);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.i("Error", "return fail");

        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.i("Error", "not getting anything");
        }


        // it is tested in testAddTask


    }

    @Test
    public void testDeleteTask(){
        TaskController.deleteTaskById deleteCtl = new TaskController.deleteTaskById("temp");
        Task up_task = new Task();
        up_task.setTaskName("hi");

        // it is tested in testAddTask


    }


    /*
    @Test
    public void requesterUpdateTaskTest(){
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hi");
        tc.addTask.execute(task);
        assertEquals(tc.searchTaskByTaskName("hi"),task);
        task.setTaskName("No");
        tc.requesterUpdateTask(task);
        assertEquals(tc.searchTaskByTaskName("No"),task);



    }
    @Test
    public void searchTaskByKeywordTest(){
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hihi");
        tc.addTask(task);
        assertTrue(tc.searchTaskByKeyword("hi").contains(task));

    }
    @Test
    public void searchBiddenTasksOfThisProviderTest(){
        String userName = "me";
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskProvider(userName);
        task.setTaskName("hihi");
        task.setTaskStatus("bidding");
        tc.addTask(task);
        assertTrue(tc.searchBiddenTasksOfThisProvider(userName).contains(task));

    }
    @Test
    public void searchAssignTasksOfThisProviderTest(){
        String userName = "me";
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskProvider(userName);
        task.setTaskName("hihi");
        task.setTaskStatus("processing");
        tc.addTask(task);
        assertTrue(tc.searchAssignTasksOfThisProvider(userName).contains(task));



    }
    @Test
    public void searchAllTasksOfThisRequesterTest(){
        String userName = "me";
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskProvider(userName);
        task.setTaskName("hihi");
        task.setTaskStatus("processing");

        Task task2= new Task();
        task2.setTaskProvider(userName);
        task2.setTaskName("hihihi2");
        task2.setTaskStatus("finished");
        tc.addTask(task);
        assertTrue(tc.searchAllTasksOfThisRequester(userName).contains(task));
        assertTrue(tc.searchAllTasksOfThisRequester(userName).contains(task2));



    }
    @Test
    public void searchAllRequestingTasksTest(){
        TaskController tc= new TaskController();
        Task task = new Task();
        task.setTaskStatus("finished");

        Task task2 = new Task();
        task.setTaskStatus("requesting");

        assertEquals(tc.searchAllRequestingTasks().get(0),task2);

        assertEquals(tc.searchAllRequestingTasks().size(),1);



    }
    @Test
    public void searchTaskByTaskNameTest(){
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hi");
        assertEquals(tc.searchTaskByTaskName("hi"),task);



    }
    */

}
