package project301.controller;

import project301.utilities.FileIOUtil;
import project301.Task;
import project301.utilities.TaskUtil;


import java.util.ArrayList;
import android.content.Context;

/**
 * @classname : FileSystemController
 * @class Detail :Save data and load Data in gson file.
 *
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class FileSystemController {
    public FileIOUtil FileIOUtil = new FileIOUtil();
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

    @SuppressWarnings("ConstantConditions")
    public void deleteAllFiles( Context context,String instruction) {
        try {
            ArrayList<String> TaskFiles;
            if(instruction == "sent"){
                TaskFiles = TaskUtil.getSentTaskFileList(context);

            }
            else if(instruction == "offlineAdd"){

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

    @SuppressWarnings("ConstantConditions")
    public void deleteFileByName(String fileName, Context context) {
        try {

            context.deleteFile(fileName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
