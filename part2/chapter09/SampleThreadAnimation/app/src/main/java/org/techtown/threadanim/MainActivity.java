package org.techtown.threadanim;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;

    ArrayList<Drawable> drawableList = new ArrayList<Drawable>();
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        drawableList.add(res.getDrawable(R.drawable.face1));
        drawableList.add(res.getDrawable(R.drawable.face2));
        drawableList.add(res.getDrawable(R.drawable.face3));
        drawableList.add(res.getDrawable(R.drawable.face4));
        drawableList.add(res.getDrawable(R.drawable.face5));

        imageView = findViewById(R.id.imageView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });
    }

    class AnimThread extends Thread {
        public void run() {
            int index = 0;
            for (int i = 0; i < 100; i++) {
                final Drawable drawable = drawableList.get(index);
                index += 1;
                if (index > 4) {
                    index = 0;
                }

                handler.post(new Runnable() {
                    public void run() {
                        imageView.setImageDrawable(drawable);
                    }
                });

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
