package com.prop.gpsbloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class Addevent extends Activity {
	Connection conn;
	String event, mno, edate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view2);
		new QuerySQL().execute();
		Intent intent = getIntent();
		mno = intent.getStringExtra("mobile");
		event = intent.getStringExtra("text");
		edate = intent.getStringExtra("eventdate");

	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Addevent.this);
			pDialog.setTitle("Add Event");
			pDialog.setMessage("Storing Event Details...");
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
						.executeUpdate("insert into details values('" + mno
								+ "','" + event + "','" + edate + "')");

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

				Toast.makeText(getBaseContext(), "Stored Successfully...",
						Toast.LENGTH_LONG).show();

				// System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open

					Intent i = new Intent(getApplicationContext(),
							Adminmenu.class);
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
							"Not Send Successfully!!!", Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}

}