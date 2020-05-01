package org.techtown.location.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

public class MyLocationProvider extends AppWidgetProvider {

	public static double ycoord = 0.0D;
	public static double xcoord = 0.0D;


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

		Log.d("MyLocationProvider", "onUpdate() called : " + ycoord + ", " + xcoord);

		final int size = appWidgetIds.length;

        for (int i = 0; i < size; i++) {
            int appWidgetId = appWidgetIds[i];

            //String uri = "geo:"+ ycoord + "," + xcoord + "?z=10";
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            
            
            String uriBegin = "geo:" + ycoord + "," + xcoord;
            String query = ycoord + "," + xcoord + "(" + "내위치" + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery + "&z=15";
            Uri uri = Uri.parse(uriString);
            
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            
            
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.mylocation);
            views.setOnClickPendingIntent(R.id.txtInfo, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        context.startService(new Intent(context,GPSLocationService.class));
	}


	public static class GPSLocationService extends Service {
		public static final String TAG = "GPSLocationService";

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

		public IBinder onBind(Intent intent) {
			return null;
		}

		public void onCreate() {
			super.onCreate();

			Log.d(TAG, "onCreate() called.");

			manager = (LocationManager) getSystemService(LOCATION_SERVICE);

		}

		public int onStartCommand(Intent intent, int flags, int startId) {
			startListening();

			return super.onStartCommand(intent, flags, startId);
		}

		public void onDestroy() {
			stopListening();

			Log.d(TAG, "onDestroy() called.");

			super.onDestroy();
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
				info = "[내 위치] " + latitude + ", " + longitude
						+ "\n터치하면 지도로 볼 수 있습니다.";
			} else {
				info += ("\n" + "[내 위치] " + latitude + ", " + longitude + ")");
				info += "\n터치하면 지도로 볼 수 있습니다.";
			}

			RemoteViews views = new RemoteViews(getPackageName(), R.layout.mylocation);

			views.setTextViewText(R.id.txtInfo, info);

			ComponentName thisWidget = new ComponentName(this, MyLocationProvider.class);
			AppWidgetManager manager = AppWidgetManager.getInstance(this);
			manager.updateAppWidget(thisWidget, views);

			xcoord = longitude;
			ycoord = latitude;
			Log.d(TAG, "coordinates : " + latitude + ", " + longitude);

		}
	}

}
