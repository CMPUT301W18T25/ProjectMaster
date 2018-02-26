package com.example.mayingnan.project301;

import com.example.mayingnan.project301.controller.TaskController;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

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
    public void addTaskTest(){

    }
    public void deleteTaskTest(){

    }
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
    public void searchTaskByKeywordTest(){}
    public void providerSetBidTest(){}
    public void providerUpdateBidTest(){}
    public void providerCancelBidTest(){}
    public void searchBiddenTasksOfThisProviderTest(){

    }
    public void searchAssignTasksOfThisProviderTest(){}
    public void searchAllTasksOfThisRequesterTest(){}
    public void searchAllRequestingTasksTest(){}
    public void searchTaskByTaskNameTest(){}

}
