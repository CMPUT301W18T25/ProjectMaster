package com.example.mayingnan.project301;


import android.test.ActivityInstrumentationTestCase2;

import com.example.mayingnan.project301.allUserActivity.LogInActivity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import java.io.ByteArrayOutputStream;



/*
 * Created by julianstys on 2018-02-25.
 */

//https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
//https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
// /data/data/project301

public class photoTest extends ActivityInstrumentationTestCase2 {
    public photoTest() {
        super(LogInActivity.class);
    }

    public void testAddPhoto(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        new_photo.addPhoto(byteArray);

        assertEquals(byteArray, new_photo.getPhoto());

    }
    public void testUpdatePhoto(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        new_photo.addPhoto(byteArray);

        assertEquals(byteArray, new_photo.getPhoto());
    }
    public void testDeletePhoto(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        new_photo.addPhoto(byteArray);
        assertEquals(byteArray, new_photo.getPhoto());

        new_photo.deletePhoto();
        assertEquals(byteArray, new_photo.getPhoto());

    }
}
