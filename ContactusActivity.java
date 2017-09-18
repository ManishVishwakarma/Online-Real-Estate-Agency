package com.example.look4home;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ContactusActivity extends Fragment {

	View rootview;
	Button btnSub;
	EditText name,mobile,message,emailid;
	@Nullable	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview=inflater.inflate(R.layout.activity_contactus, container,false);
		name=(EditText)rootview.findViewById(R.id.fed_Name);
		
mobile=(EditText)rootview.findViewById(R.id.fed_Contact);
message=(EditText)rootview.findViewById(R.id.fed_Message);
emailid=(EditText)rootview.findViewById(R.id.fed_Email);

		btnSub=(Button)rootview.findViewById(R.id.btnSubmit);
		btnSub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mohitamichand@gmail.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "Look 4 Home Ltd. Feedback From "+name.getText().toString()+" "+mobile.getText().toString()+" "+emailid.getText().toString());
				i.putExtra(Intent.EXTRA_TEXT   , message.getText().toString());
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getActivity().getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		
		return rootview;
	}
}
