package com.kit.photocapture;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CascadeLoader {
    private static CascadeLoader mCascadeLoader = null;
    private static Object syncObject = new Object();

    private String TAG = "CascadeLoader";
    private Context mContext = null;


    private CascadeLoader(Context mContext) {
        this.mContext = mContext;
    }

    public static CascadeLoader getInstance(Context context){
        synchronized (syncObject){
            if(mCascadeLoader==null){
                mCascadeLoader = new CascadeLoader(context);
            }
        }
        return mCascadeLoader;
    }

    public File loadModel(int resId,String fileName){
        File cascadeFile = null;
        try {
            InputStream is = mContext.getResources().openRawResource(resId);
            if (is == null) {
                return null;
            }
            File cascadeDir = mContext.getDir("model", Context.MODE_PRIVATE);
            cascadeFile = new File(cascadeDir, fileName);
            FileOutputStream os = new FileOutputStream(cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
        }catch (IOException ioe){
            Log.e(TAG,"Error occurred while loading cascade file "+ioe.getMessage());
            ioe.printStackTrace();
        }catch (Exception exc){
            Log.e(TAG,"Error occurred while loading cascade file "+exc.getMessage());
            exc.printStackTrace();
        }catch(Throwable t){
            Log.e(TAG,"Error occurred while loading cascade file "+t.getMessage());
            t.printStackTrace();
        }

        return cascadeFile;
    }
    public byte[] loadModelBuffer(int resId){
        byte[] nowData = null;
        try {
            InputStream is = mContext.getResources().openRawResource(resId);
            if (is == null) {
                return null;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }

            nowData = baos.toByteArray();

            is.close();
            baos.close();
        }catch (IOException ioe){
            Log.e(TAG,"Error occurred while loading cascade file "+ioe.getMessage());
            ioe.printStackTrace();
        }catch (Exception exc){
            Log.e(TAG,"Error occurred while loading cascade file "+exc.getMessage());
            exc.printStackTrace();
        }catch(Throwable t){
            Log.e(TAG,"Error occurred while loading cascade file "+t.getMessage());
            t.printStackTrace();
        }

        return nowData;
    }

    public void deleteModelDir(){
        try {
            File cascadeDir = mContext.getDir("model", Context.MODE_PRIVATE);
            cascadeDir.delete();
        }catch (Exception exc){
            Log.e(TAG,"Error occurred while loading cascade file "+exc.getMessage());
            exc.printStackTrace();
        }catch(Throwable t){
            Log.e(TAG,"Error occurred while loading cascade file "+t.getMessage());
            t.printStackTrace();
        }

    }
}
