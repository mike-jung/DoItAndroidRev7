package org.techtown.http;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    Handler handler = new Handler();

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
                final String urlStr = editText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });
    }

    public void request(String urlStr) {
        StringBuilder output = new StringBuilder();
        try {
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while (true) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }

                    output.append(line + "\n");
                }
                reader.close();
                conn.disconnect();
            }
        } catch (Exception ex) {
            println("예외 발생함 : " + ex.toString());
        }

        println("응답 -> " + output.toString());
    }

    public void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.append(data + "\n");
            }
        });

    }

}
