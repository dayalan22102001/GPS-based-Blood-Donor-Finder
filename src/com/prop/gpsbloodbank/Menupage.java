package com.prop.gpsbloodbank;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class Menupage extends TabActivity {
	/** Called when the activity is first created. */
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		@SuppressWarnings("deprecation")
		TabHost tabHost = getTabHost();

		// Tab for Photos
		TabSpec photospec = tabHost.newTabSpec("Admin Login");
		photospec.setIndicator("Blood Donar Register");
		Intent photosIntent = new Intent(this, BloodRegister.class);
		photospec.setContent(photosIntent);

		// Tab for Songs
		TabSpec songspec = tabHost.newTabSpec("User Login");
		// setting Title and Icon for the Tab
		songspec.setIndicator("User Search Login");
		Intent songsIntent = new Intent(this, UserLogin.class);
		songspec.setContent(songsIntent);

		// Tab for Videos

		// Adding all TabSpec to TabHost
		tabHost.addTab(photospec); // Adding photos tab
		tabHost.addTab(songspec); // Adding songs tab
		// tabHost.addTab(videospec); // Adding videos tab
	}
}