package com.example.browser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class BrowserActivity extends Activity {
	  /** Called when the activity is first created. */
	 WebView wv;
	     Handler handler;
	     Button btnButton;
	     @Override
	     public void onCreate(Bundle savedInstanceState) {
	         super.onCreate(savedInstanceState);
	         setContentView(R.layout.main);
	         wv = (WebView)findViewById(R.id.webView1);
	   wv.getSettings().setJavaScriptEnabled(true);
	   wv.setScrollBarStyle(0);
	   wv.loadUrl("http://www.baidu.com");
	   
	   btnButton = (Button)findViewById(R.id.turn);
	   btnButton.setOnClickListener(new View.OnClickListener() {
	    
	    @Override
	    public void onClick(View v) {
	     EditText editText = (EditText)findViewById(R.id.editText1);
	     String strurl = String.valueOf(editText.getText());
	     if(strurl.contains("http://"))
	     {
	      Log.v("ttt", strurl);
	      loadurl(wv,strurl);
	     }else {
	      Log.v("ttt", strurl);
	      loadurl(wv,"http://www.baidu.com");
	     }   
	    }
	   });
	   wv.setWebViewClient(new WebViewClient()
	   {
	    public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
	      EditText newText = (EditText)findViewById(R.id.editText1);
	      newText.setText(url);
	               loadurl(view,url);
	               return true;  
	    }
	   });
	   
	     }
	     
	    public void loadurl(final WebView view,final String url){
	   new Thread(){
	    public void run(){
	      view.loadUrl(url);
	    }
	   }.start();
	 }

}