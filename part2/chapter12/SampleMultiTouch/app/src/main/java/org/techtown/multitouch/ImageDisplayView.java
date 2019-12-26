package org.techtown.multitouch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class ImageDisplayView extends View implements View.OnTouchListener {
	private static final String TAG = "ImageDisplayView";
	
	Context mContext;
	Canvas mCanvas;
	Bitmap mBitmap;
	Paint mPaint;

	int lastX;
	int lastY;

	Bitmap sourceBitmap;
	
	Matrix mMatrix;
	
	float sourceWidth = 0.0F;
	float sourceHeight = 0.0F;
	 
	float bitmapCenterX;
	float bitmapCenterY;
	
	float scaleRatio;
	float totalScaleRatio;
	 
	float displayWidth = 0.0F;
	float displayHeight = 0.0F;
	
	int displayCenterX = 0;
	int displayCenterY = 0;

	public float startX;
    public float startY;
    
    public static float MAX_SCALE_RATIO = 5.0F;
    public static float MIN_SCALE_RATIO = 0.1F;
    
	float oldDistance = 0.0F;
	 
	int oldPointerCount = 0;
	boolean isScrolling = false;
	float distanceThreshold = 3.0F;

	public ImageDisplayView(Context context) {
		super(context);
		
		mContext = context;

		init();
	}

	public ImageDisplayView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		init();
	}

	private void init() {
		mPaint = new Paint();
        mMatrix = new Matrix();
        
		lastX = -1;
		lastY = -1;
 
		setOnTouchListener(this);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		if (w > 0 && h > 0) {
			newImage(w, h);

	        redraw();
		}
	}

	public void newImage(int width, int height) {
		Bitmap img = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas();
		canvas.setBitmap(img);
		
		mBitmap = img;
		mCanvas = canvas;

		displayWidth = (float)width;
		displayHeight = (float)height;
		 
		displayCenterX = width/2;
    	displayCenterY = height/2;
	}	
 
	public void drawBackground(Canvas canvas) {
		if (canvas != null) {
			canvas.drawColor(Color.BLACK);
		}
	}	

	protected void onDraw(Canvas canvas) {
		if (mBitmap != null) {
			canvas.drawBitmap(mBitmap, 0, 0, null);
		}
	}

    public void setImageData(Bitmap image) {
    	recycle();
    	
    	sourceBitmap = image;
    	
    	sourceWidth = sourceBitmap.getWidth();
    	sourceHeight = sourceBitmap.getHeight();
    	 
    	bitmapCenterX = sourceBitmap.getWidth()/2;
    	bitmapCenterY = sourceBitmap.getHeight()/2;
        
        scaleRatio = 1.0F;
        totalScaleRatio = 1.0F;
    }
    
    public void recycle() {
    	if (sourceBitmap != null) {
    		sourceBitmap.recycle();
    	}
    }
    
	public void redraw() {
		if (sourceBitmap == null) {
			Log.d(TAG, "sourceBitmap is null in redraw().");
			return;
		}
		
		drawBackground(mCanvas);
		
		float originX = (displayWidth - (float)sourceBitmap.getWidth()) / 2.0F;
		float originY = (displayHeight - (float)sourceBitmap.getHeight()) / 2.0F;
		
		mCanvas.translate(originX, originY);
        mCanvas.drawBitmap(sourceBitmap, mMatrix, mPaint);
        mCanvas.translate(-originX, -originY);

		invalidate();
	}
	

	public boolean onTouch(View v, MotionEvent ev) {
        final int action = ev.getAction();
        
        int pointerCount = ev.getPointerCount();
        Log.d(TAG, "Pointer Count : " + pointerCount);
        
        switch (action) {
        	case MotionEvent.ACTION_DOWN:
        		 
        		if (pointerCount == 1) {
    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
        			startX = curX;
                    startY = curY;
                     
        		} else if (pointerCount == 2) {
        			oldDistance = 0.0F;
        			
        			isScrolling = true;
        		}
        		
        		return true;
        	case MotionEvent.ACTION_MOVE:
        		
    	    	if (pointerCount == 1) {

    	    		if (isScrolling) {
    	    			return true;
    	    		}
    	    		
    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
    	    		if (startX == 0.0F) {
    	    			startX = curX;
    	                startY = curY;
    	    			
    	    			return true;
    	    		}
    	    		
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;

                	if (oldPointerCount == 2) {
                		
                	} else {
                		Log.d(TAG, "ACTION_MOVE : " + offsetX + ", " + offsetY);

                		if (totalScaleRatio > 1.0F) {
                			moveImage(-offsetX, -offsetY);
                		}
                		
    	                startX = curX;
    	                startY = curY;
                	}
	    		
    	    	} else if (pointerCount == 2) {
    	    		
    	    		float x1 = ev.getX(0);
    	    		float y1 = ev.getY(0);
    	    		float x2 = ev.getX(1);
    	    		float y2 = ev.getY(1);
    	    		
    	    		float dx = x1 - x2;
    	    		float dy = y1 - y2;
    	    		float distance = new Double(Math.sqrt(new Float(dx * dx + dy * dy).doubleValue())).floatValue();
    	    		
    	    		float outScaleRatio = 0.0F;
    	    		if (oldDistance == 0.0F) {
    	    			oldDistance = distance;
    	    			
    	    			break;
    	    		}
    	    		
    	    		if (distance > oldDistance) {
    	    			if ((distance-oldDistance) < distanceThreshold) {
    	    				return true;
    	    			}

    	    			outScaleRatio = scaleRatio + (oldDistance / distance * 0.05F);
    	    		} else if (distance < oldDistance) { 
    	    			if ((oldDistance-distance) < distanceThreshold) {
    	    				return true;
    	    			}

    	    			outScaleRatio = scaleRatio - (distance / oldDistance * 0.05F);
    	    		}

    	    		if (outScaleRatio < MIN_SCALE_RATIO || outScaleRatio > MAX_SCALE_RATIO) {
                        Log.d(TAG, "Invalid scaleRatio : " + outScaleRatio);
                    } else {
                        Log.d(TAG, "Distance : " + distance + ", ScaleRatio : " + outScaleRatio);
                        scaleImage(outScaleRatio);
                    }
    	    		
    	    		oldDistance = distance;
    	    	}
        		
    	    	oldPointerCount = pointerCount;
    	    	
        		break;
        		
        	case MotionEvent.ACTION_UP:
        		
        		if (pointerCount == 1) {

    	    		float curX = ev.getX();
    	    		float curY = ev.getY();
    	    		
                    float offsetX = startX - curX;
                    float offsetY = startY - curY;
                    
                    if (oldPointerCount == 2) {
                    		 
                	} else {
                		moveImage(-offsetX, -offsetY);
                	}
	    	 
        		} else {
        			isScrolling = false;
        		}
        		
            	return true;
        }
        
        return true;
	 }

     private void scaleImage(float inScaleRatio) {
    	Log.d(TAG, "scaleImage() called : " + inScaleRatio);
    	
    	mMatrix.postScale(inScaleRatio, inScaleRatio, bitmapCenterX, bitmapCenterY);
    	mMatrix.postRotate(0);

    	totalScaleRatio = totalScaleRatio * inScaleRatio;

    	redraw();
     }

     private void moveImage(float offsetX, float offsetY) {
    	Log.d(TAG, "moveImage() called : " + offsetX + ", " + offsetY);
    	
    	mMatrix.postTranslate(offsetX, offsetY);
    	
    	redraw();
     }

}