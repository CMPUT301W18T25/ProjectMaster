package project301;



import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;

/**
 * Photo class contains information for an array of base string64 bitmaps.
 * If photo(s) exist for a task, a single Photo class instance will exist
 * in that task object and will contain all the information about each photo
 * form that task.
 *
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


    public void addPhoto(String encodedImage){
        encoded_images.add(encodedImage);
    }

    // source:

    /**
     * Converts a base64 string encoded bitmap to a bitmap. 'index' refers to the
     * position of the desired bitamap in the encoded_images ArrayList
     *
     * source: https://stackoverflow.com/questions/30818538/converting-json-object-with-bitmaps?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     * @param index
     * @return
     */
    public Bitmap getBitmapImage(int index){

        byte[] decodedString = Base64.decode(encoded_images.get(index), Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }




    /**
     * Creates a popup in 'context' showing a Horizontal Scroll Bar of all
     * the images that belong in encoded_images
     *
     * source: https://stackoverflow.com/questions/7693633/android-image-dialog-popup
     *
     * @param context
     */
    public void showImage( Context context) {
        Log.d("Photo", "showImage");
        Log.d("Context", context.getPackageName());

        Dialog builder = new Dialog(context);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //noinspection ConstantConditions
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
        Log.d("Photo","Images added");
        myScrollGallery.addView(myGallery);
        builder.addContentView(myScrollGallery, new LinearLayout.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,700));
        builder.show();
    }

    /**
     * Inserts a photo into the Horizontal Scroll View from showImage.
     *
     * source: https://stackoverflow.com/questions/17489390/image-gallery-with-a-horizontal-scrollview
     *
     * @param bm
     * @param context
     * @return
     */
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
     * Returns the photos as encoded_images, i.e. as ArraList of base64 Strings
     *
     * @return encoded image
     */
    public ArrayList<String> getPhotos(){
        return this.encoded_images;
    }


}
