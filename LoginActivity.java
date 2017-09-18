package com.example.look4home;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button login, newusr, fb, twtr;
	EditText user_name, user_pass;
	public String user, pass, a, b, u;
	Connection connect;
	Context ctx = this;
	TextView frgtpaswd;
	Intent i;
	Progress call;
	boolean check;
	int status = 0;

	public LoginActivity() {
		call = new Progress();
		a = b = u = null;
		check = false;
		user = pass = null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_login);
		if (isNetworkAvailable() == true) {
			Toast.makeText(getBaseContext(), "!! Welcome To Look 4 Home !!",
					Toast.LENGTH_SHORT).show();
			connect = call.CONN();
		} else {
			Toast.makeText(getBaseContext(),
					"Check Your Internet\n  Connection!", Toast.LENGTH_SHORT)
					.show();
		}
		user_name = (EditText) findViewById(R.id.username);
		user_pass = (EditText) findViewById(R.id.password);

		frgtpaswd = (TextView) findViewById(R.id.frgtpassword);
		frgtpaswd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),
						"Please Register with New Account", Toast.LENGTH_SHORT)
						.show();

			}
		});
		login = (Button) findViewById(R.id.Login);
		login.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// Bundle b=new Bundle();
				hideSoftKeyboard();
				Toast.makeText(getBaseContext(), "Please wait...",
						Toast.LENGTH_SHORT).show();
				user = user_name.getText().toString();
				pass = user_pass.getText().toString();

				if (!validateEmail(user_name.getText().toString())) {
					user_name.setError("Enter Valid Email ID");
					user_name.requestFocus();

				} else if (pass.length() <= 4 || pass.length() >= 11) {
					user_pass.setError("Must be 5 to 10 character");
					user_pass.requestFocus();
				} else {
					QuerySQL("SELECT user_id,user_name,password FROM Login WHERE user_name='"
							+ user + "' and password='" + pass + "'");
					if (user.equals(a) && pass.equals(b)) {
						Toast.makeText(getBaseContext(), "Login Successful!",
								Toast.LENGTH_SHORT).show();
						i = new Intent(LoginActivity.this, MainActivity.class);
						i.putExtra("user_id", u);
						i.putExtra("user_email", a);
						startActivity(i);
						user_name.setText("");
						user_pass.setText("");
					} else {
						Toast.makeText(getBaseContext(), "No Such User Exists",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		newusr = (Button) findViewById(R.id.newuser);
		newusr.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(LoginActivity.this, RegisterActivity.class);

				startActivity(i);
			}
		});
		fb = (Button) findViewById(R.id.facebook);
		fb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://www.facebook.com/"));

				startActivity(i);
			}
		});
		twtr = (Button) findViewById(R.id.twitter);
		twtr.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i = new Intent(Intent.ACTION_VIEW, Uri
						.parse("https://twitter.com/?lang=en/"));

				startActivity(i);
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

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void QuerySQL(String COMANDSQL) {
		ResultSet rs = null;
		try {
			Statement statm = connect.createStatement();
			rs = statm.executeQuery(COMANDSQL);

			while (rs.next()) {
				u = rs.getString("user_id");
				a = rs.getString("user_name");
				b = rs.getString("password");

			}

			statm.close();

		} catch (Exception e) {
			check = true;
			Log.e("ERROR: ", e.getMessage());
		}
	}

	public void hideSoftKeyboard() {
		if (getCurrentFocus() != null) {
			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		}
	}

	/**
	 * Shows the soft keyboard
	 */
	public void showSoftKeyboard(View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		view.requestFocus();
		inputMethodManager.showSoftInput(view, 0);
	}
}
