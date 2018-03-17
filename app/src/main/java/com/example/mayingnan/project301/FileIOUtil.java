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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;

import android.util.Log;

import com.google.gson.Gson;


import org.slf4j.helpers.Util;



/**
 * The type File io util.
 */
public class FileIOUtil {


	/**
	 * Save request in file.
	 *
	 * @param task the task
	 * @param context the context
	 */
	public static void saveSentTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOnlineSentTaskFileName(task);
			FileOutputStream fos = context.openFileOutput(fileName, 0);
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void saveOfflineAddTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOfflineAddTaskFileName(task);
			FileOutputStream fos = context.openFileOutput(fileName, 0);
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}


	public static void saveOfflineEditTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOfflineEditTaskFileName(task);
			FileOutputStream fos = context.openFileOutput(fileName, 0);
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Load request from file array list.
	 *
	 * @param context the context
	 * @return the array list of requests
	 */
	/*public static ArrayList<Request> loadRequestFromFile(Context context, ArrayList<String> fileList) {
		ArrayList<Request> requestList = new ArrayList<>();
        Gson gson = RequestUtil.customGsonBuilder();
		for (String f : fileList) {
            FileInputStream fis = null;
            try {
                fis = context.openFileInput(f);
                BufferedReader in = new BufferedReader(new InputStreamReader(fis));
                NormalRequest req = gson.fromJson(in, NormalRequest.class);
                requestList.add(req);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
		}
		return requestList;
	}*/

	public static Task loadSingleTaskFromFile(String fileName, Context context) {
		Task request = new Task();
		FileInputStream fis = null;
		try {
			fis = context.openFileInput(fileName);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			request = TaskUtil.deserializer(in);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return request;
	}
}
