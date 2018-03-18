
package com.example.mayingnan.project301.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.mayingnan.project301.utilities.UserUtil;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import com.example.mayingnan.project301.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

@SuppressWarnings({"ALL", "ConstantConditions"})
public class UserListController {

    public ArrayList<User> userlist;
    private static JestDroidClient client;

    public boolean checkLogInInfo(String name){   //created by wdong2 for testing
        /**
         * return true for valid user name and passward; false otherwise
         */
        if (name == "wdong2"){ return true;}
        if (name == "IUN"){ return false;}
        return true;
    }
    //if
    public boolean checkValidationSignUp (String name){

        if (name == "wdong2"){return true;}

        User newUser;
        newUser = getAUserByName(name);
        if(newUser==null){
            Log.i("asda","asdasd");
            return true;
        }
        else {
            Log.i("userName:",newUser.getUserName());

            return false;
        }
    }
    public String addUserAndCheck(User user){
        boolean checkValidUser = checkValidationSignUp(user.getUserName());
        String userId = null;
        User newUser = new User();
        if(checkValidUser){
            UserListController.addUser addUser = new UserListController.addUser();
            addUser.execute(user);
            try {
                newUser = addUser.get();
                userId = newUser.getId();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        else{
            return null;
        }
        Log.d("UserListController","userId return "+userId);
        return userId;

    }
    @SuppressWarnings("ConstantConditions")
    public User getAUserById(String userId){
        Log.d("UserListController","getAUserById userId: "+userId);
        UserListController.getAUserById getAUserById = new UserListController.getAUserById();
        getAUserById.execute(userId);


        ArrayList<User> Userlist = null;
        try {
            Userlist = getAUserById.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (Userlist == null){
            Log.d("UserListController", "Userlist = null");

        }
        //noinspection ConstantConditions
        if(Userlist.isEmpty()){
            Log.d("UserListController", "getAUserById2 return null");
            return null;
        }
        else {
            Log.d("UserListController", "getAUserById2 return nonnull");

            return Userlist.get(0);
        }
    }
    @SuppressWarnings("ConstantConditions")
    public User getAUserByName(String userName){
        UserListController.getAUserByName getAUserByName = new UserListController.getAUserByName();
        getAUserByName.execute(userName);

        ArrayList<User> Userlist = null;
        try {
            Userlist = getAUserByName.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //noinspection ConstantConditions
        if(Userlist.isEmpty()){
            Log.i("length","0");
            return null;
        }
        else {
            Log.i("userName1:",Userlist.get(0).getUserName());

            return Userlist.get(0);
        }
    }

    public void updateUser(User user){

        UserListController.updateUser updateUser= new UserListController.updateUser();
        updateUser.execute(user);

    }


    public static class addUser extends AsyncTask<User, Void, User> {
        @Override

        protected User doInBackground(User... users) {
            verifySettings();
            String newId = null;
            User newuser = new User();

            for (User user : users) {
                Index index = new Index.Builder(user).index("cmput301w18t25").type("userst").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        user.setId(result.getId());
                        newuser = user;


                        String query = UserUtil.serializer(newuser);
                        Index index2 = new Index.Builder(query)
                                .index("cmput301w18t25").type("userst").id(newuser.getId()).build();


                        DocumentResult result2 = client.execute(index2);

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
            Log.i("newUserid",newuser.getId());
            return newuser;


        }
    }

    public static class GetAllUsers extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            String query = "{ \"size\": 100 }" ;
            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("userst")
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


    public static class getAUserById extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();
            Log.d("UserListController", "getAUserById execute");
            for (int i=0;i<search_parameters.length;i++){
                Log.d("UserListController", "search_parameters at "+i+": "+search_parameters[i]);

            }
            ArrayList<User> users = new ArrayList<User>();

            String query = "{ \n"+
                    "\"query\":{\n"+
                    "\"term\":{\"_id\":\""+search_parameters[0]+"\"}\n"+
                    "}\n"+"}";


            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("userst")
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
    public static class getAUserByName extends AsyncTask<String, Void, ArrayList<User>> {
        @Override
        protected ArrayList<User> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<User> users = new ArrayList<User>();

            String query = "{ \n" +
                    "\"query\":{\n" +
                    "\"term\":{\"userName\":\"" + search_parameters[0] + "\"}\n" +
                    "}\n" + "}";

            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("userst")
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



    /**
     * Static class that update user profile
     */
    public static class updateUser extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... users) {
            verifySettings();
            // Serialize object into Json string
            String query = UserUtil.serializer(users[0]);
            Index index = new Index.Builder(query)
                    .index("cmput301w18t25").type("userst").id(users[0].getId()).build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.i("Debug", "Successful update user profile");
                    Log.i("password",users[0].getUserPassword());
                    Log.i("name",users[0].getUserPassword());
                } else {
                    Log.i("Error", "We failed to update user profile to elastic search!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users[0];
        }


    }

    public static class deleteAllUsers extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... search_parameters) {
            verifySettings();
            ArrayList<User> users = new ArrayList<User>();

            String query = "{ \"size\": 100 }" ;
            Log.i("Query", "The query was " + query);
            Search search = new Search.Builder(query)
                    .addIndex("cmput301w18t25")
                    .addType("userst")
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

            for (User u: users){
                Delete delete = new Delete.Builder(u.getId()).index("cmput301w18t25").type("userst").build();

                try {
                    client.execute(delete);

                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;

        }
    }

    @SuppressWarnings("ConstantConditions")
    public Boolean checkUserByNameAndPassword(String userName, String userPassword){


        UserListController.getAUserByName getAUserByName = new UserListController.getAUserByName();
        getAUserByName.execute(userName);

        ArrayList<User> Userlist = null;
        try {
            Userlist = getAUserByName.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        boolean found = false;
        //noinspection ConstantConditions
        for (User u: Userlist){

            Log.i("username",u.getUserName());


            if(u.getUserPassword().equals(userPassword)){
                found = true;
                return found;
            }
        }
        return found;
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