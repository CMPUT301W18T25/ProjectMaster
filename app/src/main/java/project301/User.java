package project301;

import android.util.Log;

import java.util.ArrayList;

/**
 * User object contains all the features of a user. Basic setters and getters are included.
 * @classname : User
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author :Yue Ma
 * @author :Julian Stys
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

public class User {
    private String userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private String resultId;
    private final ArrayList<String> providerBiddenTask;

    /**
     * Default construct
     */
    public User(){
        super();
        this.providerBiddenTask = new ArrayList<>();
    }


    /**
     * Get user name
     * @return username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * set user name
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName.toLowerCase();
    }

    /**
     * get user phone
     * @return user phone
     */

    public String getUserPhone() {
        return userPhone;
    }

    /**
     * set user phone
     * @param userPhone user phone
     */

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone.toLowerCase();
    }

    /**
     * get user email
     * @return user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * set user email
     * @param userEmail
     */
    @SuppressWarnings("JavaDoc")
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * get user password
     * @return password
     */

    public String getUserPassword() {
        return userPassword;
    }

    /**
     * set user password
     * @param userPassword password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * get user id
     * @return user id
     */
    public String getId(){
        return userId;
    }

    /**
     * set user id
     * @param userId user id
     */
    public void setId(String userId){
        this.userId = userId.toLowerCase();
    }

    /**
     * set ES id
     * @return ES id
     */

    public String getResultId(){
        return resultId;
    }

    /**
     * get ES id
     * @param resultId ES id
     */
    public void setResultId(String resultId){
        this.resultId = resultId;
    }

    /**
     * get his bidden task
     * @return bidden task
     */

    public ArrayList<String> getProviderBiddenTask(){
        return this.providerBiddenTask;
    }

    /**
     * add his bidden task
     * @param taskID task id
     */

    public void addProviderBiddenTask(String taskID){
        Boolean sign = true;
        for (int i = 0; i < this.providerBiddenTask.size(); i++){
            if (this.providerBiddenTask.get(i).equals(taskID) ){
                sign = false;
            }
        }
        if (sign){
            this.providerBiddenTask.add(taskID);
        }
    }

    /**
     * remove his bidden task
     * @param taskID task id
     */

    public void removeProviderBiddenTask(String taskID){
        this.providerBiddenTask.remove(taskID);
    }


}
