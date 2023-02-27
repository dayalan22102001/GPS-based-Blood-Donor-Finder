package com.prop.gpsbloodbank;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {
	Context con;
	LayoutInflater layoutInflater;
	ArrayList<HashMap<String, String>> listvalue;

	public CustomBaseAdapter(ViewUserprofile viewprofile,
			ArrayList<HashMap<String, String>> usersList) {
		// TODO Auto-generated constructor stub
		con = viewprofile;
		listvalue = usersList;
		layoutInflater = LayoutInflater.from(viewprofile);
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
			convertView = layoutInflater.inflate(R.layout.listview2, null);
			viewHolder = new ViewHolder();
			viewHolder.txtUsername = (TextView) convertView
					.findViewById(R.id.textView5);
			viewHolder.txtUsername1 = (TextView) convertView
					.findViewById(R.id.textView6);
			viewHolder.txtUsername2 = (TextView) convertView
					.findViewById(R.id.textView7);
			viewHolder.txtUsername3 = (TextView) convertView
					.findViewById(R.id.textView8);
			viewHolder.txtUsername4 = (TextView) convertView
					.findViewById(R.id.textView9);
			viewHolder.txtUsername5 = (TextView) convertView
					.findViewById(R.id.textView10);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.txtUsername.setText(listvalue.get(position).get("name")
				.toString());
		viewHolder.txtUsername1.setText(listvalue.get(position).get("name1")
				.toString());
		viewHolder.txtUsername2.setText(listvalue.get(position).get("name2")
				.toString());
		viewHolder.txtUsername3.setText(listvalue.get(position).get("name3")
				.toString());

		viewHolder.txtUsername4.setText(listvalue.get(position).get("name4")
				.toString());

		viewHolder.txtUsername5.setText(listvalue.get(position).get("name5")
				.toString());

		return convertView;

	}

	class ViewHolder {
		TextView txtUsername;
		TextView txtUsername1;
		TextView txtUsername2;
		TextView txtUsername3;

		TextView txtUsername4;

		TextView txtUsername5;

	}

}
