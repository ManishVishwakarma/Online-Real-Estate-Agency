package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.look4home.SearchActivity.onSomeEventListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SearchItemListActivity extends Activity {

	ArrayList<byte[]> Image_list;
	ArrayList<String> title_list, price_list, city_list, locality_list,
			user_list, propfor_list, type_list;
	String newPropID = null;
	Intent search;
	Bitmap BitImage;
	ImageView IconImage;
	TextView title, descp, price, prop_for, type;
	Progress call;
	ListView list;
	Connection connect;
	BaseAdapter AD;
	ArrayList<String> prop_id;

	public SearchItemListActivity() {
		call = new Progress();
		connect = call.CONN();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_item_list);
		
		prop_id = getIntent().getExtras().getStringArrayList("prop_id_list");
		StringBuilder commaSepValueBuilder = new StringBuilder();

		for (int i = 0; i < prop_id.size(); i++) {

			commaSepValueBuilder.append(prop_id.get(i));

			if (i != prop_id.size() - 1) {

				commaSepValueBuilder.append(", ");

			}
		}
		newPropID = commaSepValueBuilder.toString();

		list = (ListView) findViewById(R.id.abc_list_item);

		QuerySQLTitle("SELECT prop_title,prop_for,prop_user,prop_type,prop_locality,prop_city,prop_image_one,prop_budget FROM Property P INNER JOIN Property_Details PD ON P.prop_id=PD.prop_id WHERE P.prop_id IN ("
				+ newPropID + ")");

		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				search = new Intent(SearchItemListActivity.this,
						RoomDetails.class);
				search.putExtra("prop_id", prop_id.get(position));
				search.putExtra("title", title_list.get(position));
				search.putExtra("email", user_list.get(position));
				search.putExtra("for", propfor_list.get(position));
				search.putExtra("type", type_list.get(position));

				startActivity(search);
			}
		});
	}

	public void QuerySQLTitle(String COMANDSQL) {
		ResultSet rs;
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
					LayoutInflater inflater = getLayoutInflater();
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
					return title_list.size();
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
			Toast.makeText(getBaseContext(), "No such user Exist!!",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}
}
