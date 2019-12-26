package org.techtown.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

public class BestPaintBoard extends View {

    public boolean changed = false;

    Canvas mCanvas;
    Bitmap mBitmap;
    Paint mPaint;

    float lastX;
    float lastY;

    Path mPath = new Path();

    float mCurveEndX;
    float mCurveEndY;

    int mInvalidateExtraBorder = 10;

    static final float TOUCH_TOLERANCE = 8;

    public BestPaintBoard(Context context) {
        super(context);

        init(context);
    }

    public BestPaintBoard(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3.0F);

        this.lastX = -1;
        this.lastY = -1;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        Bitmap img = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(img);
        canvas.drawColor(Color.WHITE);

        mBitmap = img;
        mCanvas = canvas;

    }

    protected void onDraw(Canvas canvas) {
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_UP:
                changed = true;

                Rect rect = touchUp(event, false);
                if (rect != null) {
                    invalidate(rect);
                }

                mPath.rewind();

                return true;

            case MotionEvent.ACTION_DOWN:
                rect = touchDown(event);
                if (rect != null) {
                    invalidate(rect);
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                rect = touchMove(event);
                if (rect != null) {
                    invalidate(rect);
                }

                return true;
        }

        return false;
    }

    private Rect touchDown(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        lastX = x;
        lastY = y;

        Rect mInvalidRect = new Rect();
        mPath.moveTo(x, y);

        final int border = mInvalidateExtraBorder;
        mInvalidRect.set((int) x - border, (int) y - border, (int) x + border, (int) y + border);

        mCurveEndX = x;
        mCurveEndY = y;

        mCanvas.drawPath(mPath, mPaint);

        return mInvalidRect;
    }

    private Rect touchMove(MotionEvent event) {
        Rect rect = processMove(event);

        return rect;
    }

    private Rect touchUp(MotionEvent event, boolean cancel) {
        Rect rect = processMove(event);

        return rect;
    }

    private Rect processMove(MotionEvent event) {

        final float x = event.getX();
        final float y = event.getY();

        final float dx = Math.abs(x - lastX);
        final float dy = Math.abs(y - lastY);

        Rect mInvalidRect = new Rect();
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            final int border = mInvalidateExtraBorder;
            mInvalidRect.set((int) mCurveEndX - border, (int) mCurveEndY - border,
                    (int) mCurveEndX + border, (int) mCurveEndY + border);

            float cX = mCurveEndX = (x + lastX) / 2;
            float cY = mCurveEndY = (y + lastY) / 2;

            mPath.quadTo(lastX, lastY, cX, cY);

            mInvalidRect.union((int) lastX - border, (int) lastY - border,
                    (int) lastX + border, (int) lastY + border);

            mInvalidRect.union((int) cX - border, (int) cY - border,
                    (int) cX + border, (int) cY + border);

            lastX = x;
            lastY = y;

            mCanvas.drawPath(mPath, mPaint);
        }

        return mInvalidRect;
    }


}
