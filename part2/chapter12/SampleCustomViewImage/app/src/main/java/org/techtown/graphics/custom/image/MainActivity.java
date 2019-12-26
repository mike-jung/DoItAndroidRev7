package org.techtown.graphics.custom.image;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CustomViewImage view = new CustomViewImage(this);
        setContentView(view);
    }
}
