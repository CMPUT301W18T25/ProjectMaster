package project301.controller;

import project301.utilities.FileIOUtil;
import project301.Task;
import project301.utilities.TaskUtil;


import java.util.ArrayList;
import android.content.Context;
import android.util.Log;

/**
 * Controller to convert tasks from TaskObject and Json format. Create Json file and delete Json files
 * @classname : FileSystemController
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class FileSystemController {
    public FileIOUtil FileIOUtil = new FileIOUtil();
    /**
     * Save tasks into json file
     * @param task a task object
     * @param instruction Three kinds of instruction: offlineAdd, OfflineEdit, sent
     * @param context current context
     */
    public void saveToFile(Task task,String instruction,Context context){
        if(instruction.equals("sent")){
            FileIOUtil.saveSentTaskInFile(task,context);
        }
        else if(instruction.equals("offlineAdd")){
            FileIOUtil.saveOfflineAddTaskInFile(task,context);
        }
        else{
            Log.i("save offline","TASK");
            FileIOUtil.saveOfflineEditTaskInFile(task,context);
        }
    }

    /**
     * Load 'sent' tasks from json file
     * @param context current context
     * @return tasks list
     */
    public ArrayList<Task> loadSentTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<Task> EditTasks;
        ArrayList<Task> AddTasks;


        ArrayList<String> SentTaskFiles = TaskUtil.getSentTaskFileList(context);
        ArrayList<String> OfflineAddTaskFiles = TaskUtil.getOfflineAddTaskFileList(context);

        ArrayList<String> OfflineEditTaskFiles = TaskUtil.getOfflineEditTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, SentTaskFiles);
        AddTasks = FileIOUtil.loadMultipleTasksFromFile(context, OfflineAddTaskFiles);

        EditTasks =  FileIOUtil.loadMultipleTasksFromFile(context, OfflineEditTaskFiles);
        for(Task editTask:EditTasks){
            for(Task task:Tasks){
                if(editTask.getId().equals(task.getId())){
                    Tasks.remove(task);
                    Tasks.add(editTask);
                }
            }
        }
        Tasks.addAll(AddTasks);

        return Tasks;
    }

    /**
     * Load 'offlineAdd' tasks from json file
     * @param context current context
     * @return tasks list
     */
    public ArrayList<Task> loadOfflineAddTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<String> offlineAddTaskFiles = TaskUtil.getOfflineAddTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, offlineAddTaskFiles);
        return Tasks;
    }

    /**
     * Load 'offlineEdit' tasks from json file
     * @param context current context
     * @return tasks list
     */
    public ArrayList<Task> loadOfflineEditTasksFromFile(Context context){
        ArrayList<Task> Tasks;
        ArrayList<String> offlineEditTaskFile = TaskUtil.getOfflineEditTaskFileList(context);
        Tasks = FileIOUtil.loadMultipleTasksFromFile(context, offlineEditTaskFile);
        return Tasks;
    }

    /**
     * Delete all json files begin with the specific instruction title
     * @param context current context
     * @param instruction Three kinds of instruction: offlineAdd, OfflineEdit, sent
     */
    public void deleteAllFiles( Context context,String instruction) {
        try {
            ArrayList<String> TaskFiles;
            if(instruction.equals("sent")){
                TaskFiles = TaskUtil.getSentTaskFileList(context);

            }
            else if(instruction.equals("offlineAdd")){

                TaskFiles = TaskUtil.getOfflineAddTaskFileList(context);

            }

            else{

                TaskFiles = TaskUtil.getOfflineEditTaskFileList(context);

            }

            for(String file: TaskFiles){
                context.deleteFile(file);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * Delete all json files
     * @param context current context
     */

    public void deleteFiles( Context context) {
        String[] fileList = context.fileList();

        for(String file: fileList){
            context.deleteFile(file);
        }

    }


    /**
     * Delete json file by the file name
     * @param fileName json file name
     * @param context current context
     */
    public void deleteFileByName(String fileName, Context context) {
        try {

            context.deleteFile(fileName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
