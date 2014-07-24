package com.vijay.promoteurnotes;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TabHost tabHost = getTabHost();

		// Tab for Notes
		TabSpec notespec = tabHost.newTabSpec("Notes");
		// setting Title and Icon for the Tab
		notespec.setIndicator("Notes",
				getResources().getDrawable(R.drawable.note));
		Intent notessIntent = new Intent(this, NotesActivity.class);
		notespec.setContent(notessIntent);

		// Tab for Subject
		TabSpec subspec = tabHost.newTabSpec("Subject");
		subspec.setIndicator("Subject",
				getResources().getDrawable(R.drawable.category));
		Intent subIntent = new Intent(this, SubjectActivity.class);
		subspec.setContent(subIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(notespec); // Adding photos tab
		tabHost.addTab(subspec); // Adding songs tab
	}
}
