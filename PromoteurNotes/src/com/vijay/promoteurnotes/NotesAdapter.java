package com.vijay.promoteurnotes;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NotesAdapter extends BaseAdapter {
	private Context con;
	private ArrayList<Notes> noteList;
	private ArrayList<Notes> arraylist;

	public NotesAdapter(Context con, ArrayList<Notes> noteList) {
		this.con = con;
		this.noteList = noteList;
		this.arraylist = new ArrayList<Notes>();
		this.arraylist.addAll(noteList);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return noteList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return noteList.get(position);
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
			view = inflater.inflate(R.layout.noteslist_layout, null);

		}
		TextView titletextView = (TextView) view
				.findViewById(R.id.titletextView);
		TextView datetextView = (TextView) view.findViewById(R.id.datetextView);
		//TextView timetextView = (TextView) view.findViewById(R.id.timetextView);
		TextView imagestextView = (TextView) view
				.findViewById(R.id.imagestextView);
		/*TextView videostextView = (TextView) view
				.findViewById(R.id.videostextView);*/
		TextView recordingstextView = (TextView) view
				.findViewById(R.id.recordingstextView);
		Button mapbutton = (Button) view.findViewById(R.id.mapbutton);

		final Notes obj = noteList.get(position);
		titletextView.setText(""+obj.getTitle());
		datetextView.setText(""+obj.getNotedate());
		//timetextView.setText(""+obj.getNotedate());
		int imgCount=0,vCount=0,rCount=0;
		if(obj.getImg1_location()!=null && !obj.getImg1_location().equals("")){
			imgCount=imgCount+1;
			//Bitmap bm1=readimagefile(obj.getImg1_location())l
		}
		if(obj.getImg2_location()!=null && !obj.getImg2_location().equals("")){
			imgCount=imgCount+1;
		}
		/*if(obj.getVideo_location()!=null && !obj.getVideo_location().equals("")){
			vCount=vCount+1;
		}*/
		if(obj.getRecord_location()!=null && !obj.getRecord_location().equals("")){
			rCount=rCount+1;
		}
		
		
		/*if(obj.get!=null && !obj.getImg1().equals("")){
			vCount=vCount+1;
		}
		if(obj.getImg2()!=null && !obj.getImg2().equals("")){
			rCount=rCount+1;
		}*/
		imagestextView.setText("images: "+imgCount);
		//videostextView.setText("video: "+vCount);
		recordingstextView.setText("recording: "+rCount);
		mapbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(con, MapViewActivity.class);
				System.out.println(obj.getLatitude()+"|"+obj.getLongitude());
				intent.putExtra("lat", ""+obj.getLatitude());
				intent.putExtra("lon", ""+obj.getLongitude());
				con.startActivity(intent);
			}
		});
		
		//#E6E6E6
		if (position % 2 == 0) {
			view.setBackgroundColor(Color.parseColor("#F0F0F0"));
		} else {
			view.setBackgroundColor(Color.WHITE);
		}
		view.setTag(obj);
		return view;
	}
	

	/*public Object getFilter() {
		// TODO Auto-generated method stub
		return null;
	}*/
	// Filter Class
    public void getFilter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        noteList.clear();
        if (charText.length() == 0) {
        	noteList.addAll(arraylist);
        } else {
            for (Notes obj : arraylist) {
                if (obj.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	noteList.add(obj);
                }else if (obj.getNote().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	noteList.add(obj);
                }
                else if (obj.getCategory().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                	noteList.add(obj);
            }
        }
        notifyDataSetChanged();
    }
 
    }}
