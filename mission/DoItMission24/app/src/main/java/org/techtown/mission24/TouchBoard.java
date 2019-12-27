package org.techtown.mission24;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class TouchBoard extends View {
    private Paint paint;
    private Paint basePaint;

    int rectWidth = 200;
    int rectHeight = 200;
    int curX = 100;
    int curY = 100;

    public TouchBoard(Context context) {
        super(context);

        init(context);
    }

    public TouchBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        basePaint = new Paint();
        basePaint.setStyle(Paint.Style.FILL);
        basePaint.setColor(Color.WHITE);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), getHeight(), basePaint);
        canvas.drawRect(curX, curY, curX + rectWidth, curY + rectHeight, paint);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            curX = (int) event.getX();
            curY = (int) event.getY();

            invalidate();
        } else if (action == MotionEvent.ACTION_MOVE) {
            curX = (int) event.getX();
            curY = (int) event.getY();

            invalidate();
        } else if (action == MotionEvent.ACTION_UP) {

        }

        return true;
    }

}
