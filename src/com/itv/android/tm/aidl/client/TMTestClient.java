package com.itv.android.tm.aidl.client;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.itv.android.market2.aidl.InAppOrderAidl;
import com.itv.android.market2.aidl.InAppQueryAidl;
import com.itv.android.tm.aidl.client.broadcast.BillingResultReceiver;
import com.itv.android.tm.test.R;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class TMTestClient extends Activity implements OnClickListener {
	private static final String		TAG				= TMTestClient.class.getSimpleName();
	/** Called when the activity is first created. */
	private Button					btn				= null;
	private Button					btn2			= null;
	private Button					btn9			= null;
	private Button					btn10			= null;

	private InAppOrderAidl			buyInAppService	= null;
	// 创建远程调用对象
	private ServiceConnection		buyInAppConn	= new ServiceConnection() {

														public void onServiceConnected(ComponentName name,
																IBinder service) {
															buyInAppService = InAppOrderAidl.Stub.asInterface(service);
															Log.d(TAG, "InAppOrderAidl --> Bind Success:"
																	+ buyInAppService);
														}

														public void onServiceDisconnected(ComponentName name) {
															buyInAppService = null;
														}
													};

	private InAppQueryAidl			queryInAppService	= null;
	// 创建远程调用对象
	private ServiceConnection		queryInAppConn	= new ServiceConnection() {

														public void onServiceConnected(ComponentName name,
																IBinder service) {
															queryInAppService = InAppQueryAidl.Stub.asInterface(service);
															Log.d(TAG, "InAppQueryAidl --> Bind Success:"
																	+ queryInAppService);
														}

														public void onServiceDisconnected(ComponentName name) {
															queryInAppService = null;
														}
													};

	private BillingResultReceiver	billingReceiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn = (Button) findViewById(R.id.Button01);
		btn2 = (Button) findViewById(R.id.Button02);
		btn9 = (Button) findViewById(R.id.Button09);
		btn10 = (Button) findViewById(R.id.Button10);
		btn.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn10.setOnClickListener(this);

		billingReceiver = new BillingResultReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BillingResultReceiver.INAPP_BILLING);
		registerReceiver(billingReceiver, intentFilter);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void finish() {
		super.finish();
		if (billingReceiver != null) {
			unregisterReceiver(billingReceiver);
		}
	}

	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.Button01:
				Intent buyservice = new Intent(InAppOrderAidl.class.getName());
				bindService(buyservice, buyInAppConn, BIND_AUTO_CREATE);
				Intent queryservice = new Intent(InAppQueryAidl.class.getName());
				bindService(queryservice, queryInAppConn, BIND_AUTO_CREATE);
				break;

			case R.id.Button02:
				if (buyInAppService != null) {
					buyInAppService.orderRequest(getTestDataFromAssets(this, "inapp_req_param.json"));
				}
				else {
					Log.e(TAG, "buyAppService = NULL ");
				}
				break;
			case R.id.Button09:
				
				if (queryInAppService != null) {
					String queryResult = queryInAppService.detailsQuery(getTestDataFromAssets(this, "inapp_product_query_req_param.json"));
				
					if(!TextUtils.isEmpty(queryResult))
						Log.d(TAG, "In-App Product Query Result = " + queryResult);
					else
						Log.e(TAG, "In-App Product Query Failed! ");
				}
				else {
					Log.e(TAG, "queryInAppService = NULL ");
				}
				break;
			case R.id.Button10:
				
				if (queryInAppService != null) {
					String queryResult = queryInAppService.orderRecordQuery(getTestDataFromAssets(this, "inapp_order_query_req_param.json"));
				
					if(!TextUtils.isEmpty(queryResult))
						Log.d(TAG, "In-App Order Query Result = " + queryResult);
					else
						Log.e(TAG, "In-App Order Query Failed! ");
				}
				else {
					Log.e(TAG, "queryInAppService = NULL ");
				}
				break;
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.v(TAG, "keyCode = " + keyCode);

		return super.onKeyDown(keyCode, event);
	}

	public static String getTestDataFromAssets(Context context, String filename) {
		String data = "";
		try {
			AssetManager am = context.getAssets();
			InputStream is = am.open(filename);
			if (is != null) {
				byte[] info = readInputStream(is);
				data = new String(info);
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}



}