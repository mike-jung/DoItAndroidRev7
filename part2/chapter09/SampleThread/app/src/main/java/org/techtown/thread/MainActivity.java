package org.techtown.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    int value = 0;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundThread thread = new BackgroundThread();
                thread.start();
            }
        });

    }

    class BackgroundThread extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1000);
                } catch(Exception e) {}

                value += 1;
                Log.d("Thread", "value : " + value);

                textView.setText("value 값 : " + value);
            }
        }
    }

}
