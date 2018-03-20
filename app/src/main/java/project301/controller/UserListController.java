
package project301.controller;

import android.os.AsyncTask;
import android.util.Log;

import project301.utilities.UserUtil;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;
import project301.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

/**
 * Activities can communicate with user model through this class.
 * The class contains method to renew data on the elasticsearch database
 * @classname : UserListController
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author : Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

@SuppressWarnings({"ALL", "ConstantConditions"})
public class UserListController {

    public ArrayList<User> userlist;
    private static JestDroidClient client;

    /**
     * Just for testing, will be deleted finally
     * @param name Username
     * @return
     */
    public boolean checkLogInInfo(String name){   //created by wdong2 for testing
        /**
         * return true for valid user name and passward; false otherwise
         */
        if (name == "wdong2"){ return true;}
        if (name == "IUN"){ return false;}
        return true;
    }

    /**
     * Easy interface to check validation sign up
     * @param name username
     * @return whether successful or not
     */
    public boolean checkValidationSignUp (String name){

        if (name == "wdong2"){return true;}

        User newUser;
        newUser = getAUserByName(name);
        if(newUser==null){
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Add a user and check whether the name is duplicated.
     * @param user A user object
     * @return userId
     */
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

    /**
     * Get a user object through his id.
      * @param userId userid
     * @return a user object
     */
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

    /**
     * Get a user object by the username
     * @param userName username
     * @return user object
     */

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
            //Log.i("length","0");
            return null;
        }
        else {

            return Userlist.get(0);
        }
    }

    /**
     * Update user
     *
     * @param user A user object
     */
    public void updateUser(User user){

        UserListController.updateUser updateUser= new UserListController.updateUser();
        updateUser.execute(user);

    }

    /**
     * A static class used to add user to the ES database
     */

    public static class addUser extends AsyncTask<User, Void, User> {
        @Override

        protected User doInBackground(User... users) {
            verifySettings();
            String newId = null;
            User newuser = new User();

            for (User user : users) {
                user.setId(user.getUserName());
                Index index = new Index.Builder(user).index("cmput301w18t25").type("userst").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if(result.isSucceeded())
                    {
                        user.setResultId(result.getId());
                        newuser = user;


                        String query = UserUtil.serializer(newuser);
                        Index index2 = new Index.Builder(query)
                                .index("cmput301w18t25").type("userst").id(result.getId()).build();


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

    /**
     * A static class used to get all user to the ES database
     */


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


    /**
     * A static class used to get a user by its Id from the ES database
     */

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
                    "\"term\":{\"userId\":\""+search_parameters[0]+"\"}\n"+
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

    /**
     * A static class used to get a user by its name from the ES database
     */

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
                    .index("cmput301w18t25").type("userst").id(users[0].getResultId()).build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    Log.i("Debug", "Successful update user profile");
                } else {
                    Log.i("Error", "We failed to update user profile to elastic search!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return users[0];
        }


    }

    /**
     * A static class used to delete all user from the ES database
     */


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
                Delete delete = new Delete.Builder(u.getResultId()).index("cmput301w18t25").type("userst").build();

                try {
                    client.execute(delete);

                } catch (Exception e) {
                    Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
                }

            }
            return null;

        }
    }

    /**
     * Check whether the user name and password matching with each other
     * @param userName username
     * @param userPassword userpassword
     * @return
     */
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

    /**
     * elastic search database setting
     */
    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://192.30.35.214:8080").discoveryEnabled(true).multiThreaded(true);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }

}