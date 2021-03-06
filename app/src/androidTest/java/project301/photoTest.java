package project301;


import android.test.ActivityInstrumentationTestCase2;

import project301.allUserActivity.LogInActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;


/**
 * @classname : PhotoTest
 * @class Detail : This is the test for photo handling methods
 *
 * @Date :   18/03/2018
 * @author : Julian Stys
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */

/**
 * This is the test for photo handling methods.
 */


//https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
//https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
// /data/data/project301

public class photoTest extends ActivityInstrumentationTestCase2 {
    public photoTest() {
        super(LogInActivity.class);
    }

    /**
     * this is the test for add a photo to photo object
     */
    public void testAddPhoto(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();
        String photo = new String();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = stream.toByteArray();
        try {
            photo = new String(byteArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new_photo.addPhoto(photo);

        assertEquals(photo, new_photo.getPhotos().get(0));

    }
    /**
     * this is the test for updating a photo in existing photo obeject
     */
    public void testUpdatePhoto(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();
        String photo = new String();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        byte[] byteArray = stream.toByteArray();

        try {
            photo = new String(byteArray, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        new_photo.addPhoto(photo);

        assertEquals(photo, new_photo.getPhotos().get(0));
    }

}
