package com.prop.gpsbloodbank;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Adminmenu extends Activity {
	Button send, give;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminmenu);

		send = (Button) findViewById(R.id.button2);
		give = (Button) findViewById(R.id.button1);

		give.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Adminmenu.this, SignUpActivity.class);
				startActivity(intent);
			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Adminmenu.this, SendAlert.class);
				startActivity(intent);
			}
		});
	}
}