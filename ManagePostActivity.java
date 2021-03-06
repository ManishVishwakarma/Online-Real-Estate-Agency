package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ManagePostActivity extends Fragment implements OnItemClickListener {

	View rootview;
	ListView list;
	Connection connect;
	BaseAdapter AD;
	Bitmap BitImage;
	ImageView IconImage;
	Bundle bb;
	String user, pass;
	ArrayList<byte[]> Image_list;
	ArrayList<String> prop_id_list, title_list, price_list, city_list,
			locality_list, user_list, propfor_list, type_list;

	TextView title, descp, price, prop_for, type;
	Progress call;
	int pos;

	public ManagePostActivity() {
		call = new Progress();
		connect = call.CONN();
		pos = 0;
		prop_id_list = title_list = price_list = city_list = locality_list = null;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		rootview = inflater.inflate(R.layout.activity_manage_post, container,
				false);
		list = (ListView) rootview.findViewById(R.id.menu_list_item);
		// bb=getActivity().getIntent().getExtras();
		list.setOnItemClickListener(this);
		QuerySQLTitle("SELECT P.prop_id,prop_title,prop_for,prop_user,prop_type,prop_locality,prop_city,prop_image_one,prop_budget FROM Property P INNER JOIN Property_Details PD ON P.prop_id=PD.prop_id WHERE prop_user='"
				+ getActivity().getIntent().getExtras().getString("user_email")
				+ "'");

		return rootview;

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());
		adb.setTitle("Delete?");
		adb.setMessage("Are you sure you want to delete this post? ");
		final int positionToRemove = position;
		pos = position;
		adb.setNegativeButton("Cancel", null);
		adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				QueryDelete("DELETE FROM Feature WHERE prop_id='"
						+ prop_id_list.get(positionToRemove) + "'");
				QueryDelete("DELETE FROM Property_Details WHERE prop_id='"
						+ prop_id_list.get(positionToRemove) + "'");
				QueryDelete("DELETE FROM Property WHERE prop_id='"
						+ prop_id_list.get(positionToRemove) + "'");
				QuerySQLTitle("SELECT P.prop_id,prop_title,prop_for,prop_user,prop_type,prop_locality,prop_city,prop_image_one,prop_budget FROM Property P INNER JOIN Property_Details PD ON P.prop_id=PD.prop_id WHERE prop_user='"
						+ getActivity().getIntent().getExtras().getString("user_email")
						+ "'");
				AD.notifyDataSetChanged();

			}
		});
		adb.show();
	}

	public void QuerySQLTitle(String COMANDSQL) {
		ResultSet rs;
		prop_id_list = new ArrayList<String>();
		title_list = new ArrayList<String>();
		type_list = new ArrayList<String>();
		propfor_list = new ArrayList<String>();
		user_list = new ArrayList<String>();
		Image_list = new ArrayList<byte[]>();

		price_list = new ArrayList<String>();
		city_list = new ArrayList<String>();

		locality_list = new ArrayList<String>();
		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {

				prop_id_list.add(rs.getString("prop_id"));
				title_list.add(rs.getString("prop_title"));
				propfor_list.add(rs.getString("prop_for"));
				type_list.add(rs.getString("prop_type"));
				user_list.add(rs.getString("prop_user"));
				Image_list.add(rs.getBytes("prop_image_one"));
				price_list.add(rs.getBigDecimal("prop_budget").toString());
				locality_list.add(rs.getString("prop_locality") + ","
						+ rs.getString("prop_city"));
			}
			AD = new BaseAdapter() {

				public View getView(int position, View arg1, ViewGroup parent) {
					LayoutInflater inflater = getActivity().getLayoutInflater();
					arg1 = inflater.inflate(R.layout.item_layout, null);
					IconImage = (ImageView) arg1.findViewById(R.id.item_image);
					title = (TextView) arg1.findViewById(R.id.item_title);
					descp = (TextView) arg1.findViewById(R.id.item_descp);
					price = (TextView) arg1.findViewById(R.id.item_price);
					type = (TextView) arg1.findViewById(R.id.item_type);
					prop_for = (TextView) arg1.findViewById(R.id.item_for);

					BitImage = BitmapFactory.decodeByteArray(
							Image_list.get(position), 0,
							Image_list.get(position).length);
					IconImage.setImageBitmap(BitImage);

					title.setText(title_list.get(position));
					descp.setText("Locality:  " + locality_list.get(position));
					price.setText("Rs. " + price_list.get(position));
					type.setText("Type: " + type_list.get(position));
					prop_for.setText("For : " + propfor_list.get(position));
					return arg1;
				}

				@Override
				public int getCount() {
					// TODO Auto-generated method stub
					return Image_list.size();
				}

				@Override
				public Object getItem(int position) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public long getItemId(int position) {
					// TODO Auto-generated method stub
					return 0;
				}

			};
			list.setAdapter(AD);

		} catch (SQLException e) {
			Toast.makeText(getActivity().getBaseContext(),
					"No such user Exist!!", Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}

	

	public void QueryDelete(String COMANDSQL) {
		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			statm.executeUpdate(COMANDSQL);

		} catch (Exception e) {
			Toast.makeText(getActivity().getBaseContext(),
					"No such user Exist!!", Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}

}
