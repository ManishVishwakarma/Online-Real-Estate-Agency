package com.example.look4home;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class LocMap extends Activity implements OnMapReadyCallback {
	MapFragment mapfrg;
	String newProp_ID,prop_title,prop_type,prop_for;
	Progress call;
	Connection connect;
	double lati, logi;


	public LocMap() {
		call = new Progress();
		connect = call.CONN();
		
		
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loc_map);
		
		newProp_ID = getIntent().getExtras().getString("prop_id");
		QuerySQLLatLog("Select prop_type,prop_for,prop_latitude,prop_longitude,prop_title FROM Property WHERE prop_id="
				+ newProp_ID + "");
		mapfrg = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
		mapfrg.getMapAsync(this);
		
	}

	@Override
	public void onMapReady(GoogleMap map) {
		// TODO Auto-generated method stub
		LatLng sydney = new LatLng(lati, logi);
		map.setMyLocationEnabled(true);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
		map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		map.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.ic_action_place))
				.title(prop_title)
				.snippet("Type:"+prop_type+" For"+prop_for)
				.position(sydney));

	}

	public void QuerySQLLatLog(String COMANDSQL) {
		ResultSet rs;

		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {
				lati = Double.parseDouble(rs.getString("prop_latitude"));
				logi =Double.parseDouble( rs.getString("prop_longitude"));
				prop_title=rs.getString("prop_title");
				prop_type=rs.getString("prop_type");
				prop_for=rs.getString("prop_for");
			}

		} catch (SQLException e) {
			Toast.makeText(getBaseContext(), "No Details Avaliable",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}
}
