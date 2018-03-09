
package com.example.mayingnan.project301.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.example.mayingnan.project301.User;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class UserListController {

    public ArrayList<User> userlist;
    private static JestDroidClient client;


    public boolean testTrue(String name){
        return true;
    } //created by wdong2 for testing

    public boolean testFalse(String name){
        return false;
    }//created by wdong2 for testing

    public boolean checkLogInInfo(String name){   //created by wdong2 for testing
        /**
         * return true for valid user name and passward; false otherwise
         */
        if (name == "wdong2"){ return true;}
        if (name == "IUN"){ return false;}
        return true;
    }

    public boolean checkValidationSignUp (String name, String email, String phone, String passward){ //created by wdong2 for testing
        if (name == "wdong2"){return true;}
        return false;
    }

    public static class addUser extends AsyncTask<User, Void, Void> {

        @Override

        protected Void doInBackground(User... users) {
            verifySettings();

            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w18t25").type("user").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);
                    if(result.isSucceeded())
                    {
                        user.setId(result.getId());
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

    // TODO we need a function which gets tweets from elastic search
    public static class GetAllUsers extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

//            String query = "{ \"size\": 4, \n" +
//                    "    \"query\" : {\n" +
//                    "        \"term\" : { \"userName\" : \"\" }\n" +
//                    "    }\n" +
//                    "}" ;

            String query = "{ \"size\": 100 }" ;
            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("user")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    List<User> foundUsers
                            = result.getSourceAsObjectList(User.class);
                    users.addAll(foundUsers);
                } else {
                    Log.i("Error", "The search query failed");
                }
                // TODO get the results of the query
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
        }
    }

    public User getAUserByName(String name){
        User user = new User ();
        return user;
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://192.30.35.214:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
    
}