package com.kit.photocapture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class Utility {

    private final static String TAG = "Utility";

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, UUID.randomUUID().toString(), null);
        return Uri.parse(path);
    }
    public static Bitmap getImageData(Context inContext, Uri imageUri) {
        Bitmap nowBitmap = null;
        try {
            nowBitmap = MediaStore.Images.Media.getBitmap(inContext.getContentResolver(), imageUri);
        }catch(Throwable t){
            t.printStackTrace();
        }
        return nowBitmap;
    }

    public static Bitmap createFlippedBitmap(Bitmap source, boolean xFlip, boolean yFlip) {
        boolean isError = false;
        Throwable errorObject = null;

        try {
            Matrix matrix = new Matrix();
            matrix.postScale(xFlip ? -1 : 1, yFlip ? -1 : 1, source.getWidth() / 2f, source.getHeight() / 2f);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
        }catch(Throwable t){
            isError=true;
            errorObject=t;
        }finally{
            if(isError){
                if(PhotoCaptureActivity.isDebug) {
                    Log.e(TAG, "Bitmap flip error " + errorObject.getMessage());
                    errorObject.printStackTrace();
                }
                errorObject=null;
            }
        }
        return null;
    }
}
