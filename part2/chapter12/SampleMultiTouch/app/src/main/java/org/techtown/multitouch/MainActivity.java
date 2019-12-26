package org.techtown.multitouch;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.container);

        Resources res = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.beach);

        ImageDisplayView view = new ImageDisplayView(this);
        view.setImageData(bitmap);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        container.addView(view, params);
    }

}
