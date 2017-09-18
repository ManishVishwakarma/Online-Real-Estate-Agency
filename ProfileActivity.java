package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class ProfileActivity extends Fragment {
	View rootview;
	Connection connect;
	RadioButton genmale, genfemale;
	RadioGroup gengrp;
	EditText user_pass, user_con_pass, user_first_name, user_last_name,
			user_phone_no, user_address, user_country, user_state;
	String username, passwrd, conpass, gender, firstname, lastname, contact,
			address, country, state, user_id;
	Button done, cancel;
	Bundle bundle;

	public ProfileActivity() {
		Progress call = new Progress();
		connect = call.CONN();

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview = inflater
				.inflate(R.layout.activity_profile, container, false);
		bundle = getActivity().getIntent().getExtras();
		user_id = bundle.getString("user_id");
		gengrp = (RadioGroup) rootview.findViewById(R.id.edt_Gender);
		genmale = (RadioButton) rootview.findViewById(R.id.edt_GenderMale);
		genfemale = (RadioButton) rootview.findViewById(R.id.edt_GenderFemale);
		user_pass = (EditText) rootview.findViewById(R.id.edt_password);
		user_con_pass = (EditText) rootview.findViewById(R.id.edt_conpassword);
		user_first_name = (EditText) rootview.findViewById(R.id.edt_FirstName);
		user_last_name = (EditText) rootview.findViewById(R.id.edt_LastName);
		user_phone_no = (EditText) rootview.findViewById(R.id.edt_PhoneNo);
		user_address = (EditText) rootview.findViewById(R.id.edt_Address);
		user_country = (EditText) rootview.findViewById(R.id.edt_Country);
		user_state = (EditText) rootview.findViewById(R.id.edt_State);

		done = (Button) rootview.findViewById(R.id.edt_done);
		cancel = (Button) rootview.findViewById(R.id.edt_cancel);

		String querySelectUser = "SELECT u_Fname,u_Lname,u_Address,u_Country,u_State,u_Gender,u_Contact FROM [User] WHERE user_id="
				+ user_id + " and u_user='u'";
		String querySelectLogin = "SELECT password FROM Login WHERE user_id="
				+ user_id + " and u_user='u'";

		QuerySQLUser(querySelectUser);
		QuerySQLLogin(querySelectLogin);

		putValues();

		user_pass.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user_pass.setTransformationMethod(PasswordTransformationMethod
						.getInstance());
				user_con_pass
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());

			}
		});
		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getValues();
				String queryUpdateUSER = "UPDATE [User] SET u_Fname='"
						+ firstname + "' ,u_Lname='" + lastname
						+ "' ,u_Address='" + address + "' ,u_Country='"
						+ country + "' ,u_State='" + state + "' ,u_Gender='"
						+ gender + "' ,u_Contact=" + contact
						+ " WHERE user_id=" + user_id + " and u_user='u'";

				String queryUpdateLOGIN = "UPDATE Login SET password='"
						+ passwrd + "' WHERE user_id=" + user_id
						+ " and u_user='u'";
				QuerySQLUpdate(queryUpdateUSER);
				QuerySQLUpdate(queryUpdateLOGIN);
				Toast.makeText(getActivity().getBaseContext(),
						"Profile updated Successfully!!", Toast.LENGTH_LONG)
						.show();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});

		gengrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId){
	            // Your code    
				case R.id.edt_GenderMale:
					gender="Male";
					break;
				case R.id.edt_GenderFemale:
					gender="Female";
					break;
	        }

			}
		});

		return rootview;
	}

	private void getValues() {
		// TODO Auto-generated method stub
		passwrd = user_pass.getText().toString();
		conpass = user_con_pass.getText().toString();

		firstname = user_first_name.getText().toString();
		lastname = user_last_name.getText().toString();
		contact = user_phone_no.getText().toString();
		address = user_address.getText().toString();
		country = user_country.getText().toString();
		state = user_state.getText().toString();
	}

	public void QuerySQLUser(String COMANDSQL) {
		ResultSet rs;
		try {
			Statement statm = connect.createStatement();
			rs = statm.executeQuery(COMANDSQL);
			while (rs.next()) {
				firstname = rs.getString("u_Fname");
				lastname = rs.getString("u_Lname");
				address = rs.getString("u_Address");
				country = rs.getString("u_Country");
				state = rs.getString("u_State");
				gender = rs.getString("u_Gender");
				contact = rs.getString("u_Contact");
			}
			statm.close();
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QuerySQLLogin(String COMANDSQL) {
		ResultSet rs;
		try {
			Statement statm = connect.createStatement();
			rs = statm.executeQuery(COMANDSQL);
			while (rs.next()) {
				passwrd = rs.getString("password");
			}
			statm.close();
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QuerySQLUpdate(String COMANDSQL) {

		try {
			Statement statm = connect.createStatement();
			statm.executeUpdate(COMANDSQL);

			statm.close();
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public String setgender() {
		int var = gender.length();
		switch (var) {
		case 4:
			genmale.setChecked(true);
			break;
		case 6:
			genfemale.setChecked(true);
			break;
		}
		return gender;

	}

	public String getgender(View v) {
		switch (v.getId()) {
		case R.id.edt_GenderMale:
			gender = "Male";
			break;
		case R.id.edt_GenderFemale:
			gender = "Female";
			break;
		}
		return gender;

	}

	public void putValues() {
		user_pass.setText(passwrd);
		user_con_pass.setText(passwrd);
		setgender();
		user_first_name.setText(firstname);
		user_last_name.setText(lastname);
		user_phone_no.setText(contact);
		user_address.setText(address);
		user_country.setText(country);
		user_state.setText(state);
	}

}
