package com.example.mayingnan.project301;

import com.google.gson.Gson;

import java.io.BufferedReader;

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
}
