package project301;



import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
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

    private ArrayList<String> encoded_images;

    public Photo(){
        Log.d("Photo.java","Photo constructor");
        this.encoded_images=new ArrayList<>();
    }

    /**
     * add photo
     *
     * @param newImage coded image
     */
    public void addPhoto(String encodedImage){
       // System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);
        encoded_images.add(encodedImage);
    }

    // source:
    public Bitmap getBitmapImage(int index){

        byte[] decodedString = Base64.decode(encoded_images.get(index), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;
    }

    /**
     * Update phto
     * @param newImage coded image
     */


    // source: https://stackoverflow.com/questions/7693633/android-image-dialog-popup
    public void showImage( Context context) {
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

        HorizontalScrollView myScrollGallery=new HorizontalScrollView(context);
        LinearLayout myGallery=new LinearLayout( context);
        for (int i=0;i<encoded_images.size();i++){
            myGallery.addView(insertPhoto(getBitmapImage(i), context));
        }

        myScrollGallery.addView(myGallery);
        builder.addContentView(myScrollGallery, new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,700));
        builder.show();



    }

    private View insertPhoto(Bitmap bm, Context context){

        LinearLayout layout = new LinearLayout(context);
        double iv_scale=(double)bm.getWidth()/bm.getHeight();

        layout.setLayoutParams(new ViewGroup.LayoutParams((int)(iv_scale*700), 700));
        layout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);
        Log.d("bm width", String.valueOf((int)((double)bm.getHeight()/bm.getWidth())*650));
        Log.d("scaling", String.valueOf(iv_scale));

        imageView.setLayoutParams(new ViewGroup.LayoutParams((int)(iv_scale*650), 650));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bm);

        layout.addView(imageView);
        return layout;
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
        encoded_images = null;
    }

    /**
     * get photo
     * @return coded image
     */
    public ArrayList<String> getPhotos(){
        return this.encoded_images;
    }


}
