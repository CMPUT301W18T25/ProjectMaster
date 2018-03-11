package com.example.mayingnan.project301;

import com.example.mayingnan.project301.controller.TaskController;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by julianstys on 2018-02-25.
 */

public class TaskTest {

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
    public void testAddTask(){
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hi");
        tc.addTask(task);
        assertEquals(tc.searchTaskByTaskName("hi"),task);

    }
    @Test
    public void testDeleteTask(){

        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hi");
        tc.addTask(task);
        tc.deleteTask(task);
        assertFalse(tc.searchTaskByTaskName("hi").contains(task));
    }
    @Test
    public void requesterUpdateTaskTest(){
        TaskController tc = new TaskController();
        Task task = new Task();
        task.setTaskName("hi");
        tc.addTask(task);
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

}
