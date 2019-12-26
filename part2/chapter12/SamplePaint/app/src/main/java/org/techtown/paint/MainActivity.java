package org.techtown.paint;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BestPaintBoard view = new BestPaintBoard(this);
        setContentView(view);
    }
}
