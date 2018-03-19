package project301;



import java.util.Arrays;

/**
 * @classname :Photo
 * @class Detail : Photo model contains converted photo information
 *
 * @Date :   18/03/2018
 * @author : Yuqi Zhang
 * @author :Julian Stys
 * @author :Yue Ma
 * @version 1.0
 * @copyright : copyright (c) 2018 CMPUT301W18T25
 */


public class Photo {
    private String photoName;
    private byte[] compressedImage;

    public void Photo(){

    }

    public void  addPhoto(byte[] newImage){
        System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);
    }
    public void  updatePhoto(byte[] newImage){
        System.arraycopy(newImage, 0, this.compressedImage, 0, this.compressedImage.length);

    }
    public void savePhoto(){

    }
    public void  deletePhoto(){
        Arrays.fill(this.compressedImage, (byte) 0 );
    }
    public byte[] getPhoto(){
        return this.compressedImage;
    }


}