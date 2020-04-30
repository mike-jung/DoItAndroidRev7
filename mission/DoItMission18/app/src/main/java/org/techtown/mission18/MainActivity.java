package org.techtown.mission18;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    FrameLayout container;
    Animation translateIn;

    PictureItemView view;

    TextView textView;

    int selected = 0;

    boolean running = false;

    Handler handler = new Handler();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int pictureCount = 0;

    ArrayList<ImageInfo> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        textView = findViewById(R.id.textView);

        view = new PictureItemView(this);
        view.setName("사진이름");
        view.setDate("일시");
        container.addView(view);

        translateIn = AnimationUtils.loadAnimation(this, R.anim.translate_in);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictures = queryAllPictures();

                AnimationThread thread = new AnimationThread();
                thread.start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                selected = 0;

                view.clearAnimation();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
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

    class AnimationThread extends Thread {
        public void run() {
            running = true;
            while(running) {
                handler.post(new Runnable() {
                    public void run() {
                        if (selected < pictures.size()) {
                            ImageInfo info = pictures.get(selected);

                            view.setName(info.getDisplayName());
                            view.setDate(info.getDateAdded());
                            view.setImage(info.getPath());

                            view.startAnimation(translateIn);

                            selected += 1;
                            textView.setText(selected + " / " + pictureCount + " 개");

                        } else {
                            selected = 0;
                        }

                    }
                });

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
            }

            running = false;
        }
    }

    private ArrayList<ImageInfo> queryAllPictures() {
        pictureCount = 0;

        ArrayList<ImageInfo> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        while (cursor.moveToNext()) {
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));

            if (!TextUtils.isEmpty(path)) {
                ImageInfo info = new ImageInfo(path, displayName, addedDate);
                result.add(info);
            }

            pictureCount++;
        }

        textView.setText(selected + " / " + pictureCount + " 개");
        Log.d("MainActivity", "Picture count : " + pictureCount);

        for (ImageInfo info : result) {
            Log.d("MainActivity", info.toString());
        }

        return result;
    }

}
