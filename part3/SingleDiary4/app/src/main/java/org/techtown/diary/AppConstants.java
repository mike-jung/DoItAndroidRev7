package org.techtown.diary;

import android.os.Handler;
import android.util.Log;

import java.text.SimpleDateFormat;

public class AppConstants {

    public static final int REQ_LOCATION_BY_ADDRESS = 101;
    public static final int REQ_WEATHER_BY_GRID = 102;

    public static final int REQ_PHOTO_CAPTURE = 103;
    public static final int REQ_PHOTO_SELECTION = 104;

    public static final int CONTENT_PHOTO = 105;
    public static final int CONTENT_PHOTO_EX = 106;

    public static String FOLDER_PHOTO;

    public static String DATABASE_NAME = "note.db";

    public static final int MODE_INSERT = 1;
    public static final int MODE_MODIFY = 2;


    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
    public static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH");
    public static SimpleDateFormat dateFormat3 = new SimpleDateFormat("MM-dd");
    public static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");


    private static Handler handler = new Handler();
    private static final String TAG = "AppConstants";
    public static void println(final String data) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, data);
            }
        });
    }

}
