package project301.utilities;

import project301.User;
import com.google.gson.Gson;

/**
 * Utility class that help to serialize and deserialize the user object
 * @classname : UserUtil
 * @Date :   18/03/2018
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */
public class UserUtil {
    /**
     * Convert user object into json string
     * @param user a user object
     * @return json string
     */
    public static String serializer(User user) {
        Gson gson = new Gson();
        return gson.toJson(user);
    }

}
