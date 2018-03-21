package project301;



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
    private String photoName;
    private byte[] compressedImage;

    public void Photo(){

    }

    /**
     * add photo
     *
     * @param newImage coded image
     */
    public void  addPhoto(byte[] newImage){
        System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);
    }

    /**
     * Update phto
     * @param newImage coded image
     */
    public void  updatePhoto(byte[] newImage){
        System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);

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
        Arrays.fill(this.compressedImage, (byte) 0 );
    }

    /**
     * get photo
     * @return coded image
     */
    public byte[] getPhoto(){
        return this.compressedImage;
    }


}
