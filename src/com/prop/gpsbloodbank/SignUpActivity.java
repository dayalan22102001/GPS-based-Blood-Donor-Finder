package com.prop.gpsbloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	EditText edtName, edtMobileNo, edtEmail, edtCity, edtCountry, edtUserName,
			edtPassword, edtConfrimPassword;
	Button btnSubmit;
	Connection conn;
	double lat, lon;
	EditText group;
	protected LocationManager mlocManager;

	private String name, mobilenumber, email, city, country, username,
			password, confrimpassword, group1;
	GPSTracker gpstracker;
	double fromlat, fromlong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		edtCity = (EditText) findViewById(R.id.signup_editText_city);
		edtConfrimPassword = (EditText) findViewById(R.id.signup_editText_confirm_password);
		edtCountry = (EditText) findViewById(R.id.signup_editText_country);
		edtEmail = (EditText) findViewById(R.id.signup_editText_email);
		edtMobileNo = (EditText) findViewById(R.id.signup_editText_mobileNumber);
		edtName = (EditText) findViewById(R.id.signup_editText_name);
		edtPassword = (EditText) findViewById(R.id.signup_editText_password);
		edtUserName = (EditText) findViewById(R.id.signup_editText_username);
		group = (EditText) findViewById(R.id.signup_editText_nickname);

		// lati=String.valueOf(lat);
		// longi=String.valueOf(lon);
		// if(lati.equals(null)||longi.equals(null) || lati.equals("") ||
		// longi.equals("")){
		// lati="71.000112";
		// longi="92.123343";
		// }
		btnSubmit = (Button) findViewById(R.id.signup_button_signup);
		btnSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				name = edtName.getText().toString();
				mobilenumber = edtMobileNo.getText().toString();
				email = edtEmail.getText().toString();
				city = edtCity.getText().toString();
				country = edtCountry.getText().toString();
				username = edtUserName.getText().toString();
				password = edtPassword.getText().toString();
				confrimpassword = edtConfrimPassword.getText().toString();
				group1 = group.getText().toString();

				if (!password.equals("") && password != null
						&& password.equals(confrimpassword)) {

					if (mobilenumber.length() == 10) {
						// conn = CONN();
						try {
							new QuerySQL().execute();

							// Statement statement = conn.createStatement();
							// int
							// success=statement.executeUpdate("insert into register values('"+name+"','"+mobilenumber+"','"+email+"','"+city+"','"+country+"','"+username+"','"+password+"','"+lati+"','"+longi+"')");
							//
							// if (success >= 1) {
							// // successfully created product
							// Intent i = new Intent(getApplicationContext(),
							// LoginFormActivity.class);
							// startActivity(i);
							//
							// // closing this screen
							// finish();
							// } else {
							// // failed to create product
							// }
						} catch (Exception e) {
							Log.e("ERRO", e.getMessage());
						}

					} else {
						Toast.makeText(getApplicationContext(),
								" Mobile No. should be 10 numbers!!!",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							" password not match.. Try again..!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Get_Current_GeoPoints();

	}
	
	private void Get_Current_GeoPoints()
	{
		gpstracker = new GPSTracker(SignUpActivity.this);

		if (gpstracker.canGetLocation()) {

			fromlat = gpstracker.getLatitude();
			fromlong = gpstracker.getLongitude();

			System.out.println("the current lat is " + fromlat);
			System.out.println("the current long is "
					+ fromlong);

		} else {
			showSettingsAlert();
		}
		
	}
	
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				SignUpActivity.this);
		alertDialog.setCancelable(false);
		alertDialog.setTitle("Location Services");
		alertDialog
				.setMessage("Location Services is not enabled. Do you want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						SignUpActivity.this.startActivityForResult(intent, 1);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
						finish();
					}
				});

		alertDialog.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("inside activity for result");
		if (requestCode == 1) {

			System.out.println("inside activity for result request code");
			gpstracker = new GPSTracker(SignUpActivity.this);

			if (gpstracker.canGetLocation()) {
				Get_Current_GeoPoints();
			} else {
				System.out.println("the gpstracker is disabled");
				showSettingsAlert();
			}

		}
	}

	// private Connection CONN()
	// {
	//
	// Connection conn = null;
	// String ConnURL = null;
	// try {
	//
	//
	// Class.forName("com.mysql.jdbc.Driver");
	// conn =
	// DriverManager.getConnection("jdbc:mysql://ec2-23-21-211-172.compute-1.amazonaws.com:3306/eshadow","eshadowroot","password");
	// } catch (SQLException se) {
	// Log.e("ERRO1",se.getMessage());
	// } catch (ClassNotFoundException e) {
	// Log.e("ERRO2",e.getMessage());
	// } catch (Exception e) {
	// Log.e("ERRO3",e.getMessage());
	// }
	// return conn;
	// }

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;
		ResultSet rs;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(SignUpActivity.this);
			pDialog.setTitle("Registration");
			pDialog.setMessage("Registering your credentials...");
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
						.executeUpdate("insert into bloodregister values('"
								+ name + "','" + mobilenumber + "','" + email
								+ "','" + city + "','" + country + "','"
								+ username + "','" + password + "','" + group1+ "','" + fromlat+ "','" + fromlong+ "')");

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

		private String Double(String string) {
			// TODO Auto-generated method stub
			return null;
		}

		@SuppressLint("NewApi")
		@Override
		protected void onPostExecute(Boolean result1) {
			pDialog.dismiss();
			if (result1) {

				Toast.makeText(getBaseContext(),
						"Successfully created your credentials.",
						Toast.LENGTH_LONG).show();

				// System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open

					Intent i = new Intent(getApplicationContext(),
							Menupage.class);
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
							"Not crreated your credentials!!!",
							Toast.LENGTH_LONG).show();
				}
			}
			super.onPostExecute(result1);
		}
	}

}
