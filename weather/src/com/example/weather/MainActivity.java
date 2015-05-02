package com.example.weather;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	TextView tv;

	String googleWeatherUrl1 = "http://www.google.com/ig/api?weather=zhengzhou";
	String googleWeatherUrl2 = "http://www.google.com/ig/api?hl=zh-cn&weather=zhengzhou";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ����UI���
		Button b1 = (Button) findViewById(R.id.Button01);
		Button b2 = (Button) findViewById(R.id.Button02);
		tv = (TextView) findViewById(R.id.TextView02);

		// ���ð�ť����������
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ʹ��URLConnection����GoogleWeatherAPI
				urlConn();
			}
		});

		// ���ð�ť����������
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// ʹ��HttpCient����GoogleWeatherAPI
				httpClientConn();

			}
		});

	}

	// ʹ��URLConnection����GoogleWeatherAPI
	protected void urlConn() {

		try {
			// URL
			URL url = new URL(googleWeatherUrl1);
			// HttpURLConnection
			HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();

			if (httpconn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				Toast.makeText(getApplicationContext(), "����Google Weather API�ɹ�!",
						Toast.LENGTH_SHORT).show();
				// InputStreamReader
				InputStreamReader isr = new InputStreamReader(httpconn.getInputStream(), "utf-8");
				int i;
				String content = "";
				// read
				while ((i = isr.read()) != -1) {
					content = content + (char) i;
				}
				isr.close();
				//����TextView
				tv.setText(content);
			}
			//disconnect
			httpconn.disconnect();

		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "����Google Weather APIʧ��", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		}
	}

	// ʹ��HttpCient����GoogleWeatherAPI
	protected void httpClientConn() {
		//DefaultHttpClient
		DefaultHttpClient httpclient = new DefaultHttpClient();
		//HttpGet
		HttpGet httpget = new HttpGet(googleWeatherUrl2);
		//ResponseHandler
		ResponseHandler<String> responseHandler = new BasicResponseHandler();

		try {
			String content = httpclient.execute(httpget, responseHandler);
			Toast.makeText(getApplicationContext(), "����Google Weather API�ɹ�!",
					Toast.LENGTH_SHORT).show();
			//����TextView
			tv.setText(content);
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "����Google Weather APIʧ��", Toast.LENGTH_SHORT)
			.show();
			e.printStackTrace();
		}
		httpclient.getConnectionManager().shutdown();
	}
}