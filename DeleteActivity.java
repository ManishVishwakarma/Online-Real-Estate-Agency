package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends Fragment {

	View deleteview;
	Connection connect;
	public Button btnSearch, btnDelete;
	public String a, b, u, user, pass;
	EditText edtEmail, edtPass;
	Progress call;

	public DeleteActivity() {
		call = new Progress();
		connect = call.CONN();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		deleteview = inflater.inflate(R.layout.activity_delete, container,
				false);
		btnSearch = (Button) deleteview.findViewById(R.id.del_Search);
		btnDelete = (Button) deleteview.findViewById(R.id.del_Delete);
		btnSearch.setEnabled(false);
		btnDelete.setEnabled(false);
		edtEmail = (EditText) deleteview.findViewById(R.id.del_UserName);
		edtPass = (EditText) deleteview.findViewById(R.id.del_Password);

		edtEmail.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				btnSearch.setEnabled(hasFocus);
			}
		});
		edtPass.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				btnSearch.setEnabled(hasFocus);
			}
		});
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				user = edtEmail.getText().toString();
				pass = edtPass.getText().toString();
				QuerySelect("SELECT user_name,password FROM Login WHERE user_name='"
						+ user + "' and password='" + pass + "'");
				if (user.equals(a) && pass.equals(b)) {
					new AlertDialog.Builder(getActivity())
							.setTitle("Account Found")
							.setMessage("Is this your account")
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											Toast.makeText(
													getActivity()
															.getBaseContext(),
													"Now Click on Delete Button\nTo delete Your Account",
													Toast.LENGTH_LONG).show();
											btnDelete.setEnabled(true);

										}
									})
							.setNegativeButton(android.R.string.no,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											edtEmail.setText("");
											edtPass.setText("");
											btnSearch.setEnabled(false);
											btnDelete.setEnabled(false);

										}
									})
							.setIcon(android.R.drawable.ic_dialog_alert).show();
				} else {
					Toast.makeText(getActivity().getBaseContext(), "No such account exist!!",
							Toast.LENGTH_LONG).show();

				}

			}
		});
		btnDelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QueryDelete("DELETE FROM Login WHERE user_name='" + user
						+ "' and password='" + pass + "'");
				QueryDelete("DELETE FROM [User] WHERE u_Email='" + user + "'");
				Toast.makeText(getActivity().getBaseContext(), "Account Deleted Successfully!!", Toast.LENGTH_LONG).show();
				Intent i=new Intent(getActivity().getApplicationContext(),LoginActivity.class);
				startActivity(i);
			}
		});

		return deleteview;
	}

	public void QueryDelete(String COMANDSQL) {
		try {
			Statement statm = connect.createStatement();
			statm.executeUpdate(COMANDSQL);
			
			statm.close();
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QuerySelect(String COMANDSQL) {
		ResultSet rs=null;

		try {
			Statement statm = connect.createStatement();
			rs = statm.executeQuery(COMANDSQL);

			while (rs.next()) {
				a = rs.getString("user_name");
				b = rs.getString("password");
			}
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}

	}
}