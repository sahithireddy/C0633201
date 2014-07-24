package com.vijay.promoteurnotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends FragmentActivity {
	private String lat, lon;
	private LocationManager locationManager = null;
	boolean isNetworkEnabled = false;
	private GoogleMap googleMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewactivity);
		lat = getIntent().getExtras().getString("lat");
		lon = getIntent().getExtras().getString("lon");
		if (lat != null && lon != null && (!lat.equalsIgnoreCase("")) &&(!lon.equalsIgnoreCase(""))) {
			showCurrentMapWithLocationCLient();
		}
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					// System.out.println("INTERNET:" + String.valueOf(i));
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						// System.out.println("INTERNET:" + "connected!");
						return true;
					}
				}
			}
		}
		return false;
	}

	private void showCurrentMapWithLocationCLient() {
		if (isNetworkAvailable(MapViewActivity.this)) {
			GooglePlayServicesUtil
					.getOpenSourceSoftwareLicenseInfo(MapViewActivity.this);
			int status = GooglePlayServicesUtil
					.isGooglePlayServicesAvailable(getBaseContext());

			// Showing status
			if (status != ConnectionResult.SUCCESS) { // Google Play Services
														// are
														// not available
				int requestCode = 10;
				Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
						getParent(), requestCode);
				dialog.show();

			} else {
				SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map);
				googleMap = fm.getMap();
				googleMap.setMyLocationEnabled(true);
				// get current location lat , lon values;

				// Location loc=googleMap.getMyLocation();

				if (isLocationServiceEnable()) {
					// locationList =
					// getLocationDetails(LocationViewActivity.this);
					LatLng point = new LatLng(Double.parseDouble(lat),
							Double.parseDouble(lon));
					// Drawing the marker at the coordinates
					drawMarker(point);

				} else {
					showSettingsAlert();
				}
			}
		} else {
			Toast.makeText(MapViewActivity.this, "Network is not available",
					Toast.LENGTH_LONG).show();
		}
	}

	private void drawMarker(LatLng point) {
		// Clears all the existing coordinates
		googleMap.clear();

		// Creating an instance of MarkerOptions
		MarkerOptions markerOptions = new MarkerOptions();

		// Setting latitude and longitude for the marker
		markerOptions.position(point);

		// Setting title for the InfoWindow
		markerOptions.title("Position");

		// Setting InfoWindow contents
		markerOptions.snippet("Latitude:" + point.latitude + ",Longitude"
				+ point.longitude);

		// Adding marker on the Google Map
		googleMap.addMarker(markerOptions);

		// Moving CameraPosition to the user input coordinates
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(point));

		// Setting the zoom level
		googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

	}

	private Boolean isLocationServiceEnable() {
		locationManager = (LocationManager) MapViewActivity.this
				.getSystemService(LOCATION_SERVICE);

		isNetworkEnabled = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

		if (isNetworkEnabled) {
			return true;
		} else {
			return false;
			// showSettingsAlert();
		}
	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				MapViewActivity.this);

		alertDialog.setTitle("Settings");
		alertDialog
				.setMessage("Enable Use wireless Networks, Do you want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						MapViewActivity.this.startActivity(intent);
						dialog.cancel();
					}
				});
		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}

}
