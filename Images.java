package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class Images extends Activity {

	private ImageView imageview;
	SpinnerAdapter ImageAdp;
	Gallery gallery;
	String Prop_ID;
	byte[] prop_image_1, prop_image_2, prop_image_3, prop_image_4;
	Progress call;
	Connection connect;
	ArrayList<byte[]> Image_list;
	public byte[] pics;
	Bitmap BitImage;

	public Images() {
		call = new Progress();
		connect = call.CONN();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_images);

		Prop_ID = getIntent().getExtras().getString("prop_id");
		QuerySQLImages("SELECT prop_image_one,prop_image_two,prop_image_three,prop_image_four FROM Property_Details WHERE prop_id='"
				+ Prop_ID + "'");
		gallery = (Gallery) findViewById(R.id.Image_gallery);
		gallery.setAdapter(new ImageAdapter(this));

	}

	public void QuerySQLImages(String COMANDSQL) {
		ResultSet rs;
		Image_list = new ArrayList<byte[]>();
		try {
			Statement statm = connect.createStatement();
			Log.d("Connection", "Connected");
			rs = statm.executeQuery(COMANDSQL);
			Log.d("Connection", "Value is passed");
			// configure simpleadapter

			while (rs.next()) {
				Image_list.add(rs.getBytes(1));
				Image_list.add(rs.getBytes(2));
				Image_list.add(rs.getBytes(3));
				Image_list.add(rs.getBytes(4));

			}

		} catch (SQLException e) {
			Toast.makeText(getBaseContext(), "No Details Avaliable",
					Toast.LENGTH_SHORT).show();
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public class ImageAdapter extends BaseAdapter {

		public Context context;

		public ImageAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return Image_list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView image = null;
			Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size);
			if (convertView == null) {
				image = new ImageView(context);
				image.setLayoutParams(new Gallery.LayoutParams(size.x, size.y));

			} else {
				image = (ImageView) convertView;
			}
			BitImage = BitmapFactory.decodeByteArray(Image_list.get(position),
					0, Image_list.get(position).length);
			image.setImageBitmap(BitImage);
			return image;
		}

	}
}
