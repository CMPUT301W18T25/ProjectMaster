package project301.controller;

import android.content.Context;
import android.util.Log;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import project301.Task;
import project301.utilities.FileIOUtil;

/**
 * Execute offline tasks when the App reconnects to the internet.
 * @classname : OfflineController
 * @Date :   18/03/2018
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


public class OfflineController {
    /**
     * After the app reconnect to the internet, it will try to execute previously stored offline tasks
     * @param context current context
     */
    public boolean tryToExecuteOfflineTasks(Context context){
        //Log.i("try to resume offline","Tasks");
        FileSystemController fileSystemController = new FileSystemController();
        ArrayList<Task> OfflineAddTasks = fileSystemController.loadOfflineAddTasksFromFile(context);
        Boolean change = false;
        for(Task task: OfflineAddTasks){
            String fileName = "offlineAdd-" + task.getId() + ".json";
            TaskController.addTask addTaskCtl=new TaskController.addTask();
            addTaskCtl.execute(task);
            String taskId = "taskId";
            try {
                taskId=addTaskCtl.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(!taskId.equals("taskId")) {
                change = true;
                fileSystemController.deleteFileByName(fileName, context);
                task.setId(taskId);
                FileIOUtil fileIOUtil = new FileIOUtil();
                fileIOUtil.saveSentTaskInFile(task,context);
            }
        }
        ArrayList<Task> OfflineEditTasks = fileSystemController.loadOfflineEditTasksFromFile(context);
        for(Task task: OfflineEditTasks){
            String fileName = "offlineEdit-" + task.getId() + ".json";
            String sentFileName = "sent-" + task.getId() + ".json";

            TaskController.requesterUpdateTask requesterUpdateTask=new TaskController.requesterUpdateTask();
            requesterUpdateTask.execute(task);
            Boolean success = false;
            try {
                success=requesterUpdateTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if(success) {
                Log.i("Success offEdit",".");
                change = true;

                fileSystemController.deleteFileByName(fileName, context);
                fileSystemController.deleteFileByName(sentFileName, context);
                Log.i("Success delete",".");

                FileIOUtil fileIOUtil = new FileIOUtil();
                fileIOUtil.saveSentTaskInFile(task,context);
            }
            else{
                Log.i("failed offEdit",".");
            }

        }
        return change;
    }
}
