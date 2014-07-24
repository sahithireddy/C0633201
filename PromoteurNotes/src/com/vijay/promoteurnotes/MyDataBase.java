package com.vijay.promoteurnotes;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class MyDataBase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "NotesDb.db";
	public static final int DATABASE_VERSION = 1;
	private static final String NOTES = "NOTES";
	private static final String CATEGORIES = "CATEGORIES";
	private SQLiteDatabase db;

	public MyDataBase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// String query = "CREATE TABLE IF NOT EXISTS "+NOTES
		// +"( TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1 BLOB,IMAGE2 BLOB,NOTEDATE TEXT, LATITUDE TEXT,LONGITUDE TEXT)";
		String query = "CREATE TABLE IF NOT EXISTS "
				+ NOTES
				+ "( TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1_LOCATION TEXT,IMAGE1_NAME TEXT, IMAGE2_LOCATION TEXT,IMAGE2_NAME TEXT ,NOTEDATE TEXT, LATITUDE TEXT,LONGITUDE TEXT,RECORDING_LOCATION TEXT, VIDEO_LOCATION TEXT)";
		String cquery = "CREATE TABLE IF NOT EXISTS " + CATEGORIES
				+ " (CATEGORYNAME TEXT)";
		db.execSQL(query);
		db.execSQL(cquery);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public Boolean isopen() {
		return db.isOpen();
	}

	// TO CLOSE THE DATABASE
	public void close() {
		db.close();
	}

	public void addCategory(String category) {
		ContentValues cv = new ContentValues();
		cv.put("CATEGORYNAME", category);
		db.insert(CATEGORIES, null, cv);
	}

	public boolean deleteCategory(String category) {
		String sql = "DELETE FROM " + CATEGORIES + " WHERE CATEGORYNAME="
				+ category;
		// db.execSQL(sql);CATEGORYNAME
		// return true;
		return db.delete(CATEGORIES, "CATEGORYNAME =?",
				new String[] { category }) > 0;
	}

	public ArrayList<String> getAllCategories() {
		ArrayList<String> catlist = new ArrayList<String>();
		String[] col = { "CATEGORYNAME" };
		Cursor c = db.query(CATEGORIES, col, null, null, null, null, null);

		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				String category = c.getString(c.getColumnIndex("CATEGORYNAME"));
				catlist.add(category);
			} while (c.moveToNext());
		}

		return catlist;
	}

	public boolean insertNote(String title, String note, String category,
			String img1loc, String img1name, String img2loc, String img2name,
			String notedate, Double lat, Double lon, String recordingpath,String videoPath) {

		ContentValues cv = new ContentValues();
		cv.put("TITLE", title);
		cv.put("NOTE", note);
		cv.put("CATEGORY", category);
		/*
		 * cv.put("IMAGE1", img1data); cv.put("IMAGE2", img2data);
		 */
		cv.put("IMAGE1_LOCATION", img1loc);
		cv.put("IMAGE1_NAME", img1name);
		cv.put("IMAGE2_LOCATION", img2loc);
		cv.put("IMAGE2_NAME", img2name);
		cv.put("NOTEDATE", notedate);
		cv.put("LATITUDE", "" + lat);
		cv.put("LONGITUDE", "" + lon);
		cv.put("RECORDING_LOCATION", "" + recordingpath);
		cv.put("VIDEO_LOCATION", "" + videoPath);
		return db.insert("NOTES", null, cv) > 0;
	}

	public ArrayList<Notes> getAllNotes() {
		ArrayList<Notes> nlist = new ArrayList<Notes>();
		//nlist.clear();
		// TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1_LOCATION TEXT,IMAGE1_NAME
		// TEXT, IMAGE2_LOCATION TEXT,IMAGE2_NAME TEXT ,NOTEDATE TEXT, LATITUDE
		// TEXT,LONGITUDE TEXT,RECORDING_LOCATION, TEXT VIDEO_LOCATION TEXT)";
		Cursor c = db.query("NOTES", null, null, null, null, null, null);

		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				String title = c.getString(c.getColumnIndex("TITLE"));
				String note = c.getString(c.getColumnIndex("NOTE"));
				String category = c.getString(c.getColumnIndex("CATEGORY"));
				String img1_location = c.getString(c
						.getColumnIndex("IMAGE1_LOCATION"));
				String img2_location = c.getString(c
						.getColumnIndex("IMAGE2_LOCATION"));
				String img1name = c.getString(c.getColumnIndex("IMAGE1_NAME"));
				String img2name = c.getString(c.getColumnIndex("IMAGE2_NAME"));
				String notedate = c.getString(c.getColumnIndex("NOTEDATE"));
				String lat = c.getString(c.getColumnIndex("LATITUDE"));
				String lon = c.getString(c.getColumnIndex("LONGITUDE"));
				
				String record = c.getString(c.getColumnIndex("RECORDING_LOCATION"));
				String video = c.getString(c.getColumnIndex("VIDEO_LOCATION"));

				/*
				 * Bitmap b1 = BitmapFactory.decodeByteArray(img1data, 0,
				 * img1data.length); Bitmap b2 =
				 * BitmapFactory.decodeByteArray(img2data, 0, img2data.length);
				 */
				Notes nObj = new Notes();
				nObj.setTitle(title);
				nObj.setNote(note);
				nObj.setCategory(category);
				/*
				 * nObj.setImg1(b1); nObj.setImg2(b2);
				 */
				nObj.setImg1_location(img1_location);
				nObj.setImg1_name(img1name);
				nObj.setImg2_location(img2_location);
				nObj.setImg2_name(img2name);
				nObj.setNotedate(notedate);
				nObj.setLatitude(lat);
				nObj.setLongitude(lon);
				nObj.setRecord_location(record);
				nObj.setVideo_location(video);
				nlist.add(nObj);
			} while (c.moveToNext());
		}

		return nlist;
	}

	public ArrayList<Notes> getAllNotesByDate() {
		ArrayList<Notes> nlist = new ArrayList<Notes>();
		//nlist.clear();
		// TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1 BLOB,IMAGE2 BLOB,NOTEDATE
		// TEXT, LATITUDE TEXT,LONGITUDE TEXT

		// TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1_LOCATION TEXT,IMAGE1_NAME
		// TEXT, IMAGE2_LOCATION TEXT,IMAGE2_NAME TEXT ,NOTEDATE TEXT, LATITUDE
		// TEXT,LONGITUDE TEXT)";
		/*
		 * String[] col = { "TITLE", "NOTE", "CATEGORY", "IMAGE1_LOCATION",
		 * "IMAGE2_LOCATION", "NOTEDATE", "LATITUDE", "LONGITUDE" };
		 */
		Cursor c = db.query("NOTES", null, null, null, null, null,
				"NOTEDATE ASC");// NOTEDATE ASC LIMIT 1

		if (c != null && c.getCount() > 0) {
			c.moveToFirst();
			do {
				String title = c.getString(c.getColumnIndex("TITLE"));
				String note = c.getString(c.getColumnIndex("NOTE"));
				String category = c.getString(c.getColumnIndex("CATEGORY"));
				String img1_location = c.getString(c
						.getColumnIndex("IMAGE1_LOCATION"));
				String img2_location = c.getString(c
						.getColumnIndex("IMAGE2_LOCATION"));
				String img1name = c.getString(c.getColumnIndex("IMAGE1_NAME"));
				String img2name = c.getString(c.getColumnIndex("IMAGE2_NAME"));
				String notedate = c.getString(c.getColumnIndex("NOTEDATE"));
				String lat = c.getString(c.getColumnIndex("LATITUDE"));
				String lon = c.getString(c.getColumnIndex("LONGITUDE"));
				
				String record = c.getString(c.getColumnIndex("RECORDING_LOCATION"));
				String video = c.getString(c.getColumnIndex("VIDEO_LOCATION"));

				/*
				 * Bitmap b1 = BitmapFactory.decodeByteArray(img1data, 0,
				 * img1data.length); Bitmap b2 =
				 * BitmapFactory.decodeByteArray(img2data, 0, img2data.length);
				 */
				Notes nObj = new Notes();
				nObj.setTitle(title);
				nObj.setNote(note);
				nObj.setCategory(category);
				/*
				 * nObj.setImg1(b1); nObj.setImg2(b2);
				 */
				nObj.setImg1_location(img1_location);
				nObj.setImg1_name(img1name);
				nObj.setImg2_location(img2_location);
				nObj.setImg2_name(img2name);
				nObj.setNotedate(notedate);
				nObj.setLatitude(lat);
				nObj.setLongitude(lon);
				nObj.setRecord_location(record);
				nObj.setVideo_location(video);
				nlist.add(nObj);
			} while (c.moveToNext());
		}

		return nlist;
	}

	// TITLE TEXT,NOTE TEXT, CATEGORY TEXT, IMAGE1 BLOB,IMAGE2 BLOB,NOTEDATE
	// TEXT, LATITUDE TEXT,LONGITUDE TEXT)";
	public boolean deleteNote(String title, String date) {
		// return true;
		return db.delete(NOTES, "TITLE =? AND NOTEDATE=?", new String[] {
				title, date }) > 0;
	}
}
