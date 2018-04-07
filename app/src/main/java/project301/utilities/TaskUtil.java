package project301.utilities;

import android.content.Context;
import android.util.Log;

import project301.Task;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *A class that helps to serilize and deserialize a task object
 * @classname : TaskUtil
 * @author : Yuqi Zhang
 * @author :Yue Ma
 * @author :Julian Stys
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class TaskUtil {
    /**
     * Convert task object into json format
     * @param task task object
     * @return  Json string
     */
    public static String serializer(Task task) {
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    /**
     * Convert json format string into task object
     * @param string json string
     * @return task object
     */
    public static Task deserializer(BufferedReader string) {
        Gson gson = new Gson();
        return gson.fromJson(string, Task.class);
    }

    /**
     * Generate offline file title
     * @param task task object
     * @return json file title
     */
    public static String generateOnlineSentTaskFileName(Task task) {
        return "sent-" + task.getId() + ".json";
    }
    /**
     * Generate offline adding file title
     * @param task task object
     * @return json file title
     */
    public static String generateOfflineAddTaskFileName(Task task) {
        return "offlineAdd-" + task.getId() + ".json";
    }
    /**
     * Generate offline editing file title
     * @param task task object
     * @return json file title
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

    /**
     * constant of email format
     * https://stackoverflow.com/questions/8204680/java-regex-email
     * @param nothing
     * @return nothing
     */
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    /**
     * check email format
     * https://stackoverflow.com/questions/8204680/java-regex-email
     * @param emailStr user input emali format
     * @return is_correct_format
     */
    public static boolean validate_email(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    /**
     * constant of email format
     * https://stackoverflow.com/questions/8204680/java-regex-email
     * @param nothing
     * @return nothing
     */
    public static final Pattern VALID_PHONE_REGEX =
            Pattern.compile("^[0-9]", Pattern.CASE_INSENSITIVE);

    /**
     * check email format
     * https://stackoverflow.com/questions/8204680/java-regex-email
     * @param phoneStr user input emali format
     * @return is_correct_format
     */
    public static boolean validate_phone(String phoneStr) {
        Matcher matcher = VALID_PHONE_REGEX .matcher(phoneStr);
        return matcher.find();
    }

}
