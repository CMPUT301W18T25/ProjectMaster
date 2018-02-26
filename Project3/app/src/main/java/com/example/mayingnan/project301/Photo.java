package com.example.mayingnan.project301;

import android.media.Image;

/**
 * Created by Xingyuan Yang on 2018/2/25.
 */

public class Photo {
    private String photoName;
    private byte[] compressedImage;

    public void Photo(){

    }

    public void  addPhoto(byte[] newImage){
      System.arraycopy(newImage, 0, this.compressedImage, 0, source.length);
    }
    public void  updatePhoto(byte[] newImage){
      System.arraycopy(newImage, 0, this.compressedImage, 0, source.length);

    }
    public void savePhoto(){

    }
    public void  deletePhoto(){
      Arrays.fill(this.compressedImage, (byte) 0 );
    }
    public byte[] getPhoto(){
        return compressedImage;
    }


}
