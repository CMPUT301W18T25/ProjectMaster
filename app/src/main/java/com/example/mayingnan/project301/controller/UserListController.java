
package com.example.mayingnan.project301.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.example.mayingnan.project301.User;

import java.util.ArrayList;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;

public class UserListController {

    public ArrayList<User> userlist;
    private static JestDroidClient client;


    public boolean testTrue(String name){
        return true;
    }

    public boolean testFalse(String name){
        return false;
    }

    public boolean checkUserName(String name){
        /**
         * return true for valid user name and passward; false otherwise
         */
        if (name == "wdong2"){ return true;}
        if (name == "IUN"){ return false;}
        return true;
    }

    public static class addUser extends AsyncTask<User, Void, Void> {

        @Override

        protected Void doInBackground(User... user) {
            verifySettings();

            for (User user0 : user) {
                Index index = new Index.Builder(user0).index("CMPUT301W18T25").type("user").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                    {
                        user0.setId(result.getId());
                        Log.i("Success","Elasticsearch ");

                    }
                    else
                    {
                        Log.i("Error","Elasticsearch was not able to add the user");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the users");
                }

            }
            return null;
        }
    }

    public void updateUser(User user){

    }

    public ArrayList<User> getAllUsers(){
        return userlist;
    }

    public User getAUserByName(String name){
        User user = new User ();
        return user;
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080/");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
    
}