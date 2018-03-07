package com.example.mayingnan.project301.controller;

import com.example.mayingnan.project301.Task;

import java.util.ArrayList;

/**
 * Created by Xingyuan Yang on 2018-02-25.
 */

public class FileSystemController {
    private String FILENAME;

    public boolean testTrue(String name){
        return true;
    }

    public boolean testFalse(String name){
        return false;
    }

    public void saveToFile(Task task){

    }

    public Task[] loadFromFile(){
        ArrayList<Task> Tasks = new ArrayList<Task>();
        return Tasks.toArray(new Task[Tasks.size()]);
    }
}
