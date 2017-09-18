package com.example.look4home;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;

public class AddpostActivity extends Fragment implements OnClickListener {
	View rootview;
	int i = 0, ImageViewID;
	int[] len = new int[4];
	byte[] Image;
	String[] ImageIndex = new String[4];
	double[] LatLong = new double[2];
	File[] file = new File[4];
	FileInputStream[] fis = new FileInputStream[4];
	Button done, cancel;
	EditText title, price, year, sqrfeet, address, locality, sub_locality,
			city, pincode, features;
	RadioGroup propfor;
	Connection connect;
	ImageView imageview1, imageview2, imageview3, imageview4;
	ImageView[] ImageViewer = new ImageView[4];

	String[] ImagePath = new String[4];
	Spinner Prop_spinner, bedroom_spinner, parking_spinner, balcone_spinner,
			bathroom_spinner, kitchen_spinner;
	String Prop_type = null, Prop_For = "Buy", beds, bath, kitch, balcone,
			parking;
	String user_email;
	private int PICK_IMAGE_REQUEST = 1;

	public AddpostActivity() {
		Progress call = new Progress();
		connect = call.CONN();

		beds = bath = kitch = "0";
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub;

		rootview = inflater
				.inflate(R.layout.activity_addpost, container, false);
		Bundle bundle = getActivity().getIntent().getExtras();
		user_email = bundle.getString("user_email");
		done = (Button) rootview.findViewById(R.id.add_done);
		cancel = (Button) rootview.findViewById(R.id.add_cancel);

		propfor = (RadioGroup) rootview.findViewById(R.id.PropFor);

		title = (EditText) rootview.findViewById(R.id.add_Title);
		price = (EditText) rootview.findViewById(R.id.add_price);
		year = (EditText) rootview.findViewById(R.id.add_year);
		sqrfeet = (EditText) rootview.findViewById(R.id.add_Sqrfeet);
		address = (EditText) rootview.findViewById(R.id.add_address);
		locality = (EditText) rootview.findViewById(R.id.add_locality);
		sub_locality = (EditText) rootview.findViewById(R.id.add_Sub_locality);
		city = (EditText) rootview.findViewById(R.id.add_city);
		pincode = (EditText) rootview.findViewById(R.id.add_pincode);
		features = (EditText) rootview.findViewById(R.id.add_features);

		ImageViewer[0] = (ImageView) rootview.findViewById(R.id.image1);
		ImageViewer[1] = (ImageView) rootview.findViewById(R.id.image2);
		ImageViewer[2] = (ImageView) rootview.findViewById(R.id.image3);
		ImageViewer[3] = (ImageView) rootview.findViewById(R.id.image4);

		ImageViewer[0].setOnClickListener(this);
		ImageViewer[1].setOnClickListener(this);
		ImageViewer[2].setOnClickListener(this);
		ImageViewer[3].setOnClickListener(this);

		propfor.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.buy:
					Prop_For = "Buy";
					break;
				case R.id.rent:
					Prop_For = "Rent";
					break;
				case R.id.buyrent:
					Prop_For = "BuyRent";
					break;
				}
			}
		});

		balcone_spinner = (Spinner) rootview.findViewById(R.id.add_balcone);
		parking_spinner = (Spinner) rootview.findViewById(R.id.add_parking);
		ArrayAdapter includ = ArrayAdapter.createFromResource(getActivity(),
				R.array.included, android.R.layout.simple_spinner_item);
		balcone_spinner.setAdapter(includ);
		parking_spinner.setAdapter(includ);
		balcone_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					balcone = "1";
					break;
				case 2:
					balcone = "0";
					break;
				default:
					balcone = "0";
					break;
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		parking_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					parking = "1";
					Toast.makeText(getActivity(), "Included", Toast.LENGTH_LONG)
							.show();
					break;
				case 2:
					parking = "0";
					break;
				default:
					parking = "0";
					break;

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		bedroom_spinner = (Spinner) rootview.findViewById(R.id.add_bedrooms);
		bathroom_spinner = (Spinner) rootview.findViewById(R.id.add_bathroom);
		kitchen_spinner = (Spinner) rootview.findViewById(R.id.add_kitchen);
		ArrayAdapter num = ArrayAdapter.createFromResource(getActivity(),
				R.array.numbers, android.R.layout.simple_spinner_item);
		bedroom_spinner.setAdapter(num);
		bathroom_spinner.setAdapter(num);
		kitchen_spinner.setAdapter(num);
		bedroom_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

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
		bathroom_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
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
		kitchen_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					kitch = "0";
					break;
				case 1:
					kitch = "1";
					break;
				case 2:
					kitch = "2";
					break;
				case 3:
					kitch = "3";
					break;
				case 4:
					kitch = "4";
					break;
				case 5:
					kitch = "5";
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		Prop_spinner = (Spinner) rootview.findViewById(R.id.add_Property_type);
		ArrayAdapter data = ArrayAdapter.createFromResource(getActivity()
				.getBaseContext(), R.array.prop_type,
				android.R.layout.simple_spinner_item);
		Prop_spinner.setAdapter(data);
		Prop_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 1:
					Prop_type = "Flat";
					break;
				case 2:
					Prop_type = "House";
					break;
				case 3:
					Prop_type = "Banglow";
					break;
				default:
					Prop_type = "Other";
					break;

				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}

		});

		done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String addr = address.getText().toString() + " "
						+ sub_locality.getText().toString() + " "
						+ locality.getText().toString() + " "
						+ city.getText().toString() + " "
						+ pincode.getText().toString();

				getLocationFromAddress(addr);

				QuerySQLUpdate("INSERT INTO Property Values('p','" + user_email
						+ "','" + Prop_type + "','" + Prop_For + "','"
						+ title.getText().toString() + "'," + LatLong[0] + ","
						+ LatLong[1] + ")");

				QuerySQLUpdate("INSERT INTO Feature (f_feat,p_prop,prop_details,bedrooms,bathrooms,kitchen,parking,balcone) Values('f','p','"
						+ features.getText().toString()
						+ "','"
						+ beds
						+ "','"
						+ bath
						+ "','"
						+ kitch
						+ "','"
						+ parking
						+ "','"
						+ balcone + "')");

				insertImage(connect, ImageIndex);
				Toast.makeText(getActivity().getBaseContext(),
						"Thank You for Posting....", Toast.LENGTH_LONG).show();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				price.setText("");
				year.setText("");
				sqrfeet.setText("");
				address.setText("");
				locality.setText("");
				sub_locality.setText("");
				city.setText("");
				pincode.setText("");

			}
		});

		return rootview;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == PICK_IMAGE_REQUEST
				&& resultCode == getActivity().RESULT_OK && data != null
				&& data.getData() != null) {

			Uri uri = data.getData();

			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity()
						.getContentResolver(), uri);

				i = 0;
				while (i <= 3) {

					if (ImageViewer[i].getId() == ImageViewID) {
						ImageViewer[i].setImageBitmap(bitmap);

						String[] filePathColumn = { MediaStore.Images.Media.DATA };
						Cursor cursor = getActivity().getContentResolver()
								.query(uri, filePathColumn, null, null, null);
						if (cursor.moveToFirst()) {
							int columnIndex = cursor
									.getColumnIndex(filePathColumn[0]);
							String filePath = cursor.getString(columnIndex);

							try {
								FileInputStream fis = new FileInputStream(
										filePath);
								Image = new byte[fis.available()];
								fis.read(Image);

								ImageIndex[i] = filePath;
							} catch (IOException e) {

								Toast.makeText(getActivity().getBaseContext(),
										"Error: Image is not avaliable",
										Toast.LENGTH_SHORT).show();
							}

							/*
							 * Bitmap abc=BitmapFactory.decodeByteArray(Image,
							 * 0, Image.length);
							 * ImageViewer[3].setImageBitmap(abc);
							 */
							// To get the extension of Image:
							// filePath.substring(filePath.lastIndexOf("."))
						}
						cursor.close();

						break;
					} else {

						i++;

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		ImageViewID = v.getId();
		Intent intent = new Intent();
		// Show only images, no videos or anything else
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		// Always show the chooser (if there are multiple options available)
		startActivityForResult(Intent.createChooser(intent, "Select Picture"),
				PICK_IMAGE_REQUEST);
	}

	public void getLocationFromAddress(String strAddress) {

		Geocoder coder = new Geocoder(getActivity());
		List<Address> address;
		

		try {
			address = coder.getFromLocationName(strAddress, 1);
			if (address == null) {
				this.address.setError("Enter proper Address");
				this.address.requestFocus();
			}
			Address location = address.get(0);
			LatLong[0] = location.getLatitude();
			LatLong[1] = location.getLongitude();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertImage(Connection conn, String[] img) {

		String query;
		PreparedStatement pstmt;

		try {
			for (int i = 0; i <= 3; i++) {
				file[i] = new File(img[i]);
				fis[i] = new FileInputStream(file[i]);
				len[i] = (int) file[i].length();
			}

			query = ("INSERT INTO Property_Details"
					+ " (d_deta,p_prop,prop_address,prop_city ,prop_pincode,"
					+ "prop_image_one,prop_image_two,"
					+ "prop_image_three,prop_image_four,"
					+ "prop_budget,prop_area,prop_locality,"
					+ "prop_sub_locality,prop_year_build) "
					+ "Values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "d");
			pstmt.setString(2, "p");
			pstmt.setString(3, address.getText().toString());
			pstmt.setString(4, city.getText().toString());
			pstmt.setString(5, pincode.getText().toString());
			pstmt.setBinaryStream(6, fis[0], len[0]);
			pstmt.setBinaryStream(7, fis[1], len[1]);
			pstmt.setBinaryStream(8, fis[2], len[2]);
			pstmt.setBinaryStream(9, fis[3], len[3]);
			pstmt.setBigDecimal(10, new BigDecimal(price.getText().toString()));
			pstmt.setBigDecimal(11,
					new BigDecimal(sqrfeet.getText().toString()));
			pstmt.setString(12, locality.getText().toString());
			pstmt.setString(13, sub_locality.getText().toString());
			pstmt.setString(14, year.getText().toString());

			pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(getActivity().getBaseContext(),
					"Unable to post.. \nCheck Your Connection",
					Toast.LENGTH_LONG).show();

		}

	}

	public void QuerySQLUpdate(String COMANDSQL) {

		try {
			Statement statm = connect.createStatement();

			statm.executeUpdate(COMANDSQL);

			statm.close();
		} catch (SQLException e) {
			Log.e("ERROR: The ", e.getMessage());
			Toast.makeText(getActivity().getBaseContext(), "Error : occured",
					Toast.LENGTH_LONG).show();

		}
	}

}
