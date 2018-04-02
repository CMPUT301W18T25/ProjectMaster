package project301;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

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


    // source: https://stackoverflow.com/questions/7693633/android-image-dialog-popup
    public void showImage( Context context) {

        Bitmap image = Bitmap.createBitmap(
                getBitmapImage());

        Log.d("Photo", "showImage");
        Log.d("Context", context.getPackageName());

        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.BLACK));
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;
            }
        });

        ImageView imageView = new ImageView(context);

        imageView.setImageBitmap(image);

        double iv_scale=1000/(double)image.getWidth();

        int iv_width=(int)(iv_scale*image.getWidth());
        int iv_height=(int)(iv_scale*image.getHeight());
        Log.d("image width", String.valueOf(iv_width));
        Log.d("image height", String.valueOf(iv_height));

        Log.d("scale", String.valueOf(iv_scale));
        builder.addContentView(imageView, new LinearLayout.LayoutParams(
                iv_width,iv_height));
        //builder.addContentView(imageView, new RelativeLayout.LayoutParams(
        //        ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();



    }


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
