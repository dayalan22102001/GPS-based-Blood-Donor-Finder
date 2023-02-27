package com.prop.gpsbloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Admin extends Activity {
	EditText name, password;
	Connection conn;
	Button login, forget;
	String user, pass, user1, pass1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		name = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		login = (Button) findViewById(R.id.button1);
		forget = (Button) findViewById(R.id.Button01);
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				user = name.getText().toString();
				pass = password.getText().toString();
				Log.d("username", user);
				Log.d("password", pass);

				/*
				 * lati=String.valueOf(lat); longi=String.valueOf(lon);
				 * if(lati.equals(null)||longi.equals(null)){ lati="71.000112";
				 * longi="92.123343";
				 */
				// }
				new QuerySQL().execute(user, pass);
			}
		});
		forget.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Admin.this, Forgetpswdadmin.class);

				startActivity(intent);
			}
		});

	}

	public class QuerySQL extends AsyncTask<String, Void, Boolean> {

		ProgressDialog pDialog;
		Exception error;
		ResultSet rs;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(Admin.this);
			pDialog.setTitle("Admin Authentication");
			pDialog.setMessage("Verifying your credentials...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(String... args) {

			user1 = new String(args[0]);
			pass1 = new String(args[1]);

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
				String COMANDOSQL = "select * from admin where username='"
						+ user1 + "' && password='" + pass1 + "'";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				if (rs.next()) {
					// String COMANDOSQL1 = "update register  where username='"
					// + user1 + "' && password='" + pass1 + "'";
					// Statement statement1 = conn.createStatement();
					// statement1.executeUpdate(COMANDOSQL1);
					return true;
				}

				return false;

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

				// System.out.println("ELSE(JSON) LOOP EXE");
				try {// try3 open

					Intent intent = new Intent(Admin.this, Adminmenu.class);

					// intent.putExtra("loginuser", user1);

					startActivity(intent);

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}

			} else {
				if (error != null) {
					Toast.makeText(getBaseContext(),
							error.getMessage().toString(), Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(getBaseContext(),
							"Check your credentials!!!", Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}
}
