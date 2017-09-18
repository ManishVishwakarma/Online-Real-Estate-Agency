package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchActivity extends Fragment {
	Spinner room_spin, bed_spin, bath_spin, kitch_spin;
	View search_view;
	String beds, bath, kitc, balc, park, proptype, minbud, maxbud, minyear,
			maxyear, SearchQuery, ExtraQuery, locality;
	LinearLayout ShowHideLayout;
	Progress call;
	int i = 0;
	Connection connect;
	RadioGroup grpPark, grpBalc;
	Button btnFind, btnReset, btnShowHideLayout;
	EditText edtMinBud, edtMaxBud, edtMinYear, edtMaxYear, edtLocality;
	ArrayList<String> prop_id;
	onSomeEventListener someEventListener;
	
	
	public interface onSomeEventListener {
	    public void someEvent(ArrayList<String> pid);
	  }

	public SearchActivity() {
		call = new Progress();
		connect = call.CONN();
		beds = bath = kitc = balc = park = "0";
		proptype = "Other";
		ExtraQuery = "";
	}

	public void getValues() {
		minbud = edtMinBud.getText().toString();
		maxbud = edtMaxBud.getText().toString();
		minyear = edtMinYear.getText().toString();
		maxyear = edtMaxYear.getText().toString();
		locality = edtLocality.getText().toString();

		if (i == 1) {
			ExtraQuery = " AND (bedrooms=" + beds + " OR bathrooms=" + bath
					+ " OR kitchen=" + kitc + " OR parking=" + park
					+ " OR balcone=" + balc + ") OR prop_year_build BETWEEN "
					+ minyear + " AND " + maxyear;
		} else {
			ExtraQuery = "";
		}

		SearchQuery = "SELECT P.prop_id FROM Property P INNER JOIN Property_Details PD ON P.prop_id=PD.prop_id INNER JOIN Feature F ON PD.prop_id=F.prop_id"
				+ " WHERE prop_locality LIKE '%"
				+ locality
				+ "%'"
				+ " AND prop_type LIKE '%"
				+ proptype
				+ "%'"
				// + " OR prop_for LIKE '%"++"%' "
				// + " OR prop_for LIKE '%"++"%' "
				+ " AND prop_budget BETWEEN "
				+ minbud
				+ " AND "
				+ maxbud
				+ ExtraQuery;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		search_view = inflater.inflate(R.layout.activity_search, container,
				false);
		// TODO Auto-generated method stub
		edtMinBud = (EditText) search_view.findViewById(R.id.min);
		edtMaxBud = (EditText) search_view.findViewById(R.id.max);
		edtMinYear = (EditText) search_view.findViewById(R.id.year_min);
		edtMaxYear = (EditText) search_view.findViewById(R.id.year_max);
		edtLocality = (EditText) search_view.findViewById(R.id.search_Loaclity);

		ShowHideLayout = (LinearLayout) search_view
				.findViewById(R.id.LayoutShowHide);
		btnShowHideLayout = (Button) search_view.findViewById(R.id.ShowHide);
		ShowHideLayout.setVisibility(LinearLayout.GONE);

		grpPark = (RadioGroup) search_view.findViewById(R.id.search_Park);
		grpBalc = (RadioGroup) search_view.findViewById(R.id.search_balc);
		btnFind = (Button) search_view.findViewById(R.id.find);
		btnReset = (Button) search_view.findViewById(R.id.reset);

		room_spin = (Spinner) search_view.findViewById(R.id.room_spinner);
		ArrayAdapter data = ArrayAdapter.createFromResource(getActivity()
				.getBaseContext(), R.array.prop_type,
				android.R.layout.simple_spinner_item);
		room_spin.setAdapter(data);

		bed_spin = (Spinner) search_view.findViewById(R.id.beds_spinner);
		bath_spin = (Spinner) search_view.findViewById(R.id.baths_spinner);
		kitch_spin = (Spinner) search_view.findViewById(R.id.kitch_spinner);
		ArrayAdapter num = ArrayAdapter.createFromResource(getActivity(),
				R.array.numbers, android.R.layout.simple_spinner_item);
		bed_spin.setAdapter(num);
		bath_spin.setAdapter(num);
		kitch_spin.setAdapter(num);

		bed_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					beds = "0";
					break;
				case 1:
					beds = "1";
					break;
				case 2:
					beds = "2";
					break;
				case 3:
					beds = "3";
					break;

				case 4:
					beds = "4";
					break;
				case 5:
					beds = "5";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		bath_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					bath = "0";
					break;
				case 1:
					bath = "1";
					break;
				case 2:
					bath = "2";
					break;
				case 3:
					bath = "3";
					break;

				case 4:
					bath = "4";
					break;
				case 5:
					bath = "5";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		kitch_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					kitc = "0";
					break;
				case 1:
					kitc = "1";
					break;
				case 2:
					kitc = "2";
					break;
				case 3:
					kitc = "3";
					break;

				case 4:
					kitc = "4";
					break;
				case 5:
					kitc = "5";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		room_spin.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					proptype = "Flat";
					break;
				case 2:
					proptype = "House";
					break;
				case 3:
					proptype = "Bunglow";
					break;
				default:
					proptype = "Other";
					break;

				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		grpPark.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				// Your code
				case R.id.par_included:
					park = "1";
					break;
				case R.id.par_notincluded:
					park = "0";
					break;
				}
			}
		});

		grpBalc.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				// Your code
				case R.id.balc_included:
					balc = "1";
					break;
				case R.id.balc_notincluded:
					balc = "0";
					break;
				}
			}
		});


		
		btnFind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getValues();
				QuerySQLSearch(SearchQuery);
				if (prop_id.size() == 0) {
					Toast.makeText(getActivity().getApplicationContext(),
							"Search did not match any result!",
							Toast.LENGTH_LONG).show();
				} else {
					
					Intent intent = new Intent(getActivity(),
							SearchItemListActivity.class);
					intent.putStringArrayListExtra("prop_id_list", prop_id);
					Toast.makeText(getActivity().getApplicationContext(),
							"Search Found", Toast.LENGTH_LONG).show();
					startActivity(intent);
				}

			}
		});

		btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		btnShowHideLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (i == 0) {
					ShowHideLayout.setVisibility(LinearLayout.VISIBLE);
					i = 1;

				} else if (i == 1) {
					ShowHideLayout.setVisibility(LinearLayout.GONE);
					i = 0;

				}

			}
		});

		return search_view;
	}

	public void QuerySQLSearch(String COMANDSQL) {
		ResultSet rs;
		prop_id = new ArrayList<String>();
		try {
			Statement state = connect.createStatement();
			rs = state.executeQuery(COMANDSQL);

			while (rs.next()) {
				prop_id.add(rs.getString(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void QuerySQLRetrive(String COMANDSQL) {
		ResultSet rs;
		prop_id = new ArrayList<String>();
		try {
			Statement state = connect.createStatement();
			rs = state.executeQuery(COMANDSQL);

			while (rs.next()) {

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	

	
	

}
