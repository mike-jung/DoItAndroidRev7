package org.techtown.graphics.custom.style;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomViewStyle view = new CustomViewStyle(this);
        setContentView(view);
    }
}
