package com.example.look4home;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.look4home.TableData.TableInfo;

public class Progress extends Activity {
	private ProgressBar ProBar;
	private static int progress = 0;
	private int prostatus = 0;
	Context context;
	private Handler handler = new Handler();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Remove the Title Bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.progress_main);
		ProBar = (ProgressBar) findViewById(R.id.progressBar);
		new Thread(new Runnable() {
			public void run() {
				while (prostatus < 100) {
					prostatus += 10;
					handler.post(new Runnable() {
						public void run() {
							ProBar.setProgress(prostatus);
						}
					});
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (prostatus == 100) {
					Intent i = new Intent(Progress.this, LoginActivity.class);
					startActivity(i);
					finish();
				}
			}
		}).start();
	}

	public Connection CONN() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String connURL = null;

		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			connURL = "jdbc:jtds:sqlserver://" + TableInfo.SERVER_NAME + ";"
					+ "databaseName=" + TableInfo.DATABASE_NAME + ";user="
					+ TableInfo.USER_NAME + ";password=" + TableInfo.USER_PASS
					+ ";";
			conn = DriverManager.getConnection(connURL);
			Log.w("Connection", "open");
			Log.d("Connection", "Your Database is Connected");
		} catch (SQLException se) {
			Log.e("ERROR: ", se.getMessage());
		} catch (ClassNotFoundException ce) {
			// TODO Auto-generated catch block
			Log.e("ERROR: ", ce.getMessage());
		} catch (Exception e) {
			Log.e("ERROR: ", e.getMessage());
		}

		return conn;
	}

	

}
