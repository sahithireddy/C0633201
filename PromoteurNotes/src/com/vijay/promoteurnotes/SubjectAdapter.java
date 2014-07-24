package com.vijay.promoteurnotes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SubjectAdapter extends BaseAdapter {
	private Context con;
	private ArrayList<String> sublist;

	public SubjectAdapter(Context con, ArrayList<String> sublist) {
		this.con = con;
		this.sublist = sublist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return sublist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return sublist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) con
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.subjectlistitem_layout, null);

		}
		TextView titletextView = (TextView) view
				.findViewById(R.id.subjectTextview);

		String obj = sublist.get(position);
		titletextView.setText(""+obj);
		view.setTag(obj);
		return view;
	}

}
