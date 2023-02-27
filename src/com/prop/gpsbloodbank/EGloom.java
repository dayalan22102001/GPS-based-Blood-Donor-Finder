package com.prop.gpsbloodbank;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class EGloom extends Activity {

	int splashTime = 2000;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.splash);
		Thread thread = new Thread() {
			public void run() {
				try {
					synchronized (this) {
						wait(splashTime);
					}

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					startActivity(new Intent(EGloom.this, Menupage.class));
					finish();
				}
			}
		};
		thread.start();

	}

}