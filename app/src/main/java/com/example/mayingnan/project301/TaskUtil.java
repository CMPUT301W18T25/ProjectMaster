package com.example.mayingnan.project301;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * Created by yuqi on 11/03/18.
 */

public class TaskUtil {
    public static String serializer(Task task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    public static Task deserializer(BufferedReader string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Task.class);
    }

    public static String generateOnlineSentTaskFileName(Task task) {
        return "sent-" + task.getId() + ".json";
    }
    public static String generateOfflineAddTaskFileName(Task task) {
        return "offlineAdd-" + task.getId() + ".json";
    }
    public static String generateOfflineEditTaskFileName(Task task) {
        return "offlineEdit-" + task.getId() + ".json";
    }


    public static ArrayList<String> getSentTaskFileList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> sentTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("sent-")) {
                Log.i("Debug", f);
                sentTaskFileList.add(f);
            }
        }
        return sentTaskFileList;
    }

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

    public static ArrayList<String> getOfflineEditTaskFileList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> OfflineEditTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("offlineEdit-")) {
                Log.i("Debug", f);
                OfflineEditTaskFileList.add(f);
            }
        }
        return OfflineEditTaskFileList;
    }
}
