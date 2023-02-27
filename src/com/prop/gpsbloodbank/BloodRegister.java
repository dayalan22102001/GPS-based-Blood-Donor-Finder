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

public class BloodRegister extends Activity {
	EditText name, no, email, dob,edtpass;
	Button send, view, update;
	Spinner type, dis, state, gender;
	Connection conn;
	String name1, no1, email1, type1, dis1, state1, gender1, dob1, pass1;
	GPSTracker gpstracker;
	double fromlat, fromlong;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloodregister);
		name = (EditText) findViewById(R.id.editText1);
		edtpass = (EditText) findViewById(R.id.passwordedt);
		no = (EditText) findViewById(R.id.editText2);
		email = (EditText) findViewById(R.id.editText3);
		dob = (EditText) findViewById(R.id.editText4);
		type = (Spinner) findViewById(R.id.spinner1);
		dis = (Spinner) findViewById(R.id.spinner2);
		state = (Spinner) findViewById(R.id.spinner3);
		gender = (Spinner) findViewById(R.id.spinner4);
		send = (Button) findViewById(R.id.button1);
		view = (Button) findViewById(R.id.button2);
		update = (Button) findViewById(R.id.Button01);

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
				"Kerala (KL)", "Madhya Pradesh (MP)", "Maharashtra (MH)",
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

		dob.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialogDate(dob);
			}
		});
		update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BloodRegister.this,
						MapPage1.class);
				startActivity(intent);
			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name1 = name.getText().toString();
				pass1 = edtpass.getText().toString();
				no1 = no.getText().toString();
				email1 = email.getText().toString();
				type1 = type.getSelectedItem().toString();
				dis1 = dis.getSelectedItem().toString();
				state1 = state.getSelectedItem().toString();
				gender1 = gender.getSelectedItem().toString();
				dob1 = dob.getText().toString();

				new QuerySQL().execute();
			}
		});

		view.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(BloodRegister.this, Fact.class);
				startActivity(intent);
			}
		});

		Get_Current_GeoPoints();

	}

	private void Get_Current_GeoPoints() {
		gpstracker = new GPSTracker(BloodRegister.this);

		if (gpstracker.canGetLocation()) {

			fromlat = gpstracker.getLatitude();
			fromlong = gpstracker.getLongitude();

			System.out.println("the current lat is " + fromlat);
			System.out.println("the current long is " + fromlong);

		} else {
			showSettingsAlert();
		}

	}

	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(
				BloodRegister.this);
		alertDialog.setCancelable(false);
		alertDialog.setTitle("Location Services");
		alertDialog
				.setMessage("Location Services is not enabled. Do you want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						BloodRegister.this.startActivityForResult(intent, 1);
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
			gpstracker = new GPSTracker(BloodRegister.this);

			if (gpstracker.canGetLocation()) {
				Get_Current_GeoPoints();
			} else {
				System.out.println("the gpstracker is disabled");
				showSettingsAlert();
			}

		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void showDialogDate(EditText edt) {
		// TODO Auto-generated method stub
		// getCalender();
		Calendar c = Calendar.getInstance();
		int mYear = c.get(Calendar.YEAR);
		int mMonth = c.get(Calendar.MONTH);
		int mDay = c.get(Calendar.DAY_OF_MONTH);
		System.out.println("the selected " + mDay);
		DatePickerDialog dialog = new DatePickerDialog(BloodRegister.this,
				new mDateSetListener(edt), mYear, mMonth, mDay);
		dialog.show();
	}

	class mDateSetListener implements DatePickerDialog.OnDateSetListener {
		EditText v;

		mDateSetListener(EditText v) {
			this.v = v;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			// getCalender();
			int mYear = year;
			int mMonth = monthOfYear;
			int mDay = dayOfMonth;
			// v.setText(new StringBuilder()
			// // Month is 0 based so add 1
			// .append(mMonth + 1).append("/").append(mDay).append("/")
			// .append(mYear).append(" "));
			// System.out.println(v.getText().toString());

			String selecteddate = checkDigit(mMonth + 1) + "/"
					+ checkDigit(mDay) + "/" + checkDigit(mYear);

			final Calendar c = Calendar.getInstance();
			final int year1 = c.get(Calendar.YEAR);
			int month1 = c.get(Calendar.MONTH);
			int day1 = c.get(Calendar.DAY_OF_MONTH);
			String currentdate = checkDigit((month1 + 1)) + "/"
					+ checkDigit(day1) + "/" + checkDigit(year1);

			Date date1 = null, date2 = null;
			try {
				System.out.println("Inside try");
				String formatString = "MM/dd/yyyy";
				SimpleDateFormat df = new SimpleDateFormat(formatString);
				date1 = df.parse(currentdate);
				date2 = df.parse(selecteddate);
				System.out.println("current date " + date1);
				System.out.println("selected date " + date2);

				if (date2.compareTo(date1) < 0) {
					System.out.println("inside date if");
					v.setText(selecteddate);

				} else {
					System.out.println("inside date else");
					v.setText("");

					Toast.makeText(BloodRegister.this, "Select past date",
							Toast.LENGTH_SHORT).show();

				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}

	public String checkDigit(int number) {
		return number <= 9 ? "0" + number : String.valueOf(number);
	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(BloodRegister.this);
			pDialog.setTitle("Register");
			pDialog.setMessage("Storing Register Details...");
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
								+ no1 + "','" + name1 + "','" + email1 + "','"
								+ type1 + "','" + dis1 + "','" + state1 + "','"
								+ fromlat + "','" + fromlong + "','" + gender1
								+ "','" + dob1 + "','" + pass1 + "')");

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
							"Not Send Successfully!!!", Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}
}