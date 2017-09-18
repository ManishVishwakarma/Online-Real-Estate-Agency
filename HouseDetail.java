package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HouseDetail extends Activity {
	TextView title_View, address_View, price_View, area_View, parking_View,
			balcone_View,type_View,propfor_View, bedroom_View, bathroom_View, kitchen_View,
			feature_View, loc_View, subloc_View, year_View,phone_View,email_View, Name_View;
	public ListView listitems;
	Progress call;
	Connection connect;
	String address, price, area, parking, balcone, bedroom, bathroom, kitchen,
			feature,propfor,type, loc, subloc, year, phone, Fname, Lname, Prop_id, Email;
	Button ownCall, ownEmail;

	public HouseDetail() {
		call = new Progress();
		connect = call.CONN();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_house_detail);
		initialize();

		QuerySQLFeature("Select  prop_details,bedrooms,bathrooms,kitchen,parking,balcone FROM Feature WHERE prop_id='"
				+ Prop_id + "'");
		QuerySQLDetails("Select  prop_area,prop_locality,prop_sub_locality,prop_year_build,prop_address,prop_budget FROM Property_Details WHERE prop_id='"
				+ Prop_id + "'");
		QuerySQLOwner("Select u_Fname,u_Lname,u_Contact FROM [User] WHERE u_Email='"
				+ Email + "'");
		DisplayValue();
		ownCall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent callme = new Intent(Intent.ACTION_DIAL);
				callme.setData(Uri.parse("tel:" + phone));
				startActivity(callme);

			}
		});
		ownEmail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent emailIntent = new Intent(Intent.ACTION_SEND);
				emailIntent.setType("text/plain");
				emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{Email});
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Look4Home Customer");
				emailIntent.putExtra(Intent.EXTRA_TEXT   , "Hey I am Interested in your Property....");
				startActivity(emailIntent); 

			}
		});

	}

	public void DisplayValue() {
		// TODO Auto-generated method stub
		title_View.setText(getIntent().getExtras().getString("title"));
		address_View.setText(address);
		area_View.setText(area + " sqrt ft.");
		price_View.setText("Rs. " + price);
		loc_View.setText(loc);
		subloc_View.setText(subloc);
		year_View.setText(year);

		feature_View.setText(feature);
		bedroom_View.setText(bedroom);
		bathroom_View.setText(bathroom);
		kitchen_View.setText(kitchen);

		if (Integer.parseInt(parking) == 1)
			parking_View.setText("Included");
		else
			parking_View.setText("Not Included");

		if (Integer.parseInt(balcone) == 1)
			balcone_View.setText("Included");
		else
			balcone_View.setText("Not Included");
		Name_View.setText(Fname+" "+Lname);
		phone_View.setText(phone);
		email_View.setText(Email);
		propfor_View.setText(propfor);
		type_View.setText(type);
		
		
	}

	public void initialize() {
		Prop_id = getIntent().getExtras().getString("prop_id");
		Email = getIntent().getExtras().getString("user_email");
		propfor=getIntent().getExtras().getString("for");
		type=getIntent().getExtras().getString("type");
		Name_View = (TextView) findViewById(R.id.detail_OwnName);
		email_View = (TextView) findViewById(R.id.detail_OwnEmail);
		phone_View = (TextView) findViewById(R.id.detail_OwnContact);
		title_View = (TextView) findViewById(R.id.detail_title);
		address_View = (TextView) findViewById(R.id.detail_addr);
		price_View = (TextView) findViewById(R.id.detail_price);
		area_View = (TextView) findViewById(R.id.detail_area);
		parking_View = (TextView) findViewById(R.id.detail_Parking);
		balcone_View = (TextView) findViewById(R.id.detail_balcone);
		bedroom_View = (TextView) findViewById(R.id.detail_Bedroom);
		bathroom_View = (TextView) findViewById(R.id.detail_Bathroom);
		kitchen_View = (TextView) findViewById(R.id.detail_Kitchen);
		feature_View = (TextView) findViewById(R.id.detail_Feature);
		loc_View = (TextView) findViewById(R.id.detail_locality);
		subloc_View = (TextView) findViewById(R.id.detail_sublocality);
		type_View=(TextView) findViewById(R.id.detail_type);
		propfor_View=(TextView) findViewById(R.id.detail_for);
		year_View = (TextView) findViewById(R.id.detail_yearbuild);
		ownCall = (Button) findViewById(R.id.call);
		ownEmail = (Button) findViewById(R.id.Email);

	}

	public void QuerySQLFeature(String COMANDSQL) {
		ResultSet rs;

		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {
				feature = rs.getString(1);
				bedroom = rs.getString(2);
				bathroom = rs.getString(3);
				kitchen = rs.getString(4);

				parking = rs.getString(5);

				balcone = rs.getString(6);
			}

		} catch (SQLException e) {
			Toast.makeText(getBaseContext(), "No Details Avaliable",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QuerySQLDetails(String COMANDSQL) {
		ResultSet rs;

		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {

				area = rs.getString(1);
				loc = rs.getString(2);
				subloc = rs.getString(3);
				year = rs.getString(4);
				address = rs.getString(5);
				price = rs.getString(6);
			}

		} catch (SQLException e) {
			Toast.makeText(getBaseContext(), "No Details Avaliable",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QuerySQLOwner(String COMANDSQL) {
		ResultSet rs;

		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {
				Fname = rs.getString(1);
				Lname = rs.getString(2);
				phone = rs.getString(3);
			}

		} catch (SQLException e) {
			Toast.makeText(getBaseContext(), "No Details Avaliable",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}
}
