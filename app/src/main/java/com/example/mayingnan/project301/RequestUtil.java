/*
 * Copyright (C) 2016 CMPUT301F16T18 - Alan(Xutong) Zhao, Michael(Zichun) Lin, Stephen Larsen, Yu Zhu, Zhenzhe Xu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.mayingnan.project301;
import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;


/**
 * Utility class to help pass request object through intent
 * or update to the server
 */
public class RequestUtil {
    public static String serializer(Task task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    public static Task deserializer(String string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Task.class);
    }


    public static String addTaskFileName(Task task) {
        return "online" + task.getTaskName() + ".json";
    }

    public static String addOfflineTaskFileName(Task task) {
        return "offline-" + task.getTaskName() + ".json";
    }
    //get all offline task
    public static ArrayList<String> getOfflineTaskList(Context context) {
        String[] fileList = context.fileList();
        ArrayList<String> offlineTaskFileList = new ArrayList<>();
        for (String f : fileList) {
            if (f != null && f.startsWith("offline-")) {
                offlineTaskFileList.add(f);
            }
        }
        return offlineTaskFileList;
    }


}
