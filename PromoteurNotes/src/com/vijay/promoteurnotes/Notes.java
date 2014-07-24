package com.vijay.promoteurnotes;

import android.graphics.Bitmap;

public class Notes {
	private String title,note,category,notedate,latitude,longitude;
	//private Bitmap img1,img2;
	private String img1_location,img1_name,img2_location,img2_name,record_location,video_location;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getNotedate() {
		return notedate;
	}
	public void setNotedate(String notedate) {
		this.notedate = notedate;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	/*public Bitmap getImg1() {
		return img1;
	}
	public void setImg1(Bitmap img1) {
		this.img1 = img1;
	}
	public Bitmap getImg2() {
		return img2;
	}
	public void setImg2(Bitmap img2) {
		this.img2 = img2;
	} */
	public String getImg1_location() {
		return img1_location;
	}
	public void setImg1_location(String img1_location) {
		this.img1_location = img1_location;
	}
	public String getImg1_name() {
		return img1_name;
	}
	public void setImg1_name(String img1_name) {
		this.img1_name = img1_name;
	}
	public String getImg2_location() {
		return img2_location;
	}
	public void setImg2_location(String img2_location) {
		this.img2_location = img2_location;
	}
	public String getImg2_name() {
		return img2_name;
	}
	public void setImg2_name(String img2_name) {
		this.img2_name = img2_name;
	}
	public String getRecord_location() {
		return record_location;
	}
	public void setRecord_location(String record_location) {
		this.record_location = record_location;
	}
	public String getVideo_location() {
		return video_location;
	}
	public void setVideo_location(String video_location) {
		this.video_location = video_location;
	}
	
}
