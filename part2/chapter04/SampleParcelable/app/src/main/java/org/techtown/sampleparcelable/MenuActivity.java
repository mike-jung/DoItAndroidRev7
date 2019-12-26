package org.techtown.sampleparcelable;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    TextView textView;
    public static final String KEY_SIMPLE_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "mike");
                setResult(RESULT_OK, intent);

                finish();
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    private void processIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            SimpleData data = bundle.getParcelable(KEY_SIMPLE_DATA);
            if (data != null) {
                textView.setText("전달 받은 데이터\nNumber : " + data.number
                        + "\nMessage : " + data.message);
            }
        }
    }

}