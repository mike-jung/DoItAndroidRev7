package org.techtown.mission11;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {
    public MyService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {
            String command = intent.getStringExtra("command");
            if (command != null) {
                if (command.equals("show")) {
                    String data = intent.getStringExtra("data");

                    sendToActivity(data);
                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void sendToActivity(String data) {
        Intent activityIntent = new Intent();
        activityIntent.setAction("org.techtown.broadcast.SHOW");
        activityIntent.putExtra("command", "show");
        activityIntent.putExtra("data", data);

        sendBroadcast(activityIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
