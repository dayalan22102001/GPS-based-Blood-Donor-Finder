package com.prop.gpsbloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendAlert extends Activity {

	EditText no, text, date;
	Button send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smsalert);

		no = (EditText) findViewById(R.id.editText2);
		text = (EditText) findViewById(R.id.editText1);
		date = (EditText) findViewById(R.id.EditText01);
		send = (Button) findViewById(R.id.button1);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String number = no.getText().toString();
				String message = text.getText().toString();
				String datee = date.getText().toString();
				SmsManager smsmanager = SmsManager.getDefault();
				smsmanager.sendTextMessage(number, null, message, null, null);

				Toast.makeText(getApplicationContext(),
						"Your Message Sent Successfully", Toast.LENGTH_LONG)
						.show();

				try {

					Intent intent = new Intent(SendAlert.this, Addevent.class);
					intent.putExtra("mobile", number);
					intent.putExtra("text", message);
					intent.putExtra("eventdate", datee);

					startActivity(intent);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

}
