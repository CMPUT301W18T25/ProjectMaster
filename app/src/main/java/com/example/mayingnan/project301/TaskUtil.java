package com.example.mayingnan.project301;

import com.google.gson.Gson;

/**
 * Created by yuqi on 11/03/18.
 */

public class TaskUtil {
    public static String serializer(Task task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    public static Task deserializer(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Task.class);
    }
}
