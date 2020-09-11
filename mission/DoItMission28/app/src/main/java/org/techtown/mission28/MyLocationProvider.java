package org.techtown.mission28;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.util.List;

public class MyLocationProvider extends AppWidgetProvider {

	static String receiver = "010-1000-1000";

	static PendingIntent sentIntent;
	static PendingIntent deliveredIntent;

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		if (sentIntent == null) {
			sentIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_SENT_ACTION"), 0);
		}

		if (deliveredIntent == null) {
			deliveredIntent = PendingIntent.getBroadcast(context, 0, new Intent("SMS_DELIVERED_ACTION"), 0);
		}

		Log.d("MyLocationProvider", "onUpdate() called.");

		final int size = appWidgetIds.length;

        for (int i = 0; i < size; i++) {
            int appWidgetId = appWidgetIds[i];

			Intent sendIntent = new Intent(context, GPSLocationService.class);
			sendIntent.putExtra("command", "send");
			sendIntent.putExtra("receiver", receiver);

            PendingIntent pendingIntent = PendingIntent.getService(context, 0, sendIntent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mylocation);
            views.setOnClickPendingIntent(R.id.button, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        Intent startIntent = new Intent(context, GPSLocationService.class);
		startIntent.putExtra("command", "start");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			context.startForegroundService(startIntent);
		} else {
			context.startService(startIntent);
		}

	}


	public static class GPSLocationService extends Service {
		public static final String TAG = "GPSLocationService";

		public static double ycoord = 0.0D;
		public static double xcoord = 0.0D;

		private LocationManager manager = null;

		private LocationListener listener = new LocationListener() {

			public void onStatusChanged(String provider, int status, Bundle extras) {
			}

			public void onProviderEnabled(String provider) {
			}

			public void onProviderDisabled(String provider) {
			}

			public void onLocationChanged(Location location) {
				Log.d(TAG, "onLocationChanged() called.");

				updateCoordinates(location.getLatitude(), location.getLongitude());

				stopSelf();
			}
		};

		BroadcastReceiver sentReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				switch(getResultCode()){
					case Activity.RESULT_OK:
						// 전송 성공
						Toast.makeText(context, "전송 완료", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						// 전송 실패
						Toast.makeText(context, "전송 실패", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						// 서비스 지역 아님
						Toast.makeText(context, "서비스 지역 아님", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						// 무선 꺼짐
						Toast.makeText(context, "무선(Radio)이 꺼져 있음", Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						// PDU 실패
						Toast.makeText(context, "PDU 실패", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		};

		BroadcastReceiver deliveredReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				switch (getResultCode()){
					case Activity.RESULT_OK:
						// 도착 완료
						Toast.makeText(context, "SMS 도착 완료", Toast.LENGTH_SHORT).show();
						break;
					case Activity.RESULT_CANCELED:
						// 도착 안됨
						Toast.makeText(context, "SMS 도착 안됨", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		};


		public IBinder onBind(Intent intent) {
			return null;
		}

		public void onCreate() {
			super.onCreate();

			Log.d(TAG, "onCreate() called.");

			manager = (LocationManager) getSystemService(LOCATION_SERVICE);

			registerReceiver(sentReceiver, new IntentFilter("SMS_SENT_ACTION"));
			registerReceiver(deliveredReceiver, new IntentFilter("SMS_DELIVERED_ACTION"));

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				final String strId = "LocatonProvider";
				final String strTitle = "LocatonProvider";
				NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				NotificationChannel channel = notificationManager.getNotificationChannel(strId);
				if (channel == null) {
					channel = new NotificationChannel(strId, strTitle, NotificationManager.IMPORTANCE_HIGH);
					notificationManager.createNotificationChannel(channel);
				}

				Notification notification = new NotificationCompat.Builder(this, strId).build();
				startForeground(1, notification);
			}

		}

		public int onStartCommand(Intent intent, int flags, int startId) {
			if (intent != null) {
				String command = intent.getStringExtra("command");
				if (command != null) {
					if (command.equals("start")) {
						startListening();
					} else if (command.equals("send")) {
						String receiver = intent.getStringExtra("receiver");

						String contents = "내 위치 : " + xcoord + ", " + ycoord;
						sendSMS(receiver, contents);
					}
				}
			}

			return super.onStartCommand(intent, flags, startId);
		}

		public void onDestroy() {
			stopListening();

			unregisterReceiver(sentReceiver);
			unregisterReceiver(deliveredReceiver);

			Log.d(TAG, "onDestroy() called.");

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				stopForeground(true);
			}

			super.onDestroy();
		}

		private void sendSMS(String receiver, String contents) {
			SmsManager mSmsManager = SmsManager.getDefault();
			mSmsManager.sendTextMessage(receiver, null, contents, sentIntent, deliveredIntent);
		}

		private void startListening() {
			Log.d(TAG, "startListening() called.");

			final Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_COARSE);
			criteria.setAltitudeRequired(false);
			criteria.setBearingRequired(false);
			criteria.setCostAllowed(true);
			criteria.setPowerRequirement(Criteria.POWER_LOW);

			final String bestProvider = manager.getBestProvider(criteria, true);

			try {
				if (bestProvider != null && bestProvider.length() > 0) {
					manager.requestLocationUpdates(bestProvider, 500, 10, listener);
				} else {
					final List<String> providers = manager.getProviders(true);

					for (final String provider : providers) {
						manager.requestLocationUpdates(provider, 500, 10, listener);
					}
				}
			} catch(SecurityException e) {
				e.printStackTrace();
			}
		}

		private void stopListening() {
			try {
				if (manager != null && listener != null) {
					manager.removeUpdates(listener);
				}

				manager = null;
			} catch (final Exception ex) {

			}
		}

		private void updateCoordinates(double latitude, double longitude) {
			Geocoder coder = new Geocoder(this);
			List<Address> addresses = null;
			String info = "";

			Log.d(TAG, "updateCoordinates() called.");

			try {
				addresses = coder.getFromLocation(latitude, longitude, 2);

				if (null != addresses && addresses.size() > 0) {
					int addressCount = addresses.get(0).getMaxAddressLineIndex();

					if (-1 != addressCount) {
						for (int index = 0; index <= addressCount; ++index) {
							info += addresses.get(0).getAddressLine(index);

							if (index < addressCount)
								info += ", ";
						}
					} else {
						info += addresses.get(0).getFeatureName() + ", "
								+ addresses.get(0).getSubAdminArea() + ", "
								+ addresses.get(0).getAdminArea();
					}
				}

				Log.d(TAG, "Address : " + addresses.get(0).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

			coder = null;
			addresses = null;

			if (info.length() <= 0) {
				info = "[내 위치] " + latitude + ", " + longitude;
			} else {
				info += ("\n" + "[내 위치] " + latitude + ", " + longitude + ")");
			}

			xcoord = longitude;
			ycoord = latitude;
			Log.d(TAG, "coordinates : " + latitude + ", " + longitude);

		}

	}

}
