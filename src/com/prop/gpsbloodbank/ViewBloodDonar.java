package com.prop.gpsbloodbank;

import java.sql.Connection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ViewBloodDonar extends Activity {
	EditText name, no, email;
	Button send;
	Spinner type/*, dis, state*/;
	Connection conn;
	String name1, no1, email1, type1, dis1, state1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewblood);
		type = (Spinner) findViewById(R.id.spinner1);
//		dis = (Spinner) findViewById(R.id.spinner2);
//		state = (Spinner) findViewById(R.id.spinner3);
		send = (Button) findViewById(R.id.button1);

		String type3[] = { "B+", "A-", "AB-", "O+", "O-", "AB+", "A+", "B-",
				"A1+", "A1-", "A2+", "A2-", "A1B+", "A1B-", "A2B+", "A2B-",
				"Bombay Blood Group" };

		ArrayAdapter<String> type2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type3);
		type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(type2);
//
//		String bookcity[] = { "Ariyalur", "Chennai", "Coimbatore", "Cuddalore",
//				"Dharmapuri", "Dindigul", "Erode", "Kancheepuram",
//				"Kanyakumari", "Karur", "Krishnagiri", "Madurai",
//				"Nagapattinam", "Namakkal", "Perambalur", "Pudukottai",
//				"Ramanathapuram", "Salem", "Sivaganga", "Thanjavur",
//				"The Nilgiris", "Theni", "Thirunelveli", "Thiruvallur",
//				"Thiruvannamalai", "Thiruvarur", "Tiruppur", "Trichirappalli",
//				"Tuticorin", "Vellore", "Villupuram", "Virudhunagar" };
//
//		ArrayAdapter<String> bookcity2 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, bookcity);
//		bookcity2
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		dis.setAdapter(bookcity2);
//
//		String state3[] = { "Tamil Nadu" };
//
//		ArrayAdapter<String> state2 = new ArrayAdapter<String>(this,
//				android.R.layout.simple_spinner_item, state3);
//		state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		state.setAdapter(state2);

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				type1 = type.getSelectedItem().toString();
//				dis1 = dis.getSelectedItem().toString();
//				state1 = state.getSelectedItem().toString();

//				Intent intent = new Intent(ViewBloodDonar.this,
//						ViewUserprofile.class);
//				intent.putExtra("bloodgroup", type1);
//				intent.putExtra("district", dis1);
//				intent.putExtra("state", state1);
//				startActivity(intent);
				
				Intent intent = new Intent(ViewBloodDonar.this,
						MapPage.class);
				intent.putExtra("bloodgroup", type1);
//				intent.putExtra("district", dis1);
//				intent.putExtra("state", state1);
				startActivity(intent);

			}
		});

	}
}