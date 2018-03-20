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

package project301.utilities;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

import android.util.Log;

import project301.Task;

/**
 * Save and load tasks from offline json file
 */
@SuppressWarnings({"ALL", "ConstantConditions"})
public class FileIOUtil {


	/**
	 * Save request in file.
	 *
	 * @param task the task
	 * @param context the context
	 */

	public void saveSentTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOnlineSentTaskFileName(task);
			//Log.i("asd","abbbbbb");

			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			//yLog.i("asd","aaaaaa");
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				//noinspection ConstantConditions
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			//noinspection ConstantConditions
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Save offline add request in file.
	 * @param task a task object
	 * @param context current context
	 */
	public void saveOfflineAddTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOfflineAddTaskFileName(task);
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				//noinspection ConstantConditions
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			//noinspection ConstantConditions
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Save offline edit request in file.
	 * @param task a task object
	 * @param context current context
	 */
	public void saveOfflineEditTaskInFile(Task task, Context context) {
		try {
			String jsonStr= TaskUtil.serializer(task);
			String fileName = TaskUtil.generateOfflineEditTaskFileName(task);
			FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			if (fos == null) {
				Log.i("Debug", "null fos in save request");
			}
			try {
				//noinspection ConstantConditions
				fos.write(jsonStr.getBytes());
			} catch (NullPointerException e) {
				Log.i("Debug", "getBytes() threw null pointer exception");
			}
			//noinspection ConstantConditions
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * Load multiple requests from file array list.
	 *
	 * @param context the context
	 * @return the array list of requests
	 */
	public ArrayList<Task> loadMultipleTasksFromFile(Context context, ArrayList<String> fileList) {
		ArrayList<Task> TaskList = new ArrayList<>();
		for (String f : fileList) {
			FileInputStream fis;
			try {
				fis = context.openFileInput(f);
				BufferedReader in = new BufferedReader(new InputStreamReader(fis));
				Task task = TaskUtil.deserializer(in);
				TaskList.add(task);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return TaskList;
	}

	/**
	 * Load a single request from file array list.
	 *
	 * @param context the context
	 * @return the array list of requests
	 */
	public Task loadSingleTaskFromFile(String fileName, Context context) {
		Task request = new Task();
		FileInputStream fis;
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
