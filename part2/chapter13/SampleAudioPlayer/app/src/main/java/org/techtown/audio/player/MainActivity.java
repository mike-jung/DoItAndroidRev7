package org.techtown.audio.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String AUDIO_URL = "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer mediaPlayer;
    int position = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                playAudio(AUDIO_URL);
                Toast.makeText(getApplicationContext(),"음악 파일 재생 시작됨.", Toast.LENGTH_LONG).show();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 중지됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 일시정지됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);
                    Toast.makeText(getApplicationContext(),"음악 파일 재생 재시작됨.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void playAudio(String url) {
        killMediaPlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        killMediaPlayer();
    }

    private void killMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
