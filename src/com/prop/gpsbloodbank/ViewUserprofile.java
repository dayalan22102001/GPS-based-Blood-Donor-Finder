package com.prop.gpsbloodbank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ViewUserprofile extends Activity {
	ListView listView;
	Button show;
	EditText name;
	Spinner dis;
	Connection conn;
	String bloodgroup2, district2, state2;
	HashMap<String, String> usersList1 = null;
	ArrayList<HashMap<String, String>> usersList2 = new ArrayList<HashMap<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewuserprofilelist);
		listView = (ListView) findViewById(R.id.listView1);

		Intent intent = getIntent();
		bloodgroup2 = intent.getStringExtra("bloodgroup");
		district2 = intent.getStringExtra("district");
		state2 = intent.getStringExtra("state");

		try {
			new QuerySQL().execute();
		} catch (Exception e) {
			Log.e("ERRO", e.getMessage());
		}

		// Intent intent = getIntent();
		// username = intent.getStringExtra("name");

	}

	public class QuerySQL extends AsyncTask<Object, Void, Boolean> {

		ProgressDialog pDialog;

		Exception error;

		ResultSet rs;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(ViewUserprofile.this);
			pDialog.setTitle("User Profile Details");
			pDialog.setMessage("Wait Getting User Profile  Details...");
			pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Boolean doInBackground(Object... args) {

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
				// String
				// COMANDOSQL1="select * from services where reqsender='"+sendername+"' || reqrecv='"+sendername+"' && status='accept'";
				// Statement statement1 = conn.createStatement();
				// ResultSet rs1 = statement1.executeQuery(COMANDOSQL1);
				// if(rs1.next()){
				String COMANDOSQL = "select * from bloodregister where bloodtype='"
						+ bloodgroup2
						+ "'&& district='"
						+ district2
						+ "'&& state='" + state2 + "' ";
				Statement statement = conn.createStatement();
				rs = statement.executeQuery(COMANDOSQL);
				while (rs.next()) {
					usersList1 = new HashMap<String, String>();
					usersList1.put("name", rs.getString(1));
					usersList1.put("name1", rs.getString(2));
					usersList1.put("name2", rs.getString(3));
					usersList1.put("name3", rs.getString(4));
					usersList1.put("name4", rs.getString(5));
					usersList1.put("name5", rs.getString(6));

					Log.d("Friend List Map :", usersList1.toString());

					usersList2.add(usersList1);

				}
				// }

				return true;
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

					listView.setAdapter(new CustomBaseAdapter(
							ViewUserprofile.this, usersList2));
					listView.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> parent, View v,
								int position, long id) {

							Intent intent = new Intent(ViewUserprofile.this,
									Cal.class);
							intent.putExtra("name", usersList2.get(position)
									.get("name"));

							startActivity(intent);
						}
					});

				} catch (Exception e1) {
					Toast.makeText(getBaseContext(), e1.toString(),
							Toast.LENGTH_LONG).show();

				}

			} else {
				if (error != null) {
					Toast.makeText(getBaseContext(),
							error.getMessage().toString(), Toast.LENGTH_LONG)
							.show();
				}
			}
			super.onPostExecute(result1);
		}
	}
}
