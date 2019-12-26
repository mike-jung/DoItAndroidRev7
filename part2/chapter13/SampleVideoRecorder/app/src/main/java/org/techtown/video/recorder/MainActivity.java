package org.techtown.video.recorder;

import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    MediaPlayer player;
    MediaRecorder recorder;

    String filename;

    SurfaceHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SurfaceView surface = new SurfaceView(this);
        holder = surface.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        FrameLayout frame = findViewById(R.id.container);
        frame.addView(surface);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopPlay();
            }
        });

        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename = sdcard + File.separator + "recorded.mp4";

        AutoPermissions.Companion.loadAllPermissions(this, 101);

    }

    public void startRecording() {
        if (recorder == null) {
            recorder = new MediaRecorder();
        }

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        recorder.setOutputFile(filename);

        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();

            recorder.release();
            recorder = null;
        }
    }

    public void stopRecording() {
        if (recorder == null) {
            return;
        }

        recorder.stop();
        recorder.reset();
        recorder.release();
        recorder = null;

        ContentValues values = new ContentValues(10);

        values.put(MediaStore.MediaColumns.TITLE, "RecordedVideo");
        values.put(MediaStore.Audio.Media.ALBUM, "Video Album");
        values.put(MediaStore.Audio.Media.ARTIST, "Mike");
        values.put(MediaStore.Audio.Media.DISPLAY_NAME, "Recorded Video");
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Audio.Media.DATA, filename);

        Uri videoUri = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
        if (videoUri == null) {
            Log.d("SampleVideoRecorder", "Video insert failed.");
            return;
        }

        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, videoUri));

    }

    public void startPlay() {
        if (player == null) {
            player = new MediaPlayer();
        }

        try {
            player.setDataSource(filename);
            player.setDisplay(holder);

            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlay() {
        if (player == null) {
            return;
        }

        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }

}
