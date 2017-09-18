package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	TextView linklog;
	EditText user_name, user_pass, user_con_pass, user_first_name,
			user_last_name, user_phone_no, user_address, user_country,
			user_state, user_email;
	public String user, pass, a, b, u;

	String username, passwrd, conpass, gender, firstname, lastname, contact,
			address, country, state, email;
	Button reg;
	Context ctx = this;
	Connection connect;
	String regexCont = "^[0-9]{10}$";

	public RegisterActivity() {
		// TODO Auto-generated constructor stub
		Progress call = new Progress();
		connect = call.CONN();
		gender = "Male";
		a = b = u = null;

		user = pass = null;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		user_first_name = (EditText) findViewById(R.id.reg_FirstName);
		user_last_name = (EditText) findViewById(R.id.reg_LastName);
		user_phone_no = (EditText) findViewById(R.id.reg_PhoneNo);
		user_address = (EditText) findViewById(R.id.reg_Address);
		user_country = (EditText) findViewById(R.id.reg_country);
		user_state = (EditText) findViewById(R.id.reg_state);
		user_email = (EditText) findViewById(R.id.reg_email);
		user_pass = (EditText) findViewById(R.id.reg_password);
		user_con_pass = (EditText) findViewById(R.id.reg_conpassword);
		linklog = (TextView) findViewById(R.id.link_to_login);
		linklog.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(i);
				finish();
			}
		});

		reg = (Button) findViewById(R.id.btnRegister);
		reg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				passwrd = user_pass.getText().toString();
				conpass = user_con_pass.getText().toString();
				firstname = user_first_name.getText().toString();
				lastname = user_last_name.getText().toString();
				contact = user_phone_no.getText().toString();
				address = user_address.getText().toString();
				country = user_country.getText().toString();
				state = user_state.getText().toString();
				email = user_email.getText().toString();
				getgender(v);

				/* To Validate The Fields */
				if (!(passwrd.equals(conpass))) {
					Toast.makeText(getBaseContext(),
							"Password are not matching !!", Toast.LENGTH_LONG)
							.show();
					user_pass.setText("");
					user_con_pass.setText("");

				} else if (passwrd.length() <= 4 || passwrd.length() >= 11) {
					user_pass.setError("Must be 5 to 10 character");
					user_pass.requestFocus();
					user_pass.setText("");
					user_con_pass.setText("");

				} else if (contact.length() <= 9
						|| contact.matches(regexCont) == false) {
					user_phone_no.setError("Enter Valid Phone Number");
					user_phone_no.requestFocus();

				} else if (!validateEmail(email)) {
					user_email.setError("Enter Valid Email ID");
					user_email.requestFocus();

				} else {
					QueryCheckUser("SELECT user_name FROM Login WHERE user_name='"
							+ email + "'");
					/* To Check User if Exist .. If Not Then Resgister It!! */
					if (email.equals(a) ) {
						Toast.makeText(getBaseContext(),
								"User already exist!\nWith same Email ID!! ",
								Toast.LENGTH_SHORT).show();
						user_email.setText("");
						user_pass.setText("");
						user_con_pass.setText("");

					} else {
						QueryInsertUser("INSERT INTO [User] VALUES('u','"
								+ firstname + "','" + lastname + "','"
								+ address + "','" + country + "','" + state
								+ "','" + gender + "'," + contact + ",'"
								+ email + "')");

						QueryInsertUser("INSERT INTO Login(u_user,user_name,password) VALUES('u','"
								+ email + "','" + passwrd + "')");
						Toast.makeText(getBaseContext(),
								"Registration Success !!", Toast.LENGTH_LONG)
								.show();
						finish();
					}
				}

			}
		});
	}

	protected boolean validateEmail(String emailID) {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(emailID);

		return matcher.matches();
	}

	public void QueryInsertUser(String COMANDSQL) {
		try {
			Statement statm = connect.createStatement();
			statm.executeUpdate(COMANDSQL);
			statm.close();
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void QueryCheckUser(String COMANDSQL) {
		ResultSet rs = null;
		try {
			Statement statm = connect.createStatement();
			rs = statm.executeQuery(COMANDSQL);

			while (rs.next()) {
				a = rs.getString("user_name");
			}
			statm.close();

		} catch (Exception e) {

			Log.e("ERROR: ", e.getMessage());
		}
	}

	public String getgender(View v) {
		switch (v.getId()) {
		case R.id.reg_GenderMale:
			gender = "Male";
			break;
		case R.id.reg_GenderFemale:
			gender = "Female";
			break;
		}
		return gender;

	}
}
