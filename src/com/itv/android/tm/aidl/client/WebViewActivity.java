package com.itv.android.tm.aidl.client;


import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.itv.android.tm.test.R;

public class WebViewActivity extends Activity {
	private WebView webVeiw;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		
		webVeiw = (WebView) findViewById(R.id.webview);
		
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
		
		webVeiw.loadUrl("www.baidu.com");  
	}
}
