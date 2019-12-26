package org.techtown.looper;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

    ProcessThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString();
                Message message = Message.obtain();
                message.obj = input;

                thread.processHandler.sendMessage(message);
            }
        });

        thread = new ProcessThread();
    }

    class ProcessThread extends Thread {
        ProcessHandler processHandler = new ProcessHandler();

        public void run() {
            Looper.prepare();
            Looper.loop();
        }

        class ProcessHandler extends Handler {
            public void handleMessage(Message msg) {
                final String output = msg.obj + " from thread.";

                handler.post(new Runnable() {
                    public void run() {
                        textView.setText(output);
                    }
                });
            }
        }
    }

}
