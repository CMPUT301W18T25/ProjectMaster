package project301;

import java.util.ArrayList;

/**
 * @classname : User
 * @class Detail: User model contains all the features of a user.
 *
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
    private String userAddress;
    private String userEmail;
    private String userPassword;
    private String userType;
    private String resultId;
    private ArrayList<String> providerBiddenTask;
    private ArrayList<String> requesterBiddenTask;

    public User(){
        super();
    }
    public User(String userId,String userName,String userPhone,String userAddress,String userEmail,String userPassword,String userType,String resultId){
        this.userId = userId;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.userPhone = userPhone;
        this.resultId = resultId;
        this.providerBiddenTask = new ArrayList<String>();
        this.requesterBiddenTask = new ArrayList<String>();

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
        this.userName = userName;
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
        this.userPhone = userPhone;
    }

    /**
     * get user address
     * @return user address
     */

    public String getUserAddress() {
        return userAddress;
    }

    /**
     * set user address
     * @param userAddress user address
     */
    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
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
     * set user type
     * @return user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * get user type
     * @param userType user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
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
        this.userId = userId;
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
        providerBiddenTask.add(taskID);
    }

    /**
     * remove his biddentask
     * @param taskID task id
     */
    public void removeABiddenTask(String taskID){
        providerBiddenTask.remove(taskID);
    }

    /**
     * get his bidden task
     * @return bidden tasks
     */

    public ArrayList<String> getRequesterBiddenTask(){
        return this.requesterBiddenTask;
    }

    /**
     * add his bidden task
     * @param taskID bidden tasks
     */
    public void addRequesterBiddenTask(String taskID){
        requesterBiddenTask.add(taskID);
    }

    /**
     * remove his bidden task
     * @param taskID bidden tasks
     */
    public void removeRequesterBiddenTask(String taskID){
        requesterBiddenTask.remove(taskID);
    }
}
