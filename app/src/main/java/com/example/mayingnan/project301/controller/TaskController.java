package com.example.mayingnan.project301.controller;

import com.example.mayingnan.project301.Bid;
import com.example.mayingnan.project301.Task;

import java.util.ArrayList;

/**
 * Created by Xingyuan Yang on 2018-02-25.
 */

public class TaskController {

    public void addTask(Task task){}
    public void deleteTask(Task task){}
    public void requesterUpdateTask(Task task){}
    public ArrayList<Task>  searchTaskByKeyword(String keyword){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }
    public void providerSetBid(Task task, Bid bid){}
    public void providerUpdateBid(Task task, Bid bid){}
    public void providerCancelBid(Task task, Bid bid){}

    public boolean testTrue(String name){
        return true;
    } //created by wdong2 for testing

    public boolean testFalse(String name){
        return false;
    } //created by wdong2 for testing

    public ArrayList<Task> searchBiddenTasksOfThisProvider(String userName){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAssignTasksOfThisProvider(String userName ){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAllTasksOfThisRequester(String userName){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }

    public ArrayList<Task> searchAllRequestingTasks(){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }
    public ArrayList<Task> searchTaskByTaskName(String taskname){
        ArrayList<Task> taskList = new ArrayList<Task> ();
        return taskList;

    }


}
