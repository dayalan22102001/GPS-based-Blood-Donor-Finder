package com.prop.gpsbloodbank;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Cal extends Activity {
	Button call, sms;
	String call1, getcal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cal);
		call = (Button) findViewById(R.id.button1);
		sms = (Button) findViewById(R.id.button2);
		Intent intent = getIntent();
		call1 = intent.getStringExtra("name");
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					// set the data
					String uri = "tel:" + call1;
					Intent callIntent = new Intent(Intent.ACTION_CALL, Uri
							.parse(uri));

					startActivity(callIntent);
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Your call has failed...", Toast.LENGTH_LONG)
							.show();
					e.printStackTrace();
				}
			}
		});
		sms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					String message = "Tears Of a mother can not save her child but your blood can... 20 minutes of your time and 250 cc of your blood may make the difference between life and death";
					SmsManager smsmanager = SmsManager.getDefault();

					smsmanager
							.sendTextMessage(call1, null, message, null, null);
					Toast.makeText(getApplicationContext(),
							"Your SMS Sent Sucessfully", Toast.LENGTH_LONG)
							.show();
				}

				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}