package org.techtown.mission23;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class PenPaletteDialog extends Activity {

	GridView grid;
	Button closeBtn;
	PenDataAdapter adapter;

	public static OnPenSelectedListener listener;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        this.setTitle("Pen Selection");

        grid = findViewById(R.id.colorGrid);
        closeBtn = findViewById(R.id.closeBtn);

        grid.setColumnWidth(14);
        grid.setBackgroundColor(Color.GRAY);
        grid.setVerticalSpacing(4);
        grid.setHorizontalSpacing(4);

        adapter = new PenDataAdapter(this);
        grid.setAdapter(adapter);
        grid.setNumColumns(adapter.getNumColumns());

        closeBtn.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		// dispose this activity
        		finish();
        	}
        });

	}

}

/**
 * Adapter for Pen Data
 */
class PenDataAdapter extends BaseAdapter {

	/**
	 * Application Context
	 */
	Context mContext;

	/**
	 * Pens defined
	 */
    public static final int [] pens = new int[] {
        1,2,3,4,5,
        6,7,8,9,10,
        11,13,15,17,20
    };

	int rowCount;
	int columnCount;



	public PenDataAdapter(Context context) {
		super();

		mContext = context;

		rowCount = 3;
		columnCount = 5;

	}

	public int getNumColumns() {
		return columnCount;
	}

	public int getCount() {
		return rowCount * columnCount;
	}

	public Object getItem(int position) {
		return pens[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		Log.d("PenDataAdapter", "getView(" + position + ") called.");

		// calculate position
		int rowIndex = position / rowCount;
		int columnIndex = position % rowCount;
		Log.d("PenDataAdapter", "Index : " + rowIndex + ", " + columnIndex);

		GridView.LayoutParams params = new GridView.LayoutParams(
				GridView.LayoutParams.MATCH_PARENT,
				GridView.LayoutParams.MATCH_PARENT);

		// create a Pen Image
		int areaWidth = 10;
		int areaHeight = 20;

		Bitmap penBitmap = Bitmap.createBitmap(areaWidth, areaHeight, Bitmap.Config.ARGB_8888);
		Canvas penCanvas = new Canvas();
		penCanvas.setBitmap(penBitmap);

		Paint mPaint = new Paint();
		mPaint.setColor(Color.WHITE);
		penCanvas.drawRect(0, 0, areaWidth, areaHeight, mPaint);

		mPaint.setColor(Color.BLACK);
		mPaint.setStrokeWidth((float)pens[position]);
		penCanvas.drawLine(0, areaHeight/2, areaWidth-1, areaHeight/2, mPaint);
		BitmapDrawable penDrawable = new BitmapDrawable(mContext.getResources(), penBitmap);

		// create a Button with the color
		Button aItem = new Button(mContext);
		aItem.setText(" ");
		aItem.setLayoutParams(params);
		aItem.setPadding(4, 4, 4, 4);
		aItem.setBackgroundDrawable(penDrawable);
		aItem.setHeight(parent.getHeight()/3);
		aItem.setTag(pens[position]);

		// set listener
		aItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (PenPaletteDialog.listener != null) {
					PenPaletteDialog.listener.onPenSelected(((Integer)v.getTag()).intValue());
				}

				((PenPaletteDialog)mContext).finish();
			}
		});

		return aItem;
	}
}


