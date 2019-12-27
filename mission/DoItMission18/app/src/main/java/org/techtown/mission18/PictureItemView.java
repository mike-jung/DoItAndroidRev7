package org.techtown.mission18;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PictureItemView extends LinearLayout {
    TextView textView;
    TextView textView2;

    ImageView imageView;

    BitmapFactory.Options options = new BitmapFactory.Options();

    public PictureItemView(Context context) {
        super(context);

        init(context);
    }

    public PictureItemView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.picture_item, this, true);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        imageView = findViewById(R.id.imageView);

        options.inSampleSize = 12;
    }

    public void setName(String name) {
        textView.setText(name);
    }

    public void setDate(String date) {
        textView2.setText(date);
    }

    public void setImage(int resId) {
        imageView.setImageResource(resId);
    }

    public void setImage(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }
}
