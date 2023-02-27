package com.prop.gpsbloodbank;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapteradmin extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String, String>> listvalue;

	public CustomBaseAdapteradmin(Viewadminprofile viewadminprofile,
			ArrayList<HashMap<String, String>> usersList) {
		// TODO Auto-generated constructor stub
		con = viewadminprofile;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(viewadminprofile);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listvalue.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listvalue.get(position);
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.listview, null);
			viewHolder = new ViewHolder();
			viewHolder.txtUsername = (TextView) convertView
					.findViewById(R.id.textView5);
			viewHolder.txtUsername1 = (TextView) convertView
					.findViewById(R.id.textView6);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtUsername.setText(listvalue.get(position).get("name")
				.toString());
		viewHolder.txtUsername1.setText(listvalue.get(position).get("name1")
				.toString());

		return convertView;

	}

	class ViewHolder {
		TextView txtUsername;
		TextView txtUsername1;

	}

}
