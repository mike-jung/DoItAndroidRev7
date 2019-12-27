package org.techtown.mission23;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ColorPaletteDialog extends Activity {

	GridView grid;
	Button closeBtn;
	ColorDataAdapter adapter;

	public static OnColorSelectedListener listener;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);

        this.setTitle("Color Selection");

        grid = findViewById(R.id.colorGrid);
        closeBtn = findViewById(R.id.closeBtn);

        grid.setColumnWidth(14);
        grid.setBackgroundColor(Color.GRAY);
        grid.setVerticalSpacing(4);
        grid.setHorizontalSpacing(4);

        adapter = new ColorDataAdapter(this);
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
 * Adapter for Color Data
 *
 * @author Mike
 */
class ColorDataAdapter extends BaseAdapter {

	/**
	 * Application Context
	 */
	Context mContext;

	/**
	 * Colors defined
	 */
    public static final int [] colors = new int[] {
        0xff000000,0xff00007f,0xff0000ff,0xff007f00,0xff007f7f,0xff00ff00,0xff00ff7f,
        0xff00ffff,0xff7f007f,0xff7f00ff,0xff7f7f00,0xff7f7f7f,0xffff0000,0xffff007f,
        0xffff00ff,0xffff7f00,0xffff7f7f,0xffff7fff,0xffffff00,0xffffff7f,0xffffffff
    };

	int rowCount;
	int columnCount;



	public ColorDataAdapter(Context context) {
		super();

		mContext = context;

		// create test data
		rowCount = 3;
		columnCount = 7;

	}

	public int getNumColumns() {
		return columnCount;
	}

	public int getCount() {
		return rowCount * columnCount;
	}

	public Object getItem(int position) {
		return colors[position];
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View view, ViewGroup parent) {
		Log.d("ColorDataAdapter", "getView(" + position + ") called.");

		// calculate position
		int rowIndex = position / rowCount;
		int columnIndex = position % rowCount;
		Log.d("ColorDataAdapter", "Index : " + rowIndex + ", " + columnIndex);

		GridView.LayoutParams params = new GridView.LayoutParams(
				GridView.LayoutParams.MATCH_PARENT,
				GridView.LayoutParams.MATCH_PARENT);

		// create a Button with the color
		Button aItem = new Button(mContext);
		aItem.setText(" ");
		aItem.setLayoutParams(params);
		aItem.setPadding(4, 4, 4, 4);
		aItem.setBackgroundColor(colors[position]);
		aItem.setHeight(parent.getHeight()/3);
		aItem.setTag(colors[position]);

		// set listener
		aItem.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (ColorPaletteDialog.listener != null) {
					ColorPaletteDialog.listener.onColorSelected(((Integer)v.getTag()).intValue());
				}

				((ColorPaletteDialog)mContext).finish();
			}
		});

		return aItem;
	}
}


