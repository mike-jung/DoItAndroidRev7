package org.techtown.video.player;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String VIDEO_URL = "https://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoURI(Uri.parse(VIDEO_URL));
                videoView.requestFocus();
                videoView.start();
            }
        });

    }
}
