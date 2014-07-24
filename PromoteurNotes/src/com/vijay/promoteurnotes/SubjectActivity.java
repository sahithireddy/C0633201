package com.vijay.promoteurnotes;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SubjectActivity extends Activity{
	private Button addcategorybutton;
	private ListView categorylistView;
	private ArrayList<String> categorieslist;
	private SubjectAdapter adapter;
	private MyDataBase mdb=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subjectactivity);
		addcategorybutton=(Button) findViewById(R.id.addcategorybutton);
		categorylistView=(ListView) findViewById(R.id.categorylistView);
		
		mdb=new MyDataBase(SubjectActivity.this);
		//get categories from database
		categorieslist=mdb.getAllCategories();
		adapter=new SubjectAdapter(SubjectActivity.this, categorieslist);
		categorylistView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		
		addcategorybutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDialogToAdd();
			}
		});
		categorylistView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,
					final int position, long id) {
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(
						SubjectActivity.this);

				//alertDialog.setTitle("Settings");
				alertDialog
						.setMessage("Are you sure want to delete Category");

				alertDialog.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {

								String category=categorieslist.get(position);
								// delete selected item in db
								boolean flag=mdb.deleteCategory(category);
								if(flag){
									categorieslist.remove(position);
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
	}

	

	protected void showDialogToAdd() {
		// get prompts.xml view
		LayoutInflater li = LayoutInflater.from(SubjectActivity.this);
		View promptsView = li.inflate(R.layout.addcategory, null);

		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				SubjectActivity.this);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(promptsView);

		final EditText userInput = (EditText) promptsView
				.findViewById(R.id.categoryeditText);

		// set dialog message
		alertDialogBuilder
			.setCancelable(false)
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				// get user input and set it to result
				// edit text
				//result.setText(userInput.getText());
			    	String category=userInput.getText().toString();
			    	addCategoryinDB(category);
				dialog.dismiss();
			    }
			  })
			.setNegativeButton("Cancel",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int id) {
				dialog.cancel();
			    }
			  });

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	protected void addCategoryinDB(String category) {
		if(category.length()<=0 || category==null){
			Toast.makeText(SubjectActivity.this, "Enter Category Name", Toast.LENGTH_LONG).show();
		}else {
			//add caregory in db CATEGORIES
			   mdb.addCategory(category);
			        Toast.makeText(SubjectActivity.this, "Added Category", Toast.LENGTH_LONG).show();
			// updating the listview
			        categorieslist=mdb.getAllCategories();
					adapter=new SubjectAdapter(SubjectActivity.this, categorieslist);
					categorylistView.setAdapter(adapter);
					adapter.notifyDataSetChanged();
		}
	}
}
