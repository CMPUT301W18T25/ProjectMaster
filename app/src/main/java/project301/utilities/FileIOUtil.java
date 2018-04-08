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
 * Save and load tasks into json file
 * @classname : FileIOUtil
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */
@SuppressWarnings({"ALL", "ConstantConditions"})
public class FileIOUtil {

	/**
	 * Save request task in file.
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
	 * When no internet, save add task instruction in file.
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
	 * When no internet, save editting task in file
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
	 * Load multiple tasks from file array list.
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
	 * Load a single task from file array list.
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
