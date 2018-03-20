package project301.utilities;

import android.content.Context;
import android.util.Log;

import project301.Task;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 *Task class that help to serilize
 * and deserialize a task object
 */

public class TaskUtil {
    /**
     * Convert task object into json format
     * @param task
     * @return  Json string
     */
    public static String serializer(Task task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    /**
     * Convert json format string into task object
     * @param string
     * @return task object
     */
    public static Task deserializer(BufferedReader string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Task.class);
    }

    /**
     * Generate offline file title
     * @param task
     * @return
     */
    public static String generateOnlineSentTaskFileName(Task task) {
        return "sent-" + task.getId() + ".json";
    }
    /**
     * Generate offline adding file title
     * @param task
     * @return
     */
    public static String generateOfflineAddTaskFileName(Task task) {
        return "offlineAdd-" + task.getId() + ".json";
    }
    /**
     * Generate offline editing file title
     * @param task
     * @return
     */
    public static String generateOfflineEditTaskFileName(Task task) {
        return "offlineEdit-" + task.getId() + ".json";
    }

    /**
     * get sent tasks file list
     * @param context current context
     * @return filelist
     */
    public static ArrayList<String> getSentTaskFileList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> sentTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("sent-")) {
               // Log.i("Debug", f);
                sentTaskFileList.add(f);
            }
        }
        return sentTaskFileList;
    }

    /**
     * get offline add tasks file list
     * @param context current context
     * @return filelist
     */
    public static ArrayList<String> getOfflineAddTaskFileList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> OfflineAddTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("offlineAdd-")) {
                Log.i("Debug", f);
                OfflineAddTaskFileList.add(f);
            }
        }
        return OfflineAddTaskFileList;
    }

    /**
     * get offline edit tasks file list
     * @param context current context
     * @return filelist
     */
    public static ArrayList<String> getOfflineEditTaskFileList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> OfflineEditTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("offlineEdit-")) {
                //Log.i("Debug", f);
                OfflineEditTaskFileList.add(f);
            }
        }
        return OfflineEditTaskFileList;
    }


}
