package project301;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.util.Arrays;

/**
 * Photo model contains converted photo information.
 * @classname :Photo
 * @Date :   18/03/2018
 * @author :Julian Stys
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


public class Photo {

    private String encoded_image;

    public void Photo(){

    }

    /**
     * add photo
     *
     * @param newImage coded image
     */
    public void addPhoto(String encodedImage){
       // System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);
        encoded_image = encodedImage;
    }

    // source:
    public Bitmap getBitmapImage(){
        byte[] decodedString = Base64.decode(encoded_image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    /**
     * Update phto
     * @param newImage coded image
     */


    /**
     * Upload photo
     */
    public void savePhoto(){

    }

    /**
     * Delete photo
     */
    public void  deletePhoto(){
        encoded_image = null;
    }

    /**
     * get photo
     * @return coded image
     */
    public String getPhoto(){
        return this.encoded_image;
    }


}
