package com.example.mayingnan.project301;

import android.media.Image;
import android.media.Image.Plane;
import android.provider.ContactsContract;
import android.test.ActivityInstrumentationTestCase2;
import java.io.File;
import com.example.mayingnan.project301.allUserActivity.LogInActivity;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.nio.ByteBuffer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

import ca.ualberta.cs.swapmyride.Misc.UniqueID;
import ca.ualberta.cs.swapmyride.R;


/**
 * Created by julianstys on 2018-02-25.
 */

//https://www.dyclassroom.com/image-processing-project/how-to-read-and-write-image-file-in-java
//https://stackoverflow.com/questions/4181774/show-image-view-from-file-path
// /data/data/project301

public class photoTest extends ActivityInstrumentationTestCase2 {
    public photoTest() {
        super(LogInActivity.class);
    }

    public void addPhotoTest(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        new_photo.addPhoto(byteArray);

        assertEquals(byteArray, new_photo.getPhoto());

    }
    public void updatePhotoTest(){
        Bitmap bmImg = BitmapFactory.decodeFile("/data/data/project3/Project301/test.png");
        Photo new_photo = new Photo();

        //https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmImg.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        new_photo.addPhoto(byteArray);

        assertEquals(byteArray, new_photo.getPhoto());
      }
    public void deletePhotoTest(){
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
