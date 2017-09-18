package com.example.look4home;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class RoomDetails extends TabActivity {
	TabHost th;
	Intent i;
	Intent j;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_room_details);
		String Prop_ID = getIntent().getExtras().getString("prop_id");
		String title = getIntent().getExtras().getString("title");
		String user_email = getIntent().getExtras().getString("email");

		th = (TabHost) findViewById(android.R.id.tabhost);

		TabSpec spec = th.newTabSpec("Tab 1");// tabhost object
		i = new Intent(this, HouseDetail.class);
		i.putExtra("prop_id", Prop_ID);
		i.putExtra("title", title);
		i.putExtra("user_email", user_email);
		i.putExtra("for", getIntent().getExtras().getString("for"));
		i.putExtra("type", getIntent().getExtras().getString("type"));

		spec.setContent(i);
		spec.setIndicator("Details"); // set title
		th.addTab(spec);

		TabSpec spec2 = th.newTabSpec("Tab 2");// tabhost object
		i = new Intent(this, Images.class);
		i.putExtra("prop_id", Prop_ID);

		spec2.setContent(i);
		spec2.setIndicator("Images"); // set title
		th.addTab(spec2);

		TabSpec spec3 = th.newTabSpec("Tab 3");// tabhost object
		j = new Intent(this, LocMap.class);
		j.putExtra("prop_id", Prop_ID);
		
		spec3.setContent(j);
		spec3.setIndicator("Location"); // set title
		th.addTab(spec3);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}

}
