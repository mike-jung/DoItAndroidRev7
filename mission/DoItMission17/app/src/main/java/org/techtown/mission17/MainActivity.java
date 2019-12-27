package org.techtown.mission17;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    FrameLayout container;
    Animation translateIn;
    Animation translateOut;

    CustomerItemView view;
    CustomerItemView view2;

    int selected = 1;

    boolean running = false;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);

        view = new CustomerItemView(this);
        view.setName("김준수");
        view.setMobile("010-2000-2000");
        view.setAddress("서울시");
        container.addView(view);

        view2 = new CustomerItemView(this);
        view2.setName("이희선");
        view2.setMobile("010-3000-3000");
        view2.setAddress("부산시");
        container.addView(view2);

        translateIn = AnimationUtils.loadAnimation(this, R.anim.translate_in);
        translateOut = AnimationUtils.loadAnimation(this, R.anim.translate_out);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationThread thread = new AnimationThread();
                thread.start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });

    }

    class AnimationThread extends Thread {
        public void run() {
            running = true;
            while(running) {
                handler.post(new Runnable() {
                    public void run() {
                        if (selected == 0) {
                            view.startAnimation(translateIn);
                            view2.startAnimation(translateOut);
                        } else if (selected == 1) {
                            view.startAnimation(translateOut);
                            view2.startAnimation(translateIn);
                        }
                    }
                });

                selected += 1;
                if (selected > 1) {
                    selected = 0;
                }

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }
        }
    }
}
