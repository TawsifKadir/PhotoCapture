package com.kit.photocapture;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.opencv.android.JavaCameraView;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PhotoCaptureView extends JavaCameraView implements PictureCallback {

    private static final String TAG = "PhotoCaptureView";
    private PictureDataCallback mPictureDataCallback;

    public PhotoCaptureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPictureDataCallback = null;
    }

    public List<String> getEffectList() {
        return mCamera.getParameters().getSupportedColorEffects();
    }

    public boolean isEffectSupported() {
        return (mCamera.getParameters().getColorEffect() != null);
    }

    public String getEffect() {
        return mCamera.getParameters().getColorEffect();
    }

    public void setEffect(String effect) {
        Camera.Parameters params = mCamera.getParameters();
        params.setColorEffect(effect);
        mCamera.setParameters(params);
    }

    public List<Size> getResolutionList() {
        return mCamera.getParameters().getSupportedPreviewSizes();
    }

    public void setResolution(Size resolution) {
        disconnectCamera();
        mMaxHeight = resolution.height;
        mMaxWidth = resolution.width;
        connectCamera(getWidth(), getHeight());
    }

    public Size getResolution() {
        return mCamera.getParameters().getPreviewSize();
    }

    public PictureDataCallback getPictureDataCallback() {
        return mPictureDataCallback;
    }

    public void setPictureDataCallback(PictureDataCallback mPictureDataCallback) {
        this.mPictureDataCallback = mPictureDataCallback;
    }

    public void takePicture() {
        if(PhotoCaptureActivity.isDebug)
            Log.i(TAG, "Taking picture");
        mCamera.setPreviewCallback(null);
        mCamera.takePicture(null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if(PhotoCaptureActivity.isDebug)
            Log.i(TAG, "Saving a bitmap to file");
        // The camera preview was automatically stopped. Start it again.
        if(mPictureDataCallback!=null){
          mPictureDataCallback.onPictureData(data);
        }

    }

    public void startPreview(){
        if(mCamera!=null) {
            mCamera.startPreview();
            mCamera.setPreviewCallback(this);
        }
    }

    public void stopPreview(){
        if(mCamera!=null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
        }
    }
}

