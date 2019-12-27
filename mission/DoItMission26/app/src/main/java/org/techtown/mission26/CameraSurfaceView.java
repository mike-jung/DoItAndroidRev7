package org.techtown.mission26;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


/**
 * Camera Preview
 *
 * @author Mike
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera camera = null;

    public static final String TAG = "CameraSurfaceView";

    int surfaceWidth;
    int surfaceHeight;

    int bitmapWidth = 0;
    int bitmapHeight = 0;


    public CameraSurfaceView(Context context) {
        super(context);

        init();
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    /**
     * surfaceCreated defined in Callback
     */
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();

        try {
            camera.setPreviewDisplay(mHolder);

        } catch (Exception e) {
            Log.e(TAG, "Camera Preview setting failed.", e);
        }
    }

    /**
     * surfaceChanged defined in Callback
     */
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    	surfaceWidth = width;
    	surfaceHeight = height;

    	// rotate preview display for several devices
    	try {
    		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
    			camera.setDisplayOrientation(90);
    		} else {
    			Parameters parameters = camera.getParameters();
    			parameters.setRotation(90);
    			camera.setParameters(parameters);
    		}

    		camera.setPreviewDisplay(holder);
    	} catch (IOException exception) {
    		camera.release();
    	}


    	camera.startPreview();
    }

    /**
     * surfaceDestroyed defined in Callback
     */
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.setPreviewCallback(null);
        camera.release();

        camera = null;
    }

}

