package com.prop.gpsbloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BloodRegisterupdate extends Activity {
	EditText name, no, email, dob;
	Button send, view, update;
	Spinner type, dis, state, gender;
	Connection conn;
	String name1, no1, email1, type1, dis1, state1, gender1, dob1;
	GPSTracker gpstracker;
	double fromlat, fromlong;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userupdate);
		name = (EditText) findViewById(R.id.editText1);
		no = (EditText) findViewById(R.id.editText2);
		email = (EditText) findViewById(R.id.editText3);

		type = (Spinner) findViewById(R.id.spinner1);
		dis = (Spinner) findViewById(R.id.spinner2);
		state = (Spinner) findViewById(R.id.spinner3);

		update = (Button) findViewById(R.id.button1);

		String type3[] = { "B+", "A-", "AB-", "O+", "O-", "AB+", "A+", "B-",
				"A1+", "A1-", "A2+", "A2-", "A1B+", "A1B-", "A2B+", "A2B-",
				"Bombay Blood Group" };

		ArrayAdapter<String> type2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, type3);
		type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		type.setAdapter(type2);

		String bookcity[] = { "Ariyalur", "Chennai", "Coimbatore", "Cuddalore",
				"Dharmapuri", "Dindigul", "Erode", "Kancheepuram",
				"Kanyakumari", "Karur", "Krishnagiri", "Madurai",
				"Nagapattinam", "Namakkal", "Perambalur", "Pudukottai",
				"Ramanathapuram", "Salem", "Sivaganga", "Thanjavur",
				"The Nilgiris", "Theni", "Thirunelveli", "Thiruvallur",
				"Thiruvannamalai", "Thiruvarur", "Tiruppur", "Trichirappalli",
				"Tuticorin", "Vellore", "Villupuram", "Virudhunagar" };

		ArrayAdapter<String> bookcity2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, bookcity);
		bookcity2
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dis.setAdapter(bookcity2);

		String state3[] = { " Andhra Pradesh (AP)", "Arunachal Pradesh (AR)",
				"Assam (AS)", "Bihar (BR)", "	Chhattisgarh (CG)", "Goa (GA)",
				"Gujarat (GJ)", "Haryana (HR)", "Himachal Pradesh (HP)",
				"Jammu and Kashmir (JK)", "Jharkhand (JH)", "Karnataka (KA)",
				"	Kerala (KL)", "Madhya Pradesh (MP)", "Maharashtra (MH)",
				"Manipur (MN)", "Meghalaya (ML)", "Mizoram (MZ)",
				"Nagaland (NL)", "Odisha(OR)", "Punjab (PB)", "Rajasthan (RJ)",
				"Sikkim (SK)", "Tamil Nadu (TN)", "Tripura (TR)",
				"Uttar Pradesh (UP)", "Uttarakhand (UK)", "West Bengal (WB)" };

		ArrayAdapter<String> state2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, state3);
		state2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		state.setAdapter(state2);

		String gender3[] = { "Male", "Female" };

		ArrayAdapter<String> gender31 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, gender3);
		gender31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gender.setAdapter(gender31);

		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name1 = name.getText().toString();
				no1 = no.getText().toString();
				email1 = email.getText().toString();
				type1 = type.getSelectedItem().toString();
				dis1 = dis.getSelectedItem().toString();
				state1 = state.getSelectedItem().toString();

				new QuerySQL().execute();
			}
		});

	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(BloodRegisterupdate.this);
			pDialog.setTitle("Update");
			pDialog.setMessage("Updating Register Details...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			try {

				Class.forName("com.mysql.jdbc.Driver");
				 conn = DriverManager.getConnection("jdbc:mysql://mysql-75344-0.cloudclusters.net:18880/blood","admin","cU5zYktH");
						
			} catch (SQLException se) {
				Log.e("ERRO1", se.getMessage());
			} catch (ClassNotFoundException e) {
				Log.e("ERRO2", e.getMessage());
			} catch (Exception e) {
				Log.e("ERRO3", e.getMessage());
			}

			try {
				Statement statement = conn.createStatement();
				int success = statement
						.executeUpdate("update bloodregister set mobile = '"
								+ no1 + "', email ='" + email1
								+ "', bloodtype = '" + type1
								+ "', district = '" + dis1 + "', state = '"
								+ state1 + "' where username ='" + name1 + "'");

				if (success >= 1) {
					// successfully created product

					return true;
					// closing this screen
					// finish();
				} else {
					// failed to create product
					return false;
				}

				// Toast.makeText(getBaseContext(),
				// "Successfully Inserted.", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
				error = e;
				return false;
				// Toast.makeText(getBaseContext(),"Successfully Registered...",
				// Toast.LENGTH_LONG).show();
			}

		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss();
			if (result1) {

				Toast.makeText(getBaseContext(), "Updated Successfully...",
						Toast.LENGTH_LONG).show();

				try {// try3 open

					Intent i = new Intent(getApplicationContext(),
							BloodRegister.class);
					startActivity(i);

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}

			} else {
				if (error != null) {
					Toast.makeText(getApplicationContext(), error.toString(),
							Toast.LENGTH_LONG).show();
					Log.d("Error not null...", error.toString());
				} else {
					Toast.makeText(getBaseContext(),
							"Not Send Successfully !!!", Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}
}