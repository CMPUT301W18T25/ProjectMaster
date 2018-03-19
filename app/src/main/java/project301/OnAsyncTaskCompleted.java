package project301;

/**
 * @classname : OnAsyncTaskCompleted
 * @class Detail: Listen to the asnctask to see whether it is finished
 *
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author :Julian Stys
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


/**
 * The interface On async task completed, will be
 * called after the async event is done.
 */
public interface OnAsyncTaskCompleted {
    /**
     * On task completed.
     *
     * @param o the object will be return by the asynctask
     */
    void onTaskCompleted(Object o);
}
