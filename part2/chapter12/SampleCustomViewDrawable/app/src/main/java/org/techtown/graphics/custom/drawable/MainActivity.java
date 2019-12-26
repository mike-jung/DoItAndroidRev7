package org.techtown.graphics.custom.drawable;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomViewDrawable view = new CustomViewDrawable(this);
        setContentView(view);
    }
}
