package org.techtown.manager;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getServiceList();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentActivity();
            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAppList();
            }
        });

        Button button4 = findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findActivity();
            }
        });

        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

    }

    public void getServiceList() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = manager.getRunningAppProcesses();

        for (int i = 0; i < processInfoList.size(); i++) {
            ActivityManager.RunningAppProcessInfo info = processInfoList.get(i);
            println("#" + i + " -> " + info.pid + ", " + info.processName);
        }
    }

    public void getCurrentActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = manager.getRunningTasks(1);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ActivityManager.RunningTaskInfo info = taskList.get(0);
            println("Running Task -> " + info.topActivity.toString());
        }
    }

    public void getAppList() {
        PackageManager manager = getPackageManager();
        List<ApplicationInfo> appInfoList = manager.getInstalledApplications(PackageManager.GET_META_DATA);

        for (int i = 0; i < appInfoList.size(); i++) {
            ApplicationInfo info = appInfoList.get(i);
            println("#" + i + " -> " + info.loadLabel(manager).toString() + ", " + info.packageName);
        }
    }

    public void findActivity() {
        PackageManager manager = getPackageManager();

        Intent intent = new Intent(this, MainActivity.class);
        List<ResolveInfo> activityInfoList = manager.queryIntentActivities(intent, 0);

        for (int i = 0; i < activityInfoList.size(); i++) {
            ResolveInfo info = activityInfoList.get(i);
            println("#" + i + " -> " + info.activityInfo.applicationInfo.packageName);
        }
    }

    public void setAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 101, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + 60000, pendingIntent);

    }

    public void println(String data) {
        textView.append(data + "\n");
    }

}

