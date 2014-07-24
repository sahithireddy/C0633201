package com.vijay.promoteurnotes;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class NotesActivity extends Activity {
	private ListView noteslistView;
	private NotesAdapter adapter;
	private ArrayList<Notes> noteList=new ArrayList<Notes>();
	private ArrayList<Notes> noteListbydate=new ArrayList<Notes>();
	private MyDataBase mdb = null;
	//private byte[] img1data, img2data;
	private Button filterButton, addnotebutton;
	private EditText searchEdittext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notesactivity);
		noteslistView = (ListView) findViewById(R.id.noteslistView);

		mdb = new MyDataBase(NotesActivity.this);
		noteList = mdb.getAllNotes();
		adapter = new NotesAdapter(NotesActivity.this, noteList);
		noteslistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		filterButton = (Button) findViewById(R.id.filterButton);
		addnotebutton = (Button) findViewById(R.id.addnotebutton);
		searchEdittext = (EditText) findViewById(R.id.searchEdittext);

		searchEdittext.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                //NotesActivity.this.adapter.getFilter().filter(cs);
            	String text = searchEdittext.getText().toString().toLowerCase(Locale.getDefault());
                adapter.getFilter(text);
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
		
		filterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
/*				if(filterButton.getText().toString().equalsIgnoreCase("setByDate")){
					filterByDatewise();
					filterButton.setText("setByText");
				}else{
					filterByText();
					filterButton.setText("setByDate");
				}*/
				setDatatoListview();
			}
		});
		addnotebutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(NotesActivity.this,
						AddNotesActivity.class));
			}
		});
		//noteslistView.setOnItemLongClickListener(NotesActivity.this);
		noteslistView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					final int position, long id) {
				final Notes noteObj = (Notes) v.getTag();
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						NotesActivity.this);

				//alertDialog.setTitle("Settings");
				alertDialog
						.setMessage("Are you sure want to delete Note");

				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								//Notes noteObj=noteList.get(position);
								// delete selected item in db
								boolean flag=mdb.deleteNote(noteObj.getTitle(), noteObj.getNotedate());
								if(flag){
									//noteList.remove(position);
									setDatatoListview();
									adapter.notifyDataSetChanged();
								}
								dialog.cancel();
							}
						});
				alertDialog.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
				alertDialog.show();
				return false;
			}
		});
		
		noteslistView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
			Notes obj=(Notes) v.getTag();
			Intent i=new Intent(NotesActivity.this, NoteDiaplayActivty.class);
			i.putExtra("title", ""+obj.getTitle());
			i.putExtra("category", ""+obj.getCategory());
			i.putExtra("note", ""+obj.getNote());
			i.putExtra("date", ""+obj.getNotedate());
			i.putExtra("img1", ""+obj.getImg1_location());
			i.putExtra("img2", ""+obj.getImg2_location());
			i.putExtra("record", ""+obj.getRecord_location());
			i.putExtra("latlon", ""+obj.getLatitude()+" , "+obj.getLongitude());
			startActivity(i);
			}
		});
	}

	protected void filterByDatewise() {
		noteListbydate.clear();
		noteListbydate = mdb.getAllNotesByDate();
		adapter = new NotesAdapter(NotesActivity.this, noteListbydate);
		noteslistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	protected void filterByText() {
		noteList.clear();
		noteList = mdb.getAllNotes();
		adapter = new NotesAdapter(NotesActivity.this, noteList);
		noteslistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		filterByDatewise();
		filterButton.setText("setByText");
		//setDatatoListview();
		/*noteList = mdb.getAllNotes();
		adapter = new NotesAdapter(NotesActivity.this, noteList);
		noteslistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();*/
	}

	private void setDatatoListview() {
		if(filterButton.getText().toString().equalsIgnoreCase("setByDate")){
			filterByDatewise();
			filterButton.setText("setByText");
		}else{
			filterByText();
			filterButton.setText("setByDate");
		}
	}
}
