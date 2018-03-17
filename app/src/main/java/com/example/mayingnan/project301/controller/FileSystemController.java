package com.example.mayingnan.project301.controller;

import com.example.mayingnan.project301.FileIOUtil;
import com.example.mayingnan.project301.Task;
import com.example.mayingnan.project301.TaskUtil;
import com.example.mayingnan.project301.UserUtil;

import java.util.ArrayList;
import android.content.Context;

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
    //instruction can be:sent,offlineAdd,offlineEdit
    public void saveToFile(Task task,String instruction,Context context){
        if(instruction.equals("sent")){
            FileIOUtil.saveSentTaskInFile(task,context);
        }
        else if(instruction.equals("offlineAdd")){
            FileIOUtil.saveOfflineAddTaskInFile(task,context);
        }
        else if(instruction.equals("offlineEdit")){
            FileIOUtil.saveOfflineEditTaskInFile(task,context);
        }
    }
    public ArrayList<Task> loadSentTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<String> SentTaskFiles = TaskUtil.getOfflineAddTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, SentTaskFiles);
        return Tasks;
    }

    public ArrayList<Task> loadOfflineAddTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<String> offlineAddTaskFiles = TaskUtil.getOfflineAddTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, offlineAddTaskFiles);
        return Tasks;
    }
    public ArrayList<Task> loadOfflineEditTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<String> offlineEditTaskFile = TaskUtil.getOfflineEditTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, offlineEditTaskFile);
        return Tasks;
    }
}
