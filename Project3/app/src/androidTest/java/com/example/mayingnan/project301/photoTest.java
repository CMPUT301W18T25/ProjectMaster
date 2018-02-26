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
        File imgFile = new  File("/data/data/project3/Project301/test.png");
        Photo test_photo;

        if(imgFile.exists()){
           BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

           image = ImageIO.read(imgFile);
           test_photo.addPhoto("test.png", image);
        }

        assertEquals("test.png", test_photo.photoName);

    }
    public void updatePhotoTest(){
        File imgFile = new File("/data/data/project3/Project301/test.png");
        Photo test_photo;

        if(imgFile.exists()){
           BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

           image = ImageIO.read(imgFile);
           test_photo.addPhoto("test.png", image);
        }

        imgFile = new File("/data/data/project3/Project301/test1.png");

        if(imgFile.exists()){
           BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

           image = ImageIO.read(imgFile);
           test_photo.updatePhoto("test1.png", image);
        }

        assertEquals("test.png", test_photo.photoName);
    }
    public void deletePhotoTest(){
        File imgFile = new File("/data/data/project3/Project301/test.png");
        Photo test_photo;

        if(imgFile.exists()){
           BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);

           image = ImageIO.read(imgFile);
           test_photo.addPhoto("test.png", image);
        }

        assertEquals("test.png", test_photo.photoName);
    }
}
