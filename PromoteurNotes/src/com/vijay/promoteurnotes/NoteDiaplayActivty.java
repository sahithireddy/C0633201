package com.vijay.promoteurnotes;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoteDiaplayActivty extends Activity {
	private TextView titletextView, categorytextView,notetextView, datetextView,
			locationtextView, recordingtextView;
	private ImageView imageView1, imageView2;
	private Button playbutton;
	private String title, note, date, image1, image2, recordpath, latlon,category;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notedisplayactivity);

		titletextView = (TextView) findViewById(R.id.titletextView);
		categorytextView = (TextView) findViewById(R.id.categorytextView);
		notetextView = (TextView) findViewById(R.id.notetextView);
		datetextView = (TextView) findViewById(R.id.datetextView);
		locationtextView = (TextView) findViewById(R.id.locationtextView);
		recordingtextView = (TextView) findViewById(R.id.recordingtextView);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		playbutton = (Button) findViewById(R.id.playbutton);

		title = getIntent().getExtras().getString("title");
		category = getIntent().getExtras().getString("category");
		note = getIntent().getExtras().getString("note");
		date = getIntent().getExtras().getString("date");
		image1 = getIntent().getExtras().getString("img1");
		image2 = getIntent().getExtras().getString("img2");
		recordpath = getIntent().getExtras().getString("record");
		latlon = getIntent().getExtras().getString("latlon");
		System.out.println("image1: "+image1+"| image2: "+image2);

		titletextView.setText("Title: " + title);
		categorytextView.setText("Category: " + category);
		notetextView.setText("Note: " + note);
		datetextView.setText("Date: " + date);
		locationtextView.setText("Location: " + latlon);
		if (recordpath != null && !recordpath.equalsIgnoreCase("")) {
			recordingtextView.setText("Path:" + recordpath);
			playbutton.setVisibility(View.VISIBLE);
		} else {
			recordingtextView.setText("Path:");
			playbutton.setVisibility(View.INVISIBLE);
		}

		if (image1 != null && !image1.equalsIgnoreCase("")) {
			// Bitmap bitmap1 = getBitmap(image1);
			Bitmap bitmap1 = readimagefile(image1);
			if (bitmap1 != null) {
				imageView1.setImageBitmap(bitmap1);
			}
		}
		if (image2 != null && !image2.equalsIgnoreCase("")) {
			Bitmap bitmap2 = readimagefile(image2);
			if (bitmap2 != null) {
				imageView2.setImageBitmap(bitmap2);
			}
		}
		
		playbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (recordpath != null && !recordpath.equalsIgnoreCase("")) {

					MediaPlayer mediaPlayer = new MediaPlayer();
					try {
						/*mediaPlayer.setDataSource(recordpath);
						mediaPlayer.prepare();
						mediaPlayer.start();*/
						
						Intent intent = new Intent();  
						intent.setAction(android.content.Intent.ACTION_VIEW);  
						//Uri uri=Uri.parse(recordpath);
						File file = new File(recordpath);  
						intent.setDataAndType(Uri.fromFile(file), "audio/*");  
						startActivity(intent);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Toast.makeText(NoteDiaplayActivty.this,
								"Error, Try again", Toast.LENGTH_LONG).show();
					}
				}
			}
		});
	}

	/*private Bitmap getBitmap(String path) {
		File imgFile = new File(path);
		if (imgFile.exists()) {

			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			// myImage.setImageBitmap(myBitmap);
			return myBitmap;
		} else {
			return null;
		}
	}

	private Bitmap getbitmapusingpath(String path) {

		if (path != null && !path.equalsIgnoreCase("")) {

			Bitmap bmImg = BitmapFactory.decodeFile(path);
			return bmImg;
		} else {
			return null;
		}
	}*/

	public Bitmap readimagefile(String filename) {

		Bitmap mBitmap = null;
		try {
			File inputfilestored = new File(
					NoteDiaplayActivty.this.getFilesDir(), filename);
			// String storedpath= inputfilestored.getPath();
			// imggrocerry.setImageURI(Uri.fromFile(inputfilestored));

			BitmapFactory.Options bounds = new BitmapFactory.Options();
			bounds.inSampleSize = 4;

			// Creating bitmap of the selected image from its inputstream
			mBitmap = BitmapFactory.decodeFile(inputfilestored.getPath(),
					bounds);// (,null,bounds);
		} catch (Exception ex) {
			Log.v("Exception", ex.getMessage());
		}

		return mBitmap;// Uri.fromFile(inputfilestored);
	}
	
}
